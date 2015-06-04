/**
 * 
 */
package org.smarabbit.massy.adapt.support;

import java.util.Iterator;

import org.smarabbit.massy.Descriptor;
import org.smarabbit.massy.adapt.AdaptFactoryRegistration;
import org.smarabbit.massy.adapt.AdaptFactoryRegistrationManager;
import org.smarabbit.massy.spec.Specification;
import org.smarabbit.massy.support.AbstractRegistrationManager;
import org.smarabbit.massy.util.Asserts;

/**
 * @author huangkaihui
 *
 */
public abstract class AbstractAdaptFactoryRegistrationManager<A> extends
		AbstractRegistrationManager<AdaptFactoryRegistration<A>> implements
		AdaptFactoryRegistrationManager<A> {

	private Class<A> adaptType;
	
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
	 * @see org.smarabbit.massy.adapt.AdaptFactoryRegistrationManager#getAdaptor(java.lang.Object)
	 */
	@Override
	public A getAdaptor(Object target) {
		Asserts.argumentNotNull(target, "target");
		return this.doGetAdaptor(target.getClass(), target);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.adapt.AdaptFactoryRegistrationManager#getAdaptor(java.lang.Object, org.smarabbit.massy.spec.Specification)
	 */
	@Override
	public A getAdaptor(Object target, Specification<Descriptor> spec) {
		Asserts.argumentNotNull(target, "target");
		A result = null;
				
		//循环调用AdaptFactoryRegistration的adapt方式，直到返回值不为null.
		Iterator<AdaptFactoryRegistration<A>> it = this.getRegistrations().iterator();
		while (it.hasNext()){
			AdaptFactoryRegistration<A> registration = it.next();
			
			if (spec.isStaisfyBy(registration.getDescriptor())){
				result = this.getAdaptorFromRegistration(registration, target);
				if (result != null){
					return result;
				}
			}
		}
		return null;
	}
	
	protected A getAdaptorFromRegistration(AdaptFactoryRegistration<A> registration, Object target){
		Asserts.argumentNotNull(registration, "registration");
		return registration.getAdaptFactory().getAdaptor(target);
	}

	
	
	/**
	 * 获取适配对象
	 * @param hostClass
	 * @param target
	 * @return
	 */
	protected abstract  A doGetAdaptor(Class<?> hostClass, Object target);
}
