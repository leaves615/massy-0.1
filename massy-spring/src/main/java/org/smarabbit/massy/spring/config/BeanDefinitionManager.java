/**
 * 
 */
package org.smarabbit.massy.spring.config;

import org.smarabbit.massy.annotation.support.Definition;

/**
 * Bean定义管理器
 * @author huangkaihui
 *
 */
public interface BeanDefinitionManager {

	static final Definition[] EMPTY = new Definition[0];
			
	/**
	 * 获取bean所有的{@link Definition}
	 * @param beanName bean名称
	 * @param beanType 类型
	 * @return
	 * 			{@link Definition}数组,无{@link Definition}返回empty.
	 */
	Definition[] getDefinitions(String beanName, Class<?> beanType);

}
