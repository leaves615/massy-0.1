/**
 * 
 */
package org.smarabbit.massy.spring.config;

import org.smarabbit.massy.util.Asserts;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.xml.ParserContext;

/**
 * @author huangkaihui
 *
 */
public abstract class DefaultBeanDefinitionManagerUtils {

	/**
	 * 获取{@link DefaultBeanDefinitionManager}实例。 <br>
	 * 如果实例不存在，则创建
	 * 
	 * @param parserContext
	 * @return
	 */
	protected static DefaultBeanDefinitionManager getBeanDefinitionManager(
			ParserContext parserContext) {
		Asserts.argumentNotNull(parserContext, "parserContext");
		BeanDefinition definition = parserContext
				.getRegistry()
				.getBeanDefinition(
						AnnotationDrivenDefinitionParser.ANNOTATIONDRIVENBEANPROCESSOR);

		DefaultBeanDefinitionManager result = (DefaultBeanDefinitionManager) definition
				.getAttribute(BeanDefinitionManager.class.getName());
		if (result == null) {
			result = new DefaultBeanDefinitionManager();
			definition
					.setAttribute(
							BeanDefinitionManager.class.getName(),
							result);
		}
		return result;
	}

	/**
	 * 获取{@link DefaultBeanDefinitionManager}实例
	 * 如果实例不存在，则创建
	 * @param factory
	 * @return
	 */
	protected static DefaultBeanDefinitionManager getBeanDefinitionManager(
			ConfigurableListableBeanFactory factory) {
		BeanDefinition definition = factory
				.getBeanDefinition(AnnotationDrivenDefinitionParser.ANNOTATIONDRIVENBEANPROCESSOR);
		DefaultBeanDefinitionManager result = (DefaultBeanDefinitionManager) definition
				.getAttribute(BeanDefinitionManager.class.getName());
		if (result == null) {
			result = new DefaultBeanDefinitionManager();
			definition
					.setAttribute(
							BeanDefinitionManager.class.getName(),
							result);
		}
		return result;
	}
}
