/**
 * 
 */
package org.smarabbit.massy.service.support;

import org.smarabbit.massy.annotation.ProxyServiceType;
import org.smarabbit.massy.service.ServiceRegistrationManager;
import org.smarabbit.massy.service.ServiceRegistrationManagerFactory;
import org.smarabbit.massy.service.ServiceRegistrationManagerFactoryChain;

/**
 * 注解{@link ProxyServiceType}服务类型的注册器工厂
 * @author huangkaihui
 *
 *
 */
public class ProxyAnnotatedServiceRegistrationManagerFactory implements
		ServiceRegistrationManagerFactory {

	/**
	 * 
	 */
	public ProxyAnnotatedServiceRegistrationManagerFactory() {
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistrationManagerFactory#create(java.lang.Class, org.smarabbit.massy.service.ServiceRegistrationManagerFactoryChain)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ServiceRegistrationManager<?> create(Class<?> serviceType,
			ServiceRegistrationManagerFactoryChain chain) {
		ServiceRegistrationManager<?> result = chain.proceed(serviceType);
		
		if (serviceType.isAnnotationPresent(ProxyServiceType.class)){
			if (result == null){
				result = new ProxyServiceRegistrationManager();
			}else{
				result = new ServiceRegistrationManagerProxyWrapper(result);
			}
		}
		
		return result;
	}

}
