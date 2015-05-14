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
 * 标记为输出服务
 * 
 * <br>
 * 标记的实例被创建时，将被注册到Massy Framework中。
 * 
 * @author huangkaihui
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExportService {

	/**
	 * 输出服务类型, 缺省值为标记的类型
	 * @return
	 * 			{@link Class}数组
	 */
	Class<?>[] serviceTypes() default {} ;
	
	/**
	 * 服务别名，缺省值为空。
	 * @return
	 *          字符串
	 */
	String alias() default "";
}
