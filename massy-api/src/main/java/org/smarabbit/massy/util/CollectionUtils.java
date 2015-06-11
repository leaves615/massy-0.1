/**
 * 
 */
package org.smarabbit.massy.util;

import java.util.Collection;
import java.util.Map;

/**
 * 集合工具，提供是否为Empty判断
 * @author Huangkaihui
 *
 */
public abstract class CollectionUtils {

	/**
	 * 判断{@link Collection}是否为null或empty.
	 * @param target 目标集合对象
	 * @return 返回true表示为null或者empty,否则返回false.
	 */
	public static boolean isEmpty(Collection<?> target){
		return target == null || target.isEmpty();
	}
	
	/**
	 * 判断{@link Map}是否为null或empty.
	 * @param target 目标Map对象
	 * @return 返回true表示为null或者empty,否则返回false.
	 */
	public static boolean isEmpty(Map<?, ?> target){
		return target == null || target.isEmpty();
	}
	
}
