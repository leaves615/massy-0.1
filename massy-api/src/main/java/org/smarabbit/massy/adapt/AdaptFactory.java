/**
 * 
 */
package org.smarabbit.massy.adapt;


/**
 * 适配工厂,提供获取适配对象的能力
 * 
 * @author huangkh
 *
 */
public interface AdaptFactory<A> {
	
	/**
	 * 获得适配对象
	 * @param target 
	 * 		目标对象，非空
	 * @return
	 * 		适配对象，不支持返回null.
	 */
	A getAdaptor(Object target);
}
