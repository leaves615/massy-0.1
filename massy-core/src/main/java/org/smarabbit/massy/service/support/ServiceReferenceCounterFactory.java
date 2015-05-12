/**
 * 
 */
package org.smarabbit.massy.service.support;

import org.smarabbit.massy.support.ReferenceCounter;

/**
 * 服务引用计数器工厂
 * @author huangkaihui
 *
 */
public abstract class ServiceReferenceCounterFactory {

	private static ReferenceCounter INSTANCE = new ReferenceCounter(0);
	
	/**
	 * 获取服务引用计数器
	 * @return
	 * 			{@link ReferenceCounter},不能返回null.
	 */
	public static ReferenceCounter getReferenceCount(){
		return INSTANCE;
	}
}
