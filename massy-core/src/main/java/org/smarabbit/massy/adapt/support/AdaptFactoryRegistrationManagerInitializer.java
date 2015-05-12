/**
 * 
 */
package org.smarabbit.massy.adapt.support;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.smarabbit.massy.adapt.AdaptFactoryRegistrationManager;
import org.smarabbit.massy.adapt.AdaptFactoryRegistrationManagerFactory;
import org.smarabbit.massy.util.ServiceLoaderUtils;

/**
 * @author huangkaihui
 *
 */
public class AdaptFactoryRegistrationManagerInitializer {

	protected final ConcurrentHashMap<Class<?>, AdaptFactoryRegistrationManager<?>> managerMap =
			new ConcurrentHashMap<Class<?>, AdaptFactoryRegistrationManager<?>>();
	private Map<Class<?>, AdaptFactoryRegistrationManagerFactory<?>> factories =
			new HashMap<Class<?>, AdaptFactoryRegistrationManagerFactory<?>>();
	
	/**
	 * 
	 */
	public AdaptFactoryRegistrationManagerInitializer() {
		this.loadFactoriesAndInit();
	}
	
	public Class<?>[] getAdaptTypes() {
		return this.managerMap.keySet().toArray(
				new Class<?>[this.managerMap.size()]);
	}

	/**
	 * 加载所有{@link AdaptFactoryRegistrationManagerFactory},并自动初始化
	 */
	@SuppressWarnings("rawtypes")
	protected void loadFactoriesAndInit(){
		List<AdaptFactoryRegistrationManagerFactory> factories = 
				ServiceLoaderUtils.loadServices(AdaptFactoryRegistrationManagerFactory.class);		
		Iterator<AdaptFactoryRegistrationManagerFactory> it = factories.iterator();
		while (it.hasNext()){
			AdaptFactoryRegistrationManagerFactory factory = it.next();
			
			Class<?> serviceType = factory.getSupportType();
			if (factory.autoInit()){
				//自动初始化
				AdaptFactoryRegistrationManager mngr = factory.create();
				this.managerMap.putIfAbsent(serviceType, mngr);
			}else{
				this.factories.put(serviceType, factory);
			}
		}
	}
	
	/**
	 * 按服务类型获取对应的{@link AdaptFactoryRegistrationManager}
	 * @param adaptType 适配类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <A> AdaptFactoryRegistrationManager<A> getRegistrationManager(
			Class<A> adaptType){
		return (AdaptFactoryRegistrationManager<A>)this.managerMap.get(adaptType);
	}

	/**
	 * 创建对应服务类型的{@link AdaptFactoryRegistrationManager},如果无对应的{@link AdaptFactoryRegistrationManagerFactory},
	 * 则创建通用的{@link AdaptFactoryRegistrationManager}.
	 * @param adaptType 适配类型
	 * @return
	 * 			{@link AdaptFactoryRegistrationManager},不能返回null.
	 */
	@SuppressWarnings("unchecked")
	protected <A> AdaptFactoryRegistrationManager<A> createRegistrationManager(Class<A> adaptType){
		AdaptFactoryRegistrationManagerFactory<A> factory = 
				(AdaptFactoryRegistrationManagerFactory<A>)this.factories.remove(adaptType);
		if (factory != null){
			return factory.create();
		}else{
			//创建缺省的服务注册管理器
			return new DefaultAdaptFactoryRegistrationManager<A>();
		}
	}

}
