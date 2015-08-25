/**
 * 
 */
package org.smarabbit.massy.spring.config;

import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * 抽象的{@link BeanDefinition}解析器
 * @author huangkaihui
 *
 */
public abstract class AbstractBeanDefinitionParser implements
		BeanDefinitionParser {

	/**
	 * 
	 */
	public AbstractBeanDefinitionParser() {
	}

	/**
	 * 获取属性值
	 * @param element 
	 * 						{@link Elment}，不能为null.
	 * @param attributeName 
	 * 						属性名，不能为null.
	 * @return 
	 * 			字符串,去除空格符号的属性值
	 */
	protected String getAttribute(Element element, String attributeName){
		String result = StringUtils.trimAllWhitespace(element.getAttribute(attributeName));
		return StringUtils.isEmpty(result) ? null: result;
	}
}
