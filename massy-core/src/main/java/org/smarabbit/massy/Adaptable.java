/**
 * 
 */
package org.smarabbit.massy;

/**
 * 适配能力，提供{@link #adapt(Class)}方法，获取对适配实例。
 * 
 * @author huangkh
 *
 */
public interface Adaptable {

	/**
	 * 适配，如果支持适配类型，则返回相应的适配对象。
	 * @param adaptType 
	 * 		{@link Class}，适配类型
	 * @return
	 * 		适配对象{@link A},不支持的适配返回null.
	 */
	<A> A adapt(Class<A> adaptType);
}
