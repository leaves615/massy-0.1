/**
 * 
 */
package org.smarabbit.massy.adapt.support;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.smarabbit.massy.adapt.AdaptFactoryRegistration;
import org.smarabbit.massy.adapt.AdaptFactoryRegistrationManager;
import org.smarabbit.massy.adapt.RegisterAdaptFactoryException;
import org.smarabbit.massy.annotation.support.AdaptDefinition;
import org.smarabbit.massy.annotation.support.AnnotatedDefinitionManagerFactory;
import org.smarabbit.massy.util.LogUtils;

/**
 * {@link AdaptFactoryRegistrationManager}的缺省实现
 * @author huangkaihui
 *
 */
public class DefaultAdaptFactoryRegistrationManager<A> extends
		AbstractAdaptFactoryRegistrationManager<A> {

	private final List<AdaptFactoryRegistration<A>> registrationList
			= new CopyOnWriteArrayList<AdaptFactoryRegistration<A>>();
	private final ConcurrentHashMap<Class<?>, AdaptFactoryRegistration<A>> specificMap =
			new ConcurrentHashMap<Class<?>, AdaptFactoryRegistration<A>>();
	private final TreeMap<Class<?>, AdaptFactoryRegistration<A>> extendsMap =
			new TreeMap<Class<?>, AdaptFactoryRegistration<A>>(new ClassSortedComparator());
	
	public DefaultAdaptFactoryRegistrationManager() {
		super();
	}
	
	

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.adapt.support.AbstractAdaptFactoryRegistrationManager#doGetAdaptor(java.lang.Class, java.lang.Object)
	 */
	@Override
	protected A doGetAdaptor(Class<?> hostClass, Object target) {
		//先看指定类型的适配
		AdaptFactoryRegistration<A> registration = this.specificMap.get(hostClass);
		if (registration != null){
			return this.getAdaptorFromRegistration(registration, target);
		}
		
		//在看扩展类型的适配
		for (Entry<Class<?>, AdaptFactoryRegistration<A>> entry : this.extendsMap.entrySet()){
			Class<?> extendClass = entry.getKey();
			if (extendClass.isAssignableFrom(hostClass)){
				return this.getAdaptorFromRegistration(entry.getValue(), target);
			}
		}
		return null;
	}



	/* (non-Javadoc)
	 * @see org.smarabbit.massy.support.AbstractRegistrationManager#doAdd(org.smarabbit.massy.Registration)
	 */
	@Override
	protected void doAdd(AdaptFactoryRegistration<A> registration) {
		//检查定义
		Class<?> annotatedType = registration.getAdaptFactory().getClass();
		AdaptDefinition definition =AnnotatedDefinitionManagerFactory.getDefault()
				.getDefinition(annotatedType, AdaptDefinition.class);
		if (definition == null){
			throw new RegisterAdaptFactoryException("cannot found AdaptDefiniton from " + annotatedType.getName() + ".");
		}
		
		this.registrationList.add(registration);
		synchronized (this.registrationList){
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
	 * @see org.smarabbit.massy.support.AbstractRegistrationManager#doRemove(org.smarabbit.massy.Registration)
	 */
	@Override
	protected void doRemove(AdaptFactoryRegistration<A> registration) {
		this.registrationList.remove(registration);
		//检查定义
		Class<?> annotatedType = registration.getAdaptFactory().getClass();
		AdaptDefinition definition =AnnotatedDefinitionManagerFactory.getDefault()
				.getDefinition(annotatedType, AdaptDefinition.class);
				
		if (definition == null) return;
		
		synchronized(this.registrationList){
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

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.support.AbstractRegistrationManager#getRegistrations()
	 */
	@Override
	protected Collection<AdaptFactoryRegistration<A>> getRegistrations() {
		return this.registrationList;
	}

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
