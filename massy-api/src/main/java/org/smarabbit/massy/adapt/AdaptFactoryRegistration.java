/**
 * 
 */
package org.smarabbit.massy.adapt;

import org.smarabbit.massy.Registration;



/**
 * {@link AdaptFactory}注册器，提供已经注册的适配类型和{@link AdaptFactory},同时提供取消注册方法
 * 
 * @author huangkh
 *
 */
public interface AdaptFactoryRegistration<A> extends Registration {

	/**
	 * 获取注册的适配类型
	 * @return
	 * 		{@link Class},不能返回null.
	 */
	Class<A> getAdaptType();
			
	/**
	 * 获取注册的{@link AdaptFactory}
	 * @return
	 * 		{@link AdaptFactory},非空。
	 */
	AdaptFactory<A> getAdaptFactory();
}
