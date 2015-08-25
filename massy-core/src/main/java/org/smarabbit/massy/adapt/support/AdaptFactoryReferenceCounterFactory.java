/**
 * 
 */
package org.smarabbit.massy.adapt.support;

import org.smarabbit.massy.support.ReferenceCounter;

/**
 * 适配工厂引用计数器
 * @author huangkaihui
 *
 */
public abstract class AdaptFactoryReferenceCounterFactory {

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
