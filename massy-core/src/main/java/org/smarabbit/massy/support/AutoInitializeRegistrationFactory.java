/**
 * 
 */
package org.smarabbit.massy.support;

import org.smarabbit.massy.service.ServiceRegistrationManagerFactory;
import org.smarabbit.massy.adapt.AdaptFactoryRegistrationManagerFactory;

/**
 * 支持自动初始化的注册凭据工厂
 * <br>
 * 用于和{@link ServiceRegistrationManagerFactory}和{@link AdaptFactoryRegistrationManagerFactory}配对使用，
 * 在Massy Framework启动时，自动初始化对应的注册凭据工厂
 * 
 * @author huangkaihui
 *
 */
public interface AutoInitializeRegistrationFactory{

	/**
	 * 获取支持的类型 
	 * @return
	 */
	Class<?> getSupportType();
}
