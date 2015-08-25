/**
 * 
 */
package org.smarabbit.massy.axon.spring;

import java.util.HashMap;
import java.util.Map;

import org.axonframework.eventhandling.ClusterSelector;
import org.smarabbit.massy.Constants;
import org.smarabbit.massy.MassyUtils;
import org.smarabbit.massy.service.ServiceRegistration;
import org.smarabbit.massy.service.ServiceRegistry;
import org.smarabbit.massy.spring.MassyApplicationContext;
import org.smarabbit.massy.spring.MassyResource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author huangkaihui
 *
 */
public class AxonBeanFactoryProcessor implements BeanFactoryPostProcessor, ApplicationContextAware,
	DestructionAwareBeanPostProcessor{

	private Map<String,ServiceRegistration> registrationMap =
			new HashMap<String, ServiceRegistration>();
	
	private ApplicationContext applicationContext;
	private ConfigurableListableBeanFactory factory;
	private MassyResource resource;
	
	private String eventBus = "eventBus";
	
	/**
	 * 
	 */
	public AxonBeanFactoryProcessor() {
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.BeanFactoryPostProcessor#postProcessBeanFactory(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
	 */
	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		this.factory = beanFactory;
		
		BeanDefinition definition = this.factory.getBeanDefinition(this.eventBus);
		if (definition != null){
			if (!definition.getConstructorArgumentValues().isEmpty()){
				//非服务引入Bean
				definition.getConstructorArgumentValues().clear();
			
				definition.getConstructorArgumentValues()
					.addIndexedArgumentValue(0, 
						BeanDefinitionBuilder.genericBeanDefinition(ClusterServiceSelector.class).getBeanDefinition());
			}
		}
	}
		
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessBeforeInitialization(java.lang.Object, java.lang.String)
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		Class<?> beanType = bean.getClass();
		if (beanType != ClusterServiceSelector.class){
			if (ClusterSelector.class.isAssignableFrom(beanType)){
				this.register(beanName, bean);
			}
		}
		return bean;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(java.lang.Object, java.lang.String)
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor#postProcessBeforeDestruction(java.lang.Object, java.lang.String)
	 */
	@Override
	public void postProcessBeforeDestruction(Object bean, String beanName)
			throws BeansException {
		ServiceRegistration registration = this.registrationMap.remove(beanName);
		if (registration != null){
			registration.unregister();
		}
	}
	
	protected void register(String beanName, Object bean){
		ServiceRegistry registry = MassyUtils.getDefaultContext().getService(ServiceRegistry.class);
		
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("beanName", beanName);
		if (this.resource != null){
			props.put(Constants.SERVICE_CONTAINER, "spring");
			props.put(Constants.SERVICE_CONFIGFILE, this.resource.getName());
		}
		
		ServiceRegistration registration = registry.register(ClusterSelector.class, (ClusterSelector)bean, props);
		if (registration != null){
			this.registrationMap.put(beanName, registration);
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
		if (this.applicationContext != null){
			if (this.applicationContext instanceof MassyApplicationContext){
				this.resource = ((MassyApplicationContext)this.applicationContext).getMassyResource();
			}
		}
	}

	/**
	 * @return the eventBus
	 */
	protected String getEventBus() {
		return eventBus;
	}

	/**
	 * @param eventBus the eventBus to set
	 */
	protected void setEventBus(String eventBus) {
		this.eventBus = eventBus;
	}

	
}
