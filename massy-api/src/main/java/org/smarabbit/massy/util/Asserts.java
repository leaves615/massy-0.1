/**
 * 
 */
package org.smarabbit.massy.util;

import java.util.Collection;
import java.util.Map;

import org.smarabbit.massy.ArgumentEmptyException;
import org.smarabbit.massy.ArgumentNullException;
import org.smarabbit.massy.FieldNotInitializationException;

/**
 * 断言工具类
 * @author huangkaihui
 *
 */
public abstract class Asserts {
	
	/**
	 * 断言参数是否为null
	 * @param argument
	 * @param argumentName
	 */
	public static void argumentNotNull(Object argument, String argumentName){
		if (argument == null){
			throw new ArgumentNullException(argumentName);
		}
	}
	
	/**
	 * 断言参数是否为empty
	 * @param argument
	 * @param argumentName
	 */
	public static void argumentNotEmpty(Object[] argument, String argumentName){
		if (ArrayUtils.isEmpty(argument)){
			throw new ArgumentEmptyException(argumentName);
		}
	}
	
	/**
	 * 断言参数是否为empty
	 * @param argument
	 * @param argumentName
	 */
	public static void argumentNotEmpty(Collection<?> argument, String argumentName){
		if (CollectionUtils.isEmpty(argument)){
			throw new ArgumentEmptyException(argumentName);
		}
	}
	
	/**
	 * 断言参数是否为empty
	 * @param argument
	 * @param argumentName
	 */
	public static void argumentNotEmpty(Map<?, ?> argument, String argumentName){
		if (CollectionUtils.isEmpty(argument)){
			throw new ArgumentEmptyException(argumentName);
		}
	}
	
	/**
	 * 断言字段已初始化断言
	 * @param field 
	 * 			字段
	 * @param fieldName 
	 * 			字段名
	 * @throws FieldNotInitializationException
	 * 			字段为null时抛出异常
	 */
	public static <F> F ensureFieldInited(F fieldObj, String fieldName)
		throws FieldNotInitializationException{
		if (fieldObj == null){
			throw new FieldNotInitializationException(fieldName);
		}
		
		return fieldObj;
	}
}
