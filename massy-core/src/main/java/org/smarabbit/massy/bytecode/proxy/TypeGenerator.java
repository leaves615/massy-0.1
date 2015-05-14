/**
 * 
 */
package org.smarabbit.massy.bytecode.proxy;

/**
 * @author huangkaihui
 *
 */
public interface TypeGenerator {

	/**
	 * 生成代理类
	 * @param target
	 * 		被代理的目标
	 * @return
	 * 		生成的类型，不能返回null.
	 */
	<T> Class<? extends T> generate(Class<T> targetType) throws Exception;
}
