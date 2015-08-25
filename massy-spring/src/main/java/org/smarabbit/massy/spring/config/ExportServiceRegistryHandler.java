/**
 * 
 */
package org.smarabbit.massy.spring.config;

import java.util.HashMap;
import java.util.Map;

import org.smarabbit.massy.Constants;
import org.smarabbit.massy.MassyUtils;
import org.smarabbit.massy.Registration;
import org.smarabbit.massy.annotation.support.Definition;
import org.smarabbit.massy.annotation.support.ExportServiceDefinition;
import org.smarabbit.massy.service.ServiceFactory;
import org.smarabbit.massy.service.ServiceRegistry;
import org.smarabbit.massy.spring.MassyResource;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * {@link ExportServiceDefinition}对应的注册处理器
 * 
 * @author huangkaihui
 */
public class ExportServiceRegistryHandler implements BeanRegistryHandler {

	protected static final ServiceRegistry SERVICEREGISTRY =
			MassyUtils.getDefaultContext().getService(ServiceRegistry.class);
		
	/**
	 * 
	 */
	public ExportServiceRegistryHandler() {

	}
	
	/* (non-Javadoc)
	 * @see org.smarabbit.massy.spring.config.BeanRegistryHandler#support(java.lang.Class)
	 */
	@Override
	public boolean support(Class<? extends Definition> definitionType) {
		//仅支持exprotserviceDefinition
		return ExportServiceDefinition.class == definitionType;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.spring.config.BeanRegistryHandler#register(java.lang.String, org.springframework.beans.factory.BeanFactory, org.smarabbit.massy.annotation.support.Definition)
	 */
	@Override
	public Registration register(String beanName, ConfigurableListableBeanFactory factory,
			Definition definition, MassyResource resource) {
		ExportServiceDefinition esd = (ExportServiceDefinition)definition;
		BeanDefinition beanDefinition = factory.getBeanDefinition(beanName);
		
		Map<String, Object> props = esd.getProperties();
		if (resource != null){
			//创建新的Map
			props = props == null ?
					new HashMap<String, Object>():
						new HashMap<String, Object>(props);
					
			if (resource != null){
				props.put(Constants.SERVICE_CONTAINER, "spring");
				props.put(Constants.SERVICE_CONFIGFILE, resource.getName());
			}
		}
		
		ServiceFactory service = beanDefinition.isPrototype() ?
				new PrototypeBeanServiceFactory(beanName, factory) :
					new BeanServiceFactory(beanName, factory);
										
		return SERVICEREGISTRY.register(esd.getServiceTypes(), service, props);
	}

}
