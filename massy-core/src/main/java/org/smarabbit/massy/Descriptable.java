/**
 * 
 */
package org.smarabbit.massy;

/**
 * 具有{@link Descriptor}的能力
 * @author huangkaihui
 *
 */
public interface Descriptable {

	/**
	 * 获取对象描述说明
	 * @return
	 * 		{@link Descriptor},不能返回null.
	 */
	Descriptor getDescriptor();
}
