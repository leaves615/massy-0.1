/**
 * 
 */
package org.smarabbit.massy.service.support;

import java.util.EventListener;

import org.smarabbit.massy.service.ServiceRegistrationManager;
import org.smarabbit.massy.service.ServiceRegistrationManagerFactory;
import org.smarabbit.massy.service.ServiceRegistrationManagerFactoryChain;

/**
 * {@link ServiceListenerRegistrationManager}工厂
 * @author huangkaihui
 *
 */
public class ServiceListenerRegistrationManagerFactory implements
		ServiceRegistrationManagerFactory {

	/**
	 * 
	 */
	public ServiceListenerRegistrationManagerFactory() {
		
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistrationManagerFactory#create(java.lang.Class, org.smarabbit.massy.service.ServiceRegistrationManagerFactoryChain)
	 */
	@Override
	public ServiceRegistrationManager<?> create(Class<?> serviceType,
			ServiceRegistrationManagerFactoryChain chain) {
		if (serviceType == EventListener.class){
			return new ServiceListenerRegistrationManager();
		}else{
			return chain.proceed(serviceType);
		}
	}

}
