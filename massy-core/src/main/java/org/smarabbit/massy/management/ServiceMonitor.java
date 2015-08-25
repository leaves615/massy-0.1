/**
 * 
 */
package org.smarabbit.massy.management;

import org.smarabbit.massy.service.ServiceRegistration;

/**
 * @author huangkaihui
 *
 */
public interface ServiceMonitor{
	
	/**
	 * 注册服务注册凭据
	 * @param registration
	 */
	void register(ServiceRegistration registration);
	
	/**
	 * 撤销注册服务注册凭据
	 * @param registration
	 */
	void unregister(ServiceRegistration registration);
}
