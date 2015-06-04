/**
 * 
 */
package org.smarabbit.massy.spring;

import org.smarabbit.massy.service.ServiceRegistrationManager;
import org.smarabbit.massy.service.ServiceRegistrationManagerFactory;
import org.smarabbit.massy.service.ServiceRegistrationManagerFactoryChain;
import org.springframework.context.ApplicationContext;

/**
 * {@link ApplicationContextRegistrationManager}工厂
 * @author huangkaihui
 *
 */
public class ApplicationContextRegistrationManagerFactory implements
		ServiceRegistrationManagerFactory {

	/**
	 * 
	 */
	public ApplicationContextRegistrationManagerFactory() {
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistrationManagerFactory#create(java.lang.Class, org.smarabbit.massy.service.ServiceRegistrationManagerFactoryChain)
	 */
	@Override
	public ServiceRegistrationManager<?> create(Class<?> serviceType,
			ServiceRegistrationManagerFactoryChain chain) {
		if (serviceType == ApplicationContext.class){
			return new ApplicationContextRegistrationManager();
		}else{
			return chain.proceed(serviceType);
		}
	}

	
	
}
