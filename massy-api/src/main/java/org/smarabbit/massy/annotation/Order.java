/**
 * 
 */
package org.smarabbit.massy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 排序注解
 * 
 * @author huangkh
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
public @interface Order {

	/**
	 * 排序值
	 * @return
	 */
	int value() default Ordered.DEFAULT_PRECEDENCE ;
}
