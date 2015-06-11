/**
 * 
 */
package org.smarabbit.massy.service;

import org.smarabbit.massy.Registration;

/**
 * 服务注册凭据
 * @author huangkaihui
 *
 */
public interface ServiceRegistration extends Registration {

	/**
	 * 获取绑定的服务类型
	 * @return
	 * 		Class数组，不能返回null.
	 */
	Class<?>[] getServiceTypes();
	
	/**
	 * 获取注册对象
	 * <br>
	 * 注册对象可以是服务类型的实现，也可以是{@link ServiceFactory}对象实例
	 * @return
	 *    对象实例，不能为null.
	 */
	Object getRegistedObject();
	
	/**
	 * 获取注册对象的类型
	 * @return
	 * 			{@link Class},非空	 
	 */
	Class<?> getRegistedObjectType();
	
	/**
	 * 获取服务
	 * @return 
	 * 		服务实例，不能返回null.
	 */
	Object getService();
}
