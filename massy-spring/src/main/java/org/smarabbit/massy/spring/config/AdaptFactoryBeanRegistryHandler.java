/**
 * 
 */
package org.smarabbit.massy.spring.config;

import org.smarabbit.massy.MassyUtils;
import org.smarabbit.massy.Registration;
import org.smarabbit.massy.adapt.AdaptFactory;
import org.smarabbit.massy.adapt.AdaptFactoryRepository;
import org.smarabbit.massy.annotation.support.AdaptDefinition;
import org.smarabbit.massy.annotation.support.Definition;
import org.smarabbit.massy.spring.MassyResource;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * {@link AdaptFactory}注解bean注册句柄
 * @author huangkaihui
 *
 */
public class AdaptFactoryBeanRegistryHandler implements BeanRegistryHandler {

	/**
	 * 
	 */
	public AdaptFactoryBeanRegistryHandler() {
		
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.spring.config.BeanRegistryHandler#support(java.lang.Class)
	 */
	@Override
	public boolean support(Class<? extends Definition> definitionType) {
		return definitionType == AdaptDefinition.class;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.spring.config.BeanRegistryHandler#register(java.lang.String, org.springframework.beans.factory.config.ConfigurableListableBeanFactory, org.smarabbit.massy.annotation.support.Definition, org.smarabbit.massy.spring.MassyResource)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Registration register(String beanName,
			ConfigurableListableBeanFactory factory, Definition definition,
			MassyResource resource) {
		AdaptDefinition ad = (AdaptDefinition)definition;
		Object service = factory.getBean(beanName);
		if (service instanceof AdaptFactory){
			AdaptFactoryRepository  repository = MassyUtils.getDefaultContext()
					.getService(AdaptFactoryRepository.class);
			return repository.register(ad.getAdaptType(), (AdaptFactory)service, null);
		}
		
		return null;
	}

}
