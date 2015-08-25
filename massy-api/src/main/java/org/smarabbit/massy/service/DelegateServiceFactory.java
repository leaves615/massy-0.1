/**
 * 
 */
package org.smarabbit.massy.service;

/**
 * 代理服务工厂，提供对服务的封装
 * @author huangkaihui
 *
 */
public interface DelegateServiceFactory {

	/**
	 * 获取委托类型
	 * @return
	 */
	Class<?> getObjectClass();
}
