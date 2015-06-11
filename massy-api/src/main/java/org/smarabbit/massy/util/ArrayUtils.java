/**
 * 
 */
package org.smarabbit.massy.util;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Set;

/**
 * 数组工具类,提供判断数组是否为Empty等方法
 * 
 * @author Huangkaihui
 *
 */
public abstract class ArrayUtils {

	/**
	 * 判断对象数组是否为null或者empty.
	 * @param target 对象数组
	 * @return 返回true表示为null或empty,否则返回false.
	 */
	public static boolean isEmpty(Object[] target){
		return target == null || target.length == 0;
	}
	
	/**
	 * 根据数组类型的class创建对应类型的数组
	 * @param <T> 目标类型
	 * @param clazz
	 * @param length 数组长度
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] newArrayByArrayClass(Class<T[]> clazz, int length) {
	    return (T[]) Array.newInstance(clazz.getComponentType(), length);
	}
	    
	/**
	 * 根据普通类型的class创建数组
	 * @param <T> 目标类型
	 * @param clazz
	 * @param length 数组长度
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] newArrayByClass(Class<T> clazz, int length) {
		return (T[]) Array.newInstance(clazz, length);
	}
	
	/**
	 * 转换数组为Set对象
	 * @param arr 目标数组,非空
	 * @param calzz 类型
	 * @return 返回Set对象，不能返回null.
	 */
	public static <T> Set<T> asSet(T[] arr, Class<T> calzz){
		Asserts.argumentNotNull(arr, "arr");
		Set<T> result = new HashSet<T>();
		for (T t: arr){
			result.add(t);
		}
		return result;
	}
}
