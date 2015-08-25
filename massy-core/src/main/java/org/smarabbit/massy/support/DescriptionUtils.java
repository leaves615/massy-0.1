/**
 * 
 */
package org.smarabbit.massy.support;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.smarabbit.massy.annotation.Description;


/**
 * {@link Description}注解工具类，提供注解的内容
 * @author huangkh
 *
 */
public abstract class DescriptionUtils {

	/**
	 * 获得对象定义的{@link Description}说明信息
	 * @param target 
	 * 		目标对象,非空
	 * @return
	 * 		字符串，无{@link Description}注解返回null.
	 */
	public static String getDescription(Object target){
		if (target == null) return null;
		return getDescription(target.getClass());
	}
	
	/**
	 * 获得类定义的{@link Description}说明信息
	 * @param clazz 
	 * 		目标对象,非空
	 * @return
	 * 		字符串，无{@link Description}注解返回null.
	 */
	public static String getDescription(Class<?> clazz){
		Description desc = clazz.getAnnotation(Description.class);
		return desc == null ? null: desc.value();
	}
	
	/**
	 * 获得方法定义的{@link Description}说明信息
	 * @param method 
	 * 		目标对象,非空
	 * @return
	 * 		字符串，无{@link Description}注解返回null.
	 */
	public static String getDescription(Method method){
		Description desc = method.getAnnotation(Description.class);
		return desc == null ? null: desc.value();
	}
	
	/**
	 * 获得方法定义的{@link Description}说明信息
	 * @param field 
	 * 		目标对象,非空
	 * @return
	 * 		字符串，无{@link Description}注解返回null.
	 */
	public static String getDescription(Field field){
		Description desc = field.getAnnotation(Description.class);
		return desc == null ? null: desc.value();
	}
}
