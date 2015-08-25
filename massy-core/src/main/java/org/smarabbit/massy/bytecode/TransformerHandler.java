/**
 * 
 */
package org.smarabbit.massy.bytecode;

import java.lang.instrument.IllegalClassFormatException;

/**
 * 增强转换处理句柄
 * @author huangkaihui
 */
public interface TransformerHandler<T> {

	/**
	 * 增强转换
	 * @param target 待增强转换目标，不能为null.
	 * @throws IllegalClassFormatException
	 * 			增强时发生类格式异常则抛出
	 */
	void transform(T target) throws IllegalClassFormatException;
}
