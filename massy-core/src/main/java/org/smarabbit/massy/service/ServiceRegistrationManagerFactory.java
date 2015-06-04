/**
 * 
 */
package org.smarabbit.massy.service;

/**
 * {@link ServiceRegistrationManager}工厂，提供获取{@link ServiceRegistrationManager}服务
 * 
 * <br>
 * 在实现上可注解{@link org.smarabbit.massy.annotation.Order},进行排序
 * @author huangkaihui
 */
public interface ServiceRegistrationManagerFactory{
	
	/**
	 * 创建{@link ServiceRegistrationManager}
	 * <br>
	 * 如果serviceType是所支付的服务类型，返回新建的{@link ServiceRegistrationManager},
	 * 否则调用{@link ServiceRegistrationManagerFactoryChain#proceed(Class)}进行处理。
	 * 
	 * @param serviceType 服务类型
	 * @param chain 服务工厂链
	 * @return
	 * 		{@link ServiceRegistrationManager},不能返回null.
	 */
	ServiceRegistrationManager<?> create(Class<?> serviceType,  ServiceRegistrationManagerFactoryChain chain);
}
