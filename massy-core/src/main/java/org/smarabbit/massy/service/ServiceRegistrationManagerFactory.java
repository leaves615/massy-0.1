/**
 * 
 */
package org.smarabbit.massy.service;

/**
 * {@link ServiceRegistrationManager}工厂，提供获取{@link ServiceRegistrationManager}服务
 * 
 * @author huangkaihui
 */
public interface ServiceRegistrationManagerFactory<S> {
		
	/**
	 * 指明是否在MassyContext初始化时自动构建对应的{@link ServiceRegistrationManager}实例
	 * @return 
	 * 			true表示是，否则返回false
	 */
	boolean autoInit();
	
	/**
	 * 获取支持的服务类型
	 * @return
	 */
	Class<S> getSupportType();
	
	/**
	 * 创建{@link ServiceRegistrationManager}
	 * @return
	 * 		{@link ServiceRegistrationManager},不能返回null.
	 */
	ServiceRegistrationManager<S> create();
}
