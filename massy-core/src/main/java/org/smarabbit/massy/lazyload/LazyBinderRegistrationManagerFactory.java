/**
 * 
 */
package org.smarabbit.massy.lazyload;

import org.smarabbit.massy.service.ServiceRegistrationManager;
import org.smarabbit.massy.service.ServiceRegistrationManagerFactory;
import org.smarabbit.massy.service.ServiceRegistrationManagerFactoryChain;

/**
 * {@link LazyBinderRegistrationManager}工厂
 * @author huangkaihui
 */
public class LazyBinderRegistrationManagerFactory implements
		ServiceRegistrationManagerFactory {

	/**
	 * 
	 */
	public LazyBinderRegistrationManagerFactory() {
		
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistrationManagerFactory#create(java.lang.Class, org.smarabbit.massy.service.ServiceRegistrationManagerFactoryChain)
	 */
	@Override
	public ServiceRegistrationManager<?> create(Class<?> serviceType,
			ServiceRegistrationManagerFactoryChain chain) {
		if (serviceType == LazyBinder.class){
			return new LazyBinderRegistrationManager();
		}else{
			return chain.proceed(serviceType);
		}
	}

}
