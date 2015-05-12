/**
 * 
 */
package org.smarabbit.massy.service;

/**
 * 提供创建单例服务的工厂
 * <br>
 * 如果提供多态的服务实例，请参阅{@link PrototypeServiceFactory}
 * @author huangkaihui
 *
 */
public interface ServiceFactory {

	/**
	 * 获取服务实例
	 * @return
	 * 		服务实例，不能返回null.
	 */
	Object getService();
}
