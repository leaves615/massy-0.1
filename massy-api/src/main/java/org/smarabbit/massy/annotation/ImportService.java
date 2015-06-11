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
 * 标记引入服务字段注解
 * 
 * <br>
 * 在字段被读取时，字段值为null时，自动绑定Massy Framework中的服务
 * @author huangkaihui
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ImportService {

	/**
	 * 引入服务类型，缺省取字段类型
	 * @return
	 * 			{@link Class}
	 */
	Class<?> serviceType() default Void.class;
	
	/**
	 * 引入服务别名,缺省值为空
	 * @return
	 * 			字符串
	 */
	String alias() default "";
	
	/**
	 * 是否允许服务不存在
	 * <br>
	 * 服务不存在而不允许为null.则会抛出异常
	 * @return
	 * 			true表示允许服务不存在，false表示不允许服务不存在
	 */
	boolean allowNull() default false;
}
