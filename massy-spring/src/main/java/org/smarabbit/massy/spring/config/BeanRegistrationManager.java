/**
 * 
 */
package org.smarabbit.massy.spring.config;

import org.smarabbit.massy.Registration;


/**
 * @author huangkaihui
 *
 */
public interface BeanRegistrationManager {

	/**
	 * 添加{@link Registration}
	 * @param registration {@link Registration},不能为null.
	 */
	<R extends Registration> void addRegistration(String beanName, R registration);
	
	/**
	 * 取消注册
	 */
	void unregister(String beanName);
}
