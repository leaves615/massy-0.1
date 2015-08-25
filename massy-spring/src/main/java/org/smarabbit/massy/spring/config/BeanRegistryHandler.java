/**
 * 
 */
package org.smarabbit.massy.spring.config;

import org.smarabbit.massy.Registration;
import org.smarabbit.massy.annotation.support.Definition;
import org.smarabbit.massy.spring.MassyResource;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * Bean注册处理句柄, 实现服务/适配等的注册。
 * @author huangkaihui
 *
 */
public interface BeanRegistryHandler {
	
	/**
	 * 判断是否支持{@link Definition}类型的注册
	 * @param definition {@link Definition}
	 * @return
	 * 		true表示支持，false表示不支持
	 */
	boolean support(Class<? extends Definition> definitionType);
	
	/**
	 * 注册并返回{@link Registration}
	 * 
	 * @param beanName bean名称
	 * @param factory {@link ConfigurableListableBeanFactory}工厂
	 * @param definition {@link Definition}
	 * @param resource {@link MassyResource},可以为null.
	 * @return
	 * 		{@link Registration},可能返回null.
	 */
	Registration register(String beanName, ConfigurableListableBeanFactory factory,  Definition definition, MassyResource resource);
}
