/**
 * 
 */
package org.smarabbit.massy.service.support;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.smarabbit.massy.annotation.AutoInitializeServiceType;
import org.smarabbit.massy.service.ServiceRegistrationManager;
import org.smarabbit.massy.service.ServiceRegistrationManagerFactory;
import org.smarabbit.massy.service.ServiceRegistrationManagerFactoryChain;
import org.smarabbit.massy.support.AutoInitializeRegistrationFactory;
import org.smarabbit.massy.support.ObjectOrderUtils;
import org.smarabbit.massy.util.ServiceLoaderUtils;

/**
 * @author huangkaihui
 *
 */
public abstract class ServiceRegistrationManagerInitializer {

	protected final ConcurrentHashMap<Class<?>, ServiceRegistrationManager<?>> managerMap =
			new ConcurrentHashMap<Class<?>, ServiceRegistrationManager<?>>();
	
	private ServiceRegistrationManagerFactoryChain chain;
	
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
	protected void loadFactoriesAndInit(){
		List<ServiceRegistrationManagerFactory> factories = 
				ServiceLoaderUtils.loadServices(ServiceRegistrationManagerFactory.class);	
		
		//检查支持AutoInitializable的工厂，并创建对应的ServiceRegistrationManagerFactory.
		int size = factories.size();
		for (int i=size-1; i>-1; i--){
			ServiceRegistrationManagerFactory factory = factories.get(i);
			
			if (factory instanceof AutoInitializeRegistrationFactory){
				Class<?> serviceType = ((AutoInitializeRegistrationFactory)factory).getSupportType();
				Chain chain = new Chain(factory);
				ServiceRegistrationManager<?> mngr =chain.proceed(serviceType);
				this.managerMap.put(serviceType, mngr);
				
				factories.remove(factory);
			}
		}
				
		ObjectOrderUtils.sort(factories);
		factories.add(0, new ProxyAnnotatedServiceRegistrationManagerFactory());
		
		Iterator<ServiceRegistrationManagerFactory> it = factories.iterator();
		this.chain = new Chain(it);
	}
	
	/**
	 * 按服务类型获取对应的{@link ServiceRegistrationManager}
	 * @param serviceType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <S> ServiceRegistrationManager<S> getRegistrationManager(
			Class<S> serviceType){
		ServiceRegistrationManager<S> result = (ServiceRegistrationManager<S>)this.managerMap.get(serviceType);
		if (result == null){
			//自动实例化的服务注册管理器
			if (this.isAutoInitializeServiceType(serviceType)){
				result = this.createRegistrationManager(serviceType);
			}
		}
		
		return result;
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
				
		ServiceRegistrationManager<S> result = null;
		ServiceRegistrationManager<S> tmp =  this.chain.proceed(serviceType);
		if (tmp == null){
			tmp = new DefaultServiceRegistrationManager<S>();
		}
		if (tmp.getServiceType() == null){
			tmp.bind(serviceType);
		}
		
		result = (ServiceRegistrationManager<S>) this.managerMap.putIfAbsent(serviceType, tmp);
		if (result == null){
			result = tmp;
		}
		
		return result;
	}
	
	private boolean isAutoInitializeServiceType(Class<?> serviceType){
		return serviceType.isAnnotationPresent(AutoInitializeServiceType.class);
	}

	private class Chain implements ServiceRegistrationManagerFactoryChain {
		
		private ServiceRegistrationManagerFactory current;
		private Chain next;
		
		public Chain(ServiceRegistrationManagerFactory factory){
			this.current = factory;
			this.next = null;
		}

		public Chain(Iterator<ServiceRegistrationManagerFactory> it) {
			if (it.hasNext()){
				this.current = it.next();
				this.next = new Chain(it);
			}else{
				this.current = null;
				this.next = null;
			}
		}

		/* (non-Javadoc)
		 * @see org.smarabbit.massy.service.ServiceRegistrationManagerFactoryChain#proceed(java.lang.Class)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public <S> ServiceRegistrationManager<S> proceed(Class<S> serviceType) {
			ServiceRegistrationManager<?> result = null;
			if (this.current != null){
				result = this.current.create(serviceType, next);
			}
			return (ServiceRegistrationManager<S>)result;
		}
	}
}
