/**
 * 
 */
package org.smarabbit.massy.service;

import java.util.Collections;
import java.util.List;

import org.smarabbit.massy.annotation.Ordered;


/**
 * 对象排序工具类，提供{@link Ordered}和{@link Order}的对象进行排序
 * 
 * @author huangkh
 *
 */
public final class ObjectOrderUtils {

	//排序器
	private static OrderComparator comparator = new OrderComparator();
	
	/**
	 * 排序
	 * @param list
	 */
	public static void sort(List<?> list){
		Collections.sort(list, comparator);
	}
}
