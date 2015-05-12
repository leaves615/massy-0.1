/**
 * 
 */
package org.smarabbit.massy.support;

/**
 * @author huangkh
 *
 */
public interface Ordered {

	/**
	 * 最高优先级
	 * @see java.lang.Integer#MIN_VALUE
	 */
	int HIGHEST_PRECEDENCE = Integer.MIN_VALUE;
	
	/**
	 * 缺省优先级
	 */
	int DEFAULT_PRECEDENCE = Integer.MAX_VALUE - 100;

	/**
	 * 最低优先级
	 * @see java.lang.Integer#MAX_VALUE
	 */
	int LOWEST_PRECEDENCE = Integer.MAX_VALUE;
	
	/**
	 * 获取目标对象的排序
	 * @return 
	 * 		整形，排序
	 */
	int getOrder();
}
