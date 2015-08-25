package org.smarabbit.massy.annotation.support;

import java.lang.annotation.ElementType;

/**
 * 定义，对应注解或者配置设置的映射对象
 * @author huangkaihui
 *
 */
public interface Definition {

	/**
	 * 实际定义注解的{@link ElementType}
	 * @return
	 * 			{@link ElementType}
	 */
	ElementType getElementType();
	
	/**
	 * 是否可继承
	 * @return
	 */
	boolean isInherited();
	
}
