/**
 * 
 */
package org.smarabbit.massy.spring.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.smarabbit.massy.annotation.support.ExportServiceDefinition;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Export-Service 定义解析器
 * @author huangkaihui
 *
 */
public class ExportServiceBeanDefinitionParser extends
		AbstractBeanDefinitionParser {
		
	private static final String ANONYMOUS = "anonymous";
	protected static final ClassLoader LOADER = Thread.currentThread().getContextClassLoader();
	
	protected static final String ID = "id";
	protected static final String REF = "ref";
	protected static final String SERVICE_TYPE = "massy:service-type";
	protected static final String CLASSNAME = "class";
	
	protected static final String SERVICE_PROPERTY = "massy:service-property";
	protected static final String NAME = "name";
	protected static final String VALUE = "value";
		
	/**
	 * 
	 */
	public ExportServiceBeanDefinitionParser() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.xml.BeanDefinitionParser#parse(org.w3c.dom.Element, org.springframework.beans.factory.xml.ParserContext)
	 */
	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		try{
			//别名等于beanId;
			String alias = this.getAttribute(element, "id");		
			String beanName = this.getAttribute(element, REF);
			Class<?>[] serviceTypes = this.getServiceTypes(element);
			Map<String, Object> props = this.getProperties(element);
			
			BeanDefinition refDefinition = parserContext.getRegistry().getBeanDefinition(beanName);
			if (refDefinition == null){
				throw new FatalBeanException("bean cannot found: beanName=" + beanName + ".");
			}
			
			if (refDefinition.getBeanClassName() != null){
				Class<?> beanType = LOADER.loadClass(refDefinition.getBeanClassName());
				ExportServiceDefinition definition =
						this.createDefinition(beanType.getName(), serviceTypes,  alias, props);
				DefaultBeanDefinitionManager definitionManager =
						DefaultBeanDefinitionManagerUtils.getBeanDefinitionManager(parserContext);
				definitionManager.addDefinition(beanName, beanType, definition);
			}
		}catch(Exception e){
			throw new FatalBeanException(e.getMessage(), e);
		}
		
		return null;
	}
	
	/**
	 * 创建{@link ExportServiceDefinition}
	 * @param serviceTypeNames 
	 * @param alias
	 * @param props
	 * @return
	 */
	protected ExportServiceDefinition createDefinition(String declaringTypeName, Class<?>[] serviceTypes, String alias, 
			Map<String, Object> props){
		ExportServiceDefinition result =
				new ExportServiceDefinition(declaringTypeName, serviceTypes, props);
						
		if (alias != null){
			result.setAlias(alias);
		}
		
		return result;
	}
	
	/*
	 * 获取输出服务类型集合
	 */
	protected Class<?>[] getServiceTypes(Element element) throws Exception{
		List<Class<?>> result = null;
		
		NodeList nodes = element.getElementsByTagName(SERVICE_TYPE);
		int size = nodes.getLength();
		for (int i=0; i<size; i++){
			Node node = nodes.item(i);
		
			String className = node.getAttributes().getNamedItem(CLASSNAME).getNodeValue();
			if (!StringUtils.isEmpty(className)){
				if (result == null){
					result = new ArrayList<Class<?>>();
				}
				result.add(LOADER.loadClass(className));
			}
		}
		
		return result.toArray(new Class<?>[result.size()]);
	}
	
	/**
	 * 获取属性Map
	 * @param element
	 * @return
	 */
	protected Map<String, Object> getProperties(Element element){
		Map<String, Object> result = null;
		
		NodeList nodes = element.getElementsByTagName(SERVICE_PROPERTY);
		int size = nodes.getLength();
		for (int i=0; i<size; i++){
			Node node = nodes.item(i);
			
			String name = node.getAttributes().getNamedItem(NAME).getNodeValue();
			String value = node.getAttributes().getNamedItem(VALUE).getNodeValue();
			
			if (result == null){
				result = new HashMap<String, Object>();
			}
			result.put(name, value);
		}
		
		return result;
	}
	
	/**
	 * 获取匿名Bean名称
	 * @return
	 */
	protected String getAnonymousBeanName(){
		return  "_" + ANONYMOUS + "_" +UUID.randomUUID().toString();
	}

}
