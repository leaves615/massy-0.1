/**
 * 
 */
package org.smarabbit.massy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 适配类型注解，标注在{@link AdaptFactory}上，表明可适配的类型和目标类型。
 * @author huangkh
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Adapt {

	/**
	 * 可适配类型
	 * @return
	 * 		{@link Class}
	 */
	Class<?> value();
}
