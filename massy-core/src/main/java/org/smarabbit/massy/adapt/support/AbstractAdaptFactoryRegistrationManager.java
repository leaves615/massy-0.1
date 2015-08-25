/**
 * 
 */
package org.smarabbit.massy.adapt.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.smarabbit.massy.Descriptor;
import org.smarabbit.massy.adapt.AdaptFactoryRegistration;
import org.smarabbit.massy.adapt.AdaptFactoryRegistrationManager;
import org.smarabbit.massy.adapt.RegisterAdaptFactoryException;
import org.smarabbit.massy.annotation.support.AdaptDefinition;
import org.smarabbit.massy.annotation.support.AnnotatedDefinitionManagerFactory;
import org.smarabbit.massy.spec.Specification;
import org.smarabbit.massy.support.AbstractRegistrationManager;
import org.smarabbit.massy.util.Asserts;
import org.smarabbit.massy.util.LogUtils;

/**
 * @author huangkaihui
 *
 */
public abstract class AbstractAdaptFactoryRegistrationManager<A> extends
		AbstractRegistrationManager<AdaptFactoryRegistration<A>> implements
		AdaptFactoryRegistrationManager<A> {

	private Class<A> adaptType;
	
	private final ConcurrentHashMap<Class<?>, AdaptFactoryRegistration<A>> specificMap =
			new ConcurrentHashMap<Class<?>, AdaptFactoryRegistration<A>>();
	private final TreeMap<Class<?>, AdaptFactoryRegistration<A>> extendsMap =
			new TreeMap<Class<?>, AdaptFactoryRegistration<A>>(new ClassSortedComparator());
	
	public AbstractAdaptFactoryRegistrationManager() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see org.smarabbit.massy.adapt.AdaptFactoryRegistrationManager#bind(java.lang.Class)
	 */
	@Override
	public void bind(Class<A> adaptType) {
		this.adaptType = adaptType;
	}
	
	@Override
	public Class<A> getAdaptType(){
		return this.adaptType;
	}
	
	
	/* (non-Javadoc)
	 * @see org.smarabbit.massy.adapt.AdaptFactoryRegistrationManager#isSupport(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> type) {
		//指定类型
		if (this.specificMap.contains(type)){
			return true;
		}
		
		//扩展类型
		Collection<Class<?>> c = this.extendsMap.keySet();
		for (Class<?> tmp : c){
			if (tmp.isAssignableFrom(type)){
				return true;
			}
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.adapt.AdaptFactoryRegistrationManager#getAdaptor(java.lang.Object)
	 */
	@Override
	public A getAdaptor(Object target) {
		Asserts.argumentNotNull(target, "target");
		Class<?> clazz = target.getClass();
		//先看指定类型的适配
		AdaptFactoryRegistration<A> registration = this.specificMap.get(clazz);
		if (registration != null){
				return this.getAdaptorFromRegistration(registration, target);
		}
				
		//在看扩展类型的适配
		for (Entry<Class<?>, AdaptFactoryRegistration<A>> entry : this.extendsMap.entrySet()){
			Class<?> extendClass = entry.getKey();
			if (extendClass.isAssignableFrom(clazz)){
				return this.getAdaptorFromRegistration(entry.getValue(), target);
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.adapt.AdaptFactoryRegistrationManager#getAdaptor(java.lang.Object, org.smarabbit.massy.spec.Specification)
	 */
	@Override
	public A getAdaptor(Object target, Specification<Descriptor> spec) {
		Asserts.argumentNotNull(target, "target");
		
		A result = null;
		
		Class<?> clazz = target.getClass();
		List<AdaptFactoryRegistration<A>> c =
				new ArrayList<AdaptFactoryRegistration<A>>();
		
		//先看指定类型的适配
		AdaptFactoryRegistration<A> registration = this.specificMap.get(clazz);
		if (registration != null){
				c.add(registration);
		}
					
		//在看扩展类型的适配
		for (Entry<Class<?>, AdaptFactoryRegistration<A>> entry : this.extendsMap.entrySet()){
			Class<?> extendClass = entry.getKey();
			if (extendClass.isAssignableFrom(clazz)){
				c.add(entry.getValue());
			}
		}
		
		//循环调用AdaptFactoryRegistration的adapt方式，直到返回值不为null.
		Iterator<AdaptFactoryRegistration<A>> it = c.iterator();
		while (it.hasNext()){
			AdaptFactoryRegistration<A> tmp = it.next();
			
			if (spec.isStaisfyBy(tmp.getDescriptor())){
				result = this.getAdaptorFromRegistration(tmp, target);
				if (result != null){
					return result;
				}
			}
		}
		return result;
	}
	
	protected A getAdaptorFromRegistration(AdaptFactoryRegistration<A> registration, Object target){
		Asserts.argumentNotNull(registration, "registration");
		return registration.getAdaptFactory().getAdaptor(target);
	}

	/**
	 * 获取{@link AdaptDefinition}
	 * @param registration {@link AdaptFactoryRegistration}
	 * @return
	 * 		{@link AdaptDefinition}，不能返回null.
	 */
	protected AdaptDefinition getAdaptDefinition(AdaptFactoryRegistration<A> registration){
		//检查定义
		Class<?> annotatedType = registration.getAdaptFactory().getClass();
		AdaptDefinition result = AnnotatedDefinitionManagerFactory.getDefault()
				.getDefinition(annotatedType, AdaptDefinition.class);
		if (result == null){
			throw new RegisterAdaptFactoryException("cannot found AdaptDefiniton from " + annotatedType.getName() + ".");
		}
		
		return result;
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.smarabbit.massy.support.AbstractRegistrationManager#add(org.smarabbit.massy.Registration)
	 */
	@Override
	public void add(AdaptFactoryRegistration<A> registration) {
		Asserts.argumentNotNull(registration, "registration");
		
		AdaptDefinition definition = this.getAdaptDefinition(registration);
		this.doAdd(registration);
		
		synchronized (this){
			int size = definition.getSpecificStrategy().length;
			if (size >0){
				//宿主类型，指定模式
				for (int i=0; i<size; i++){
					Class<?> specClass = definition.getSpecificStrategy()[i];
					if (!this.specificMap.contains(specClass)){
						this.specificMap.put(specClass, registration);
					}else{
						if (LogUtils.isWarnEnabled()){
							LogUtils.warn("register specifici class  to adapt failed: " +  specClass.getName() + ",  same adapt exist.");
						}
					}
				}
			}
			
			size = definition.getExtendsStrategy().length;
			if (size >0){
				//宿主类型，扩展模式
				for (int i=0; i<size; i++){
					Class<?> extendClass = definition.getExtendsStrategy()[i];
					this.extendsMap.put(extendClass, registration);
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.smarabbit.massy.support.AbstractRegistrationManager#remove(org.smarabbit.massy.Registration)
	 */
	@Override
	public void remove(AdaptFactoryRegistration<A> registration) {
		Asserts.argumentNotNull(registration, "registration");
		
		AdaptDefinition definition = this.getAdaptDefinition(registration);
		if (definition == null) return;
		
		super.remove(registration);
		
		synchronized(this){
			int size = definition.getSpecificStrategy().length;
			for (int i=0; i<size; i++){
				Class<?> clazz = definition.getSpecificStrategy()[i];
				AdaptFactoryRegistration<A> value = this.specificMap.get(clazz);
				if (value == registration){
					this.specificMap.remove(clazz);
				}
			}
			size = definition.getExtendsStrategy().length;
			for (int i=0; i<size; i++){
				Class<?> clazz = definition.getExtendsStrategy()[i];
				AdaptFactoryRegistration<A> value = this.extendsMap.get(clazz);
				if (value == registration){
					this.extendsMap.remove(clazz);
				}
			}
		}
	}
	
	/**
	 * 类排序比较器,按类的继承关系排序
	 * @author huangkaihui
	 *
	 */
	private class ClassSortedComparator implements Comparator<Class<?>> {

		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(Class<?> clazz1, Class<?> clazz2) {
			if (clazz1 == clazz2) return 0;
			
			return clazz1.isAssignableFrom(clazz2) ? -1: 1;
		}
		
		
	}
}
