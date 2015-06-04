/**
 * 
 */
package org.smarabbit.massy.service;


/**
 * 服务监听器，提供监听服务注册和取消服务注册事件的方法。
 * @author huangkh
 *
 */
public interface ServiceListener {

	/**
	 * 服务注册与取消注册事件
	 * @param event {@link ServiceEvent},非空
	 */
	void onEvent(ServiceEvent event);
}
