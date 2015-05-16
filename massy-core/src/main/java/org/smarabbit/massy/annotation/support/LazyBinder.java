/**
 * 
 */
package org.smarabbit.massy.annotation.support;

/**
 * @author huangkaihui
 *
 */
public interface LazyBinder<T> {

	/**
	 * 获取字段值
	 * @param declaringObject {@link T},声明延迟加载字段的实例，非空
	 * @return
	 * 		字段值，可以返回null.
	 */
	Object getValue(T declaringObject);
}
