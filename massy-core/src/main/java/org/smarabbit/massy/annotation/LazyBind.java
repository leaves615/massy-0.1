/**
 * 
 */
package org.smarabbit.massy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 延迟绑定注解
 * 
 * @author huangkh
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LazyBind {
	
	/**
	 * 类名，默认取注解字段的类型
	 * @return
	 * 			{@link Class}
	 */
	Class<?> value() default Void.class;
	
	/**
	 * 字段名, 默认取字段名
	 * @return
	 * 		字符串
	 */
	String fieldName() default "";
}
