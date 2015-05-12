/**
 * 
 */
package org.smarabbit.massy.service.support;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.smarabbit.massy.service.ServiceRegistrationManager;
import org.smarabbit.massy.service.ServiceRegistrationManagerFactory;
import org.smarabbit.massy.util.ServiceLoaderUtils;

/**
 * @author huangkaihui
 *
 */
public abstract class ServiceRegistrationManagerInitializer {

	protected final ConcurrentHashMap<Class<?>, ServiceRegistrationManager<?>> managerMap =
			new ConcurrentHashMap<Class<?>, ServiceRegistrationManager<?>>();
	private Map<Class<?>, ServiceRegistrationManagerFactory<?>> factories =
			new HashMap<Class<?>, ServiceRegistrationManagerFactory<?>>();
	
	/**
	 * 
	 */
	public ServiceRegistrationManagerInitializer() {
		this.loadFactoriesAndInit();
	}
	
	public Class<?>[] getServiceTypes() {
		return this.managerMap.keySet().toArray(
				new Class<?>[this.managerMap.size()]);
	}

	/**
	 * 加载所有{@link ServiceRegistrationManagerFactory},并自动初始化
	 */
	@SuppressWarnings("rawtypes")
	protected void loadFactoriesAndInit(){
		List<ServiceRegistrationManagerFactory> factories = 
				ServiceLoaderUtils.loadServices(ServiceRegistrationManagerFactory.class);		
		Iterator<ServiceRegistrationManagerFactory> it = factories.iterator();
		while (it.hasNext()){
			ServiceRegistrationManagerFactory factory = it.next();
			
			Class<?> serviceType = factory.getSupportType();
			if (factory.autoInit()){
				//自动初始化
				ServiceRegistrationManager mngr = factory.create();
				this.managerMap.putIfAbsent(serviceType, mngr);
			}else{
				this.factories.put(serviceType, factory);
			}
		}
	}
	
	/**
	 * 按服务类型获取对应的{@link ServiceRegistrationManager}
	 * @param serviceType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <S> ServiceRegistrationManager<S> getRegistrationManager(
			Class<S> serviceType){
		return (ServiceRegistrationManager<S>)this.managerMap.get(serviceType);
	}

	/**
	 * 创建对应服务类型的{@link ServiceRegistrationManager},如果无对应的{@link ServiceRegistrationManagerFactory},
	 * 则创建通用的{@link ServiceRegistrationManager}.
	 * @param serviceType
	 * @return
	 * 			{@link ServiceRegistrationManager},不能返回null.
	 */
	@SuppressWarnings("unchecked")
	protected <S> ServiceRegistrationManager<S> createRegistrationManager(Class<S> serviceType){
		ServiceRegistrationManagerFactory<S> factory = 
				(ServiceRegistrationManagerFactory<S>)this.factories.remove(serviceType);
		if (factory != null){
			return factory.create();
		}else{
			//创建缺省的服务注册管理器
			return new DefaultServiceRegistrationManager<S>();
		}
	}

}
