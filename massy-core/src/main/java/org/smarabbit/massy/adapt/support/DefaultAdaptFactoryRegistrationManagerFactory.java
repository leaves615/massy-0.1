/**
 * 
 */
package org.smarabbit.massy.adapt.support;

import org.smarabbit.massy.adapt.AdaptFactoryRegistry;
import org.smarabbit.massy.service.ServiceRegistrationManager;
import org.smarabbit.massy.service.ServiceRegistrationManagerFactory;
import org.smarabbit.massy.service.ServiceRegistrationManagerFactoryChain;

/**
 * {@link AdaptFactoryRegistry}服务工厂，提供{@link AdaptFactoryRegistry}对应的服务注册管理器实例
 * 
 * @see {@link ServiceRegistrationManagerFactory}
 * @author huangkaihui
 *
 */
public class DefaultAdaptFactoryRegistrationManagerFactory implements
		ServiceRegistrationManagerFactory {

	/**
	 * 
	 */
	public DefaultAdaptFactoryRegistrationManagerFactory() {
		
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistrationManagerFactory#create(java.lang.Class, org.smarabbit.massy.service.ServiceRegistrationManagerFactoryChain)
	 */
	@Override
	public ServiceRegistrationManager<?> create(Class<?> serviceType,
			ServiceRegistrationManagerFactoryChain chain) {
		if (serviceType == AdaptFactoryRegistry.class){
			return new DefaultAdaptFactoryRepositoryRegistrationManager();
		}else{
			return chain.proceed(serviceType);
		}
	}

}
