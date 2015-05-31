/**
 * 
 */
package org.smarabbit.massy.adapt.support;

import org.smarabbit.massy.adapt.AdaptFactoryRegistry;
import org.smarabbit.massy.service.ServiceRegistrationManager;
import org.smarabbit.massy.service.ServiceRegistrationManagerFactory;

/**
 * {@link AdaptFactoryRegistry}服务工厂，提供{@link AdaptFactoryRegistry}对应的服务注册管理器实例
 * 
 * @see {@link ServiceRegistrationManagerFactory}
 * @author huangkaihui
 *
 */
public class DefaultAdaptFactoryRegistrationManagerFactory implements
		ServiceRegistrationManagerFactory<AdaptFactoryRegistry> {

	/**
	 * 
	 */
	public DefaultAdaptFactoryRegistrationManagerFactory() {
		
	}

	@Override
	public boolean autoInit() {
		//随massy framework启动时创建
		return true;
	}

	@Override
	public Class<AdaptFactoryRegistry> getSupportType() {
		return AdaptFactoryRegistry.class;
	}

	@Override
	public ServiceRegistrationManager<AdaptFactoryRegistry> create() {
		return new DefaultAdaptFactoryRepositoryRegistrationManager();
	}

	
}
