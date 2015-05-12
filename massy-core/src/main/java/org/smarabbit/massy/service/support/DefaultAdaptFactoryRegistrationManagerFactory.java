/**
 * 
 */
package org.smarabbit.massy.service.support;

import org.smarabbit.massy.adapt.AdaptFactoryRepository;
import org.smarabbit.massy.service.ServiceRegistrationManager;
import org.smarabbit.massy.service.ServiceRegistrationManagerFactory;

/**
 * {@link AdaptFactoryRepository}服务工厂，提供{@link AdaptFactoryRepository}对应的服务注册管理器实例
 * 
 * @see {@link ServiceRegistrationManagerFactory}
 * @author huangkaihui
 *
 */
public class DefaultAdaptFactoryRegistrationManagerFactory implements
		ServiceRegistrationManagerFactory<AdaptFactoryRepository> {

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
	public Class<AdaptFactoryRepository> getSupportType() {
		return AdaptFactoryRepository.class;
	}

	@Override
	public ServiceRegistrationManager<AdaptFactoryRepository> create() {
		return new DefaultAdaptFactoryRepositoryRegistrationManager();
	}

	
}
