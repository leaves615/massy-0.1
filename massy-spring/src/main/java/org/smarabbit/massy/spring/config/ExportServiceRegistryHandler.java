/**
 * 
 */
package org.smarabbit.massy.spring.config;

import org.smarabbit.massy.MassyUtils;
import org.smarabbit.massy.Registration;
import org.smarabbit.massy.annotation.support.Definition;
import org.smarabbit.massy.annotation.support.ExportServiceDefinition;
import org.smarabbit.massy.service.ServiceFactory;
import org.smarabbit.massy.service.ServiceRepository;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * {@link ExportServiceDefinition}对应的注册处理器
 * 
 * @author huangkaihui
 */
public class ExportServiceRegistryHandler implements BeanRegistryHandler {

	protected static final ServiceRepository SERVICEREPOSITORY =
			MassyUtils.getDefaultContext().getService(ServiceRepository.class);
	
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
			Definition definition) {
		ExportServiceDefinition esd = (ExportServiceDefinition)definition;
		BeanDefinition beanDefinition = factory.getBeanDefinition(beanName);
		ServiceFactory service = beanDefinition.isPrototype() ?
				new PrototypeBeanServiceFactory(beanName, factory) :
					new BeanServiceFactory(beanName, factory);
										
		return SERVICEREPOSITORY.register(esd.getServiceTypes(), service, esd.getProperties());
	}

}
