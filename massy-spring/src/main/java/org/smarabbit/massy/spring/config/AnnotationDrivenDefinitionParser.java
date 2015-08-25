/**
 * 
 */
package org.smarabbit.massy.spring.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * @author huangkaihui
 *
 */
public class AnnotationDrivenDefinitionParser implements BeanDefinitionParser {

	public static final String ANNOTATIONDRIVENBEANPROCESSOR = "_annotationDrivenBeanProcessor";
	
	/**
	 * 
	 */
	public AnnotationDrivenDefinitionParser() {
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.xml.BeanDefinitionParser#parse(org.w3c.dom.Element, org.springframework.beans.factory.xml.ParserContext)
	 */
	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		this.registerAnnotationDrivenBeanProcessor(element, parserContext);
		return null;
	}

	/**
	 * 注册{@link AnnotationDrivenBeanProcessor}
	 * @param element
	 * @param parserContext
	 */
	private synchronized void registerAnnotationDrivenBeanProcessor(Element element, 
			ParserContext parserContext) {
		RootBeanDefinition beanDefinition = null;
		try{
			beanDefinition = (RootBeanDefinition)parserContext.getRegistry()
					.getBeanDefinition(ANNOTATIONDRIVENBEANPROCESSOR);
		}catch(Exception e){
			beanDefinition = new RootBeanDefinition();
			beanDefinition.setBeanClass(AnnotationDrivenBeanProcessor.class);		
			parserContext.getRegistry().registerBeanDefinition(ANNOTATIONDRIVENBEANPROCESSOR, beanDefinition);
		}
	}
			
}
