/**
 * 
 */
package org.smarabbit.massy.service.support;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.smarabbit.massy.bytecode.proxy.ServiceProxyFactory;
import org.smarabbit.massy.service.PrototypeServiceFactory;
import org.smarabbit.massy.service.ServiceRegistration;
import org.smarabbit.massy.util.Asserts;

/**
 * 代理服务注册管理器，为提供按注册类型的服务代理。
 * @author huangkaihui
 *
 */
public class ProxyServiceRegistrationManager<S> extends
		AbstractServiceRegistrationManager<S> {

	private Map<ServiceRegistration, S> registrationMap =
			new HashMap<ServiceRegistration, S>();
	
	public ProxyServiceRegistrationManager() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.support.AbstractRegistrationManager#doAdd(org.smarabbit.massy.Registration)
	 */
	@Override
	protected void doAdd(ServiceRegistration registration) {
		this.registrationMap.put(registration, null);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.support.AbstractRegistrationManager#doRemove(org.smarabbit.massy.Registration)
	 */
	@Override
	protected void doRemove(ServiceRegistration registration) {
		this.registrationMap.remove(registration);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.support.AbstractRegistrationManager#getRegistrations()
	 */
	@Override
	protected Collection<ServiceRegistration> getRegistrations() {
		return this.registrationMap.keySet();
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.support.AbstractServiceRegistrationManager#getServiceFromRegistration(org.smarabbit.massy.service.ServiceRegistration)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected S getServiceFromRegistration(ServiceRegistration registration) {
		S result = this.registrationMap.get(registration);
		if (result == null){
			result = this.createProxy((S)registration.getService());
			if (this.isSingleton(registration)){
				this.registrationMap.put(registration, result);
			}
		}
		return result;
	}

	protected Map<ServiceRegistration, S> getServiceRegistrionMap(){
		return this.registrationMap;
	}
	
	protected S createProxy(S delegate){
		try{
			return ServiceProxyFactory.createProxy(this.getServiceType(), delegate);
		}catch(Exception e){
			throw new RuntimeException("create proxy failed.", e);
		}
	}
	
	/**
	 * 判断是否单例服务
	 * @param registration
	 * 			{@link ServiceRegistration},非空
	 * @return
	 * 			true表示是单例服务，否则返回false.
	 */
	protected boolean isSingleton(ServiceRegistration registration){
		Asserts.argumentNotNull(registration, "registration");
		return !PrototypeServiceFactory.class.isAssignableFrom(registration.getRegistedObjectType());
	}
}
