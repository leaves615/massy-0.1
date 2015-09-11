/**
 * 
 */
package org.smarabbit.massy.spring.config;

import org.smarabbit.massy.annotation.support.Definition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * 定义处理器
 * @author huangkaihui
 *
 */
public interface DefinitionProcessor {

	/**
	 * 执行定义
	 * @param beanName bean名称
	 * @param beanType bean类型
	 * @param beanFactory {@link ConfigurableListableBeanFactory}工厂
	 * @return {@link Definition}定义，返回为非空则会在对应的{@link BeanRegistryHandler}中处理。
	 */
	Definition discovry(String beanName, Class<?> beanType, ConfigurableListableBeanFactory beanFactory);
}
