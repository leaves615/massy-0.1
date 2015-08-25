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
 * 代理服务类型注解，获取实现该服务类型的Massy服务，将返回一个代理对象。
 * @author huangkaihui
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ProxyServiceType {

}
