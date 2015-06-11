/**
 * 
 */
package org.smarabbit.massy.spec;

/**
 * 规则检查器,对目标对象是否满足规则进行检查，并返回是否满足的结果。
 * 
 * @author Huangkaihui
 *
 */
public interface Specification<T>{

	/**
	 * 判断是否满足规则要求
	 * @param target 目标对象，不能为null.
	 * @return 返回true表示满足特定规则，否则返回false.
	 */
	boolean isStaisfyBy(T target);
}
