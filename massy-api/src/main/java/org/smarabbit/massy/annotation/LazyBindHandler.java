/**
 * 
 */
package org.smarabbit.massy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 延迟绑定的处理句柄，方法有且只有一个参数，并必须有返回值，
 * 方法返回值将作为参数实例的fieldName字段的初始值。
 * 
 * @author huangkh
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LazyBindHandler {

	/**
	 * 声明类型,默认情况下取方法的唯一参数的类型。
	 * @return
	 * 		{@link Class}
	 */
	Class<?> declaringType() default Void.class;
	
	/**
	 * 目标类型的字段名，方法返回值将为参数实例的fieldName字段提供初始化值。
	 * @return
	 *    	字符串
	 */
	String fieldName();
}
