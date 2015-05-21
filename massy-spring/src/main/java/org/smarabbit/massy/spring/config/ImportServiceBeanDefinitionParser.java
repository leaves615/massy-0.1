/**
 * 
 */
package org.smarabbit.massy.spring.config;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * @author huangkaihui
 *
 */
public class ImportServiceBeanDefinitionParser extends
		AbstractBeanDefinitionParser {

	protected static final String ID = "id";
	protected static final String ALIAS = "alias";
	protected static final String SERVICETYPE = "serviceType";
	
	/**
	 * 
	 */
	public ImportServiceBeanDefinitionParser() {
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.xml.BeanDefinitionParser#parse(org.w3c.dom.Element, org.springframework.beans.factory.xml.ParserContext)
	 */
	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		String id = this.getAttribute(element, ID);
		String alias = this.getAttribute(element, ALIAS);
		String serviceType = this.getServiceType(element);
		
		GenericBeanDefinition beanDefinition =
				this.createImportServiceFactoryBean(serviceType, alias);
		parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
		return null;
	}
	
	protected GenericBeanDefinition createImportServiceFactoryBean(String serviceType, String alias){
		GenericBeanDefinition result = new GenericBeanDefinition();
		result.setBeanClass(ImportServiceFactoryBean.class);
		
		MutablePropertyValues propertyValues = new MutablePropertyValues();
		propertyValues.add("alias", alias);
		propertyValues.add("serviceType", serviceType);
		result.setPropertyValues(propertyValues);
		
		return result;
	}
	
	protected String getServiceType(Element element){
		return this.getAttribute(element, SERVICETYPE);
	}

}
