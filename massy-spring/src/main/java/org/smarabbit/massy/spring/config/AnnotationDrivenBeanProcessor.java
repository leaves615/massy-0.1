/**
 * 
 */
package org.smarabbit.massy.spring.config;

import java.util.Iterator;

import org.smarabbit.massy.Registration;
import org.smarabbit.massy.annotation.support.Definition;
import org.smarabbit.massy.spring.MassyApplicationContext;
import org.smarabbit.massy.spring.MassyResource;
import org.smarabbit.massy.util.Asserts;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.support.AbstractRefreshableApplicationContext;

/**
 * 注解驱动Bean解析器
 * @author huangkaihui
 *
 */
public class AnnotationDrivenBeanProcessor 
	extends BeanRegistryHandlerLoader
	implements DestructionAwareBeanPostProcessor, BeanFactoryPostProcessor, ApplicationContextAware, SmartLifecycle {
	
	private ApplicationContext applicationContext;
	private ConfigurableListableBeanFactory beanFactory;
	private volatile boolean running = false;
    private int phase = 0;
    private MassyResource resource;
    
    private BeanRegistrationManager registrationManager = 
    		new DefaultBeanRegistrationManager();
    	
	/**
	 * 
	 */
	public AnnotationDrivenBeanProcessor() {
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.BeanFactoryPostProcessor#postProcessBeanFactory(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
	 */
	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
			
		Class<?> beanType = bean.getClass();
		DefaultBeanDefinitionManager definitionManager = 
				DefaultBeanDefinitionManagerUtils.getBeanDefinitionManager(this.beanFactory);
		
		//按Definition执行注册
		Definition[] definitions = definitionManager.getDefinitions(beanName, beanType);
		if (definitions.length != 0){
			for (Definition definition: definitions){
				Iterator<BeanRegistryHandler> it = this.iterator();
				while (it.hasNext()){
					BeanRegistryHandler handler = it.next();
					if (handler.support(definition.getClass())){
						Registration registration = handler.register(beanName, this.beanFactory, definition, this.resource);
						if (registration != null){
							this.registrationManager.addRegistration(beanName, registration);
						}
					}
				}
			}
		}
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}
	
	@Override
	public void postProcessBeforeDestruction(Object bean, String beanName)
			throws BeansException {
		//清除并撤销Bean的所有注册
		this.registrationManager.unregister(beanName);
	}
			
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
		AbstractRefreshableApplicationContext appContext = 
				(AbstractRefreshableApplicationContext)applicationContext;
		this.beanFactory = appContext.getBeanFactory();
		if (this.applicationContext instanceof MassyApplicationContext){
			this.resource = ((MassyApplicationContext)this.applicationContext).getMassyResource();
		}
	}
	
	@Override
	public int getPhase() {
		return phase;
	}
	
	/**
     * Sets the phase in which handlers are subscribed and unsubscribed. Defaults to 0.
     *
     * @param phase The phase in which handlers are subscribed and unsubscribed
     */
    public void setPhase(int phase) {
        this.phase = phase;
    }

	@Override
	public void start() {
		running = true;
	}

	@Override
	public void stop() {
		running = false;
	}
	
	@Override
	public boolean isAutoStartup() {
		return true;
	}

	@Override
	public void stop(Runnable callback) {
		 stop();
	     callback.run();
	}

	@Override
	public boolean isRunning() {
		return this.running;
	}
	
	protected ApplicationContext getApplicationContext(){
		return this.applicationContext;
	}
	
	protected ConfigurableListableBeanFactory getBeanFactory(){
		return Asserts.ensureFieldInited(this.beanFactory, "beanFactory");
	}
}
