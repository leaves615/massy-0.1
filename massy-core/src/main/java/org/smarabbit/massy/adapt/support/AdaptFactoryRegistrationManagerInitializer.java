/**
 * 
 */
package org.smarabbit.massy.adapt.support;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.smarabbit.massy.adapt.AdaptFactoryRegistrationManager;
import org.smarabbit.massy.adapt.AdaptFactoryRegistrationManagerFactory;
import org.smarabbit.massy.adapt.AdaptFactoryRegistrationManagerFactoryChain;
import org.smarabbit.massy.annotation.AutoInitializeServiceType;
import org.smarabbit.massy.support.AutoInitializeRegistrationFactory;
import org.smarabbit.massy.util.ServiceLoaderUtils;

/**
 * @author huangkaihui
 *
 */
public class AdaptFactoryRegistrationManagerInitializer {

	protected final ConcurrentHashMap<Class<?>, AdaptFactoryRegistrationManager<?>> managerMap =
			new ConcurrentHashMap<Class<?>, AdaptFactoryRegistrationManager<?>>();
	
	private Chain chain;
	
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
	protected void loadFactoriesAndInit(){
		List<AdaptFactoryRegistrationManagerFactory> factories = 
				ServiceLoaderUtils.loadServices(AdaptFactoryRegistrationManagerFactory.class);		
		
		int size = factories.size();
		for (int i=size-1; i>-1; i--){
			AdaptFactoryRegistrationManagerFactory factory = factories.get(i);
			if (factory instanceof AutoInitializeRegistrationFactory){
				Class<?> adaptType = ((AutoInitializeRegistrationFactory)factory).getSupportType();
				
				Chain chain = new Chain(factory);
				AdaptFactoryRegistrationManager<?> mngr = chain.proceed(adaptType);
				this.managerMap.put(adaptType, mngr);
				
				factories.remove(factory);
			}
		}
		
		this.chain = new Chain(factories.iterator());
	}
	
	/**
	 * 按服务类型获取对应的{@link AdaptFactoryRegistrationManager}
	 * @param adaptType 适配类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <A> AdaptFactoryRegistrationManager<A> getRegistrationManager(
			Class<A> adaptType){
		 
		AdaptFactoryRegistrationManager<A> result =(AdaptFactoryRegistrationManager<A>) this.managerMap.get(adaptType);
		if (result == null){
			if (this.isAutoInitializeServiceType(adaptType)){
				result = this.createRegistrationManager(adaptType);
			}
		}
		
		return result;
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
		AdaptFactoryRegistrationManager<A> result = null;
		AdaptFactoryRegistrationManager<A> tmp = null;
		
		tmp = this.chain.proceed(adaptType);
		if (tmp == null){
			tmp = new DefaultAdaptFactoryRegistrationManager<A>();
		}
		if (tmp.getAdaptType() == null){
			tmp.bind(adaptType);
		}
		
		result = (AdaptFactoryRegistrationManager<A>) this.managerMap.putIfAbsent(adaptType, tmp);
		if (result == null){
			result = tmp;
		}
		
		return result;
	}
	
	private boolean isAutoInitializeServiceType(Class<?> serviceType){
		return serviceType.isAnnotationPresent(AutoInitializeServiceType.class);
	}

	private class Chain implements AdaptFactoryRegistrationManagerFactoryChain {
		
		private AdaptFactoryRegistrationManagerFactory current;
		private Chain next;
		
		public Chain(AdaptFactoryRegistrationManagerFactory factory){
			this.current = factory;
			this.next = null;
		}

		public Chain(Iterator<AdaptFactoryRegistrationManagerFactory> it) {
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
		public <A> AdaptFactoryRegistrationManager<A> proceed(Class<A> adaptType) {
			AdaptFactoryRegistrationManager<?> result = null;
			if (this.current != null){
				result = this.current.create(adaptType, next);
			}
			return (AdaptFactoryRegistrationManager<A>)result;
		}
	}
}
