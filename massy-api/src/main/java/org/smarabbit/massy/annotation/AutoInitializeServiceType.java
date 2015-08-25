/**
 * 
 */
package org.smarabbit.massy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动初始化的服务实例类型
 * <br>
 * 该服务至少存在一个对应服务类性的服务实例
 * @author huangkaihui
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoInitializeServiceType {

}
