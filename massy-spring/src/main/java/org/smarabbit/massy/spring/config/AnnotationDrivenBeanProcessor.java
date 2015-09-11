/**
 * 
 */
package org.smarabbit.massy.spring.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.smarabbit.massy.Constants;
import org.smarabbit.massy.Registration;
import org.smarabbit.massy.annotation.support.Definition;
import org.smarabbit.massy.annotation.support.ExportServiceDefinition;
import org.smarabbit.massy.spring.MassyApplicationContext;
import org.smarabbit.massy.spring.MassyResource;
import org.smarabbit.massy.util.Asserts;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.AbstractRefreshableApplicationContext;

/**
 * 注解驱动Bean解析器
 * @author huangkaihui
 *
 */
public class AnnotationDrivenBeanProcessor 
	extends BeanRegistryHandlerLoader
	implements DestructionAwareBeanPostProcessor, BeanFactoryPostProcessor, ApplicationContextAware, 
	ApplicationListener<ApplicationEvent>, SmartLifecycle {
	
	private ApplicationContext applicationContext;
	private ConfigurableListableBeanFactory beanFactory;
	private volatile boolean running = false;
    private int phase = 0;
    private MassyResource resource;
    
    private Map<String, ProcessingPoint> beanNameMap;
    
    private BeanRegistrationManager registrationManager = 
    		new DefaultBeanRegistrationManager();
    	
	/**
	 * 
	 */
	public AnnotationDrivenBeanProcessor() {
		this.beanNameMap = new HashMap<String, ProcessingPoint>();
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.BeanFactoryPostProcessor#postProcessBeanFactory(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
	 */
	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;		
	}
	
	private void processBeanDefinition(String beanName, Class<?> beanType, 
			DefaultBeanDefinitionManager definitionManager, ConfigurableListableBeanFactory beanFactory){
		Iterator<DefinitionProcessor> it = this.defintionProcessorIterator();
		while (it.hasNext()){
			DefinitionProcessor processor = it.next();
			Definition definition = processor.discovry(beanName, beanType, beanFactory);
			
			if (definition != null){
				if (definition instanceof ExportServiceDefinition){
					ExportServiceDefinition esd = (ExportServiceDefinition)definition;
					Map<String, Object> props = esd.getProperties();
					props.put(Constants.SERVICE_CONTAINER, "spring");
					props.put(Constants.SERVICE_CONFIGFILE, this.resource.getName());
				}
				
				definitionManager.addDefinition(beanName, beanType, definition);
			}
		}
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {		
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		
		Class<?> beanType = bean.getClass();
				
		//避免发生循环引用问题，所以FactoryBean在最后处理
		if (!FactoryBean.class.isAssignableFrom(beanType)){
			DefaultBeanDefinitionManager definitionManager = 
					DefaultBeanDefinitionManagerUtils.getBeanDefinitionManager(this.beanFactory);
			
			if (!this.beanNameMap.containsKey(beanName)){
				this.processBeanDefinition(beanName, beanType, definitionManager, this.getBeanFactory());	
				this.beanNameMap.put(beanName, ProcessingPoint.BEANPROCESSOR);
			}
			
			this.doRegister(beanName, beanType, beanFactory, definitionManager);
		}
		
		return bean;
	}
	
	@Override
	public void postProcessBeforeDestruction(Object bean, String beanName)
			throws BeansException {
		//清除并撤销Bean的所有注册
		this.doUnregister(beanName);
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
	
	

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 */
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ContextRefreshedEvent){
			this.onContextRefreshed((ContextRefreshedEvent)event);
		}
		
		if (event instanceof ContextClosedEvent){
			this.onContextClosed((ContextClosedEvent)event);
		}
	}
	
	/**
	 * Application Refreshed 事件处理
	 * <br>
	 * 如果Bean在其他BeanFactoryPostProcessor中被实例化，
	 * 则BeanPostProcessor无法拦截该Bean的初始化方法。
	 * 本方法则对该类的Bean补充需要注册处理的过程
	 * @param event
	 */
	protected void onContextRefreshed(ContextRefreshedEvent event){
		DefaultBeanDefinitionManager definitionManager = 
				DefaultBeanDefinitionManagerUtils.getBeanDefinitionManager(this.beanFactory);
		
		String[] beanNames = this.beanFactory.getBeanDefinitionNames();
		int size = beanNames.length;
		for (int i=0; i<size; i++){
			String beanName = beanNames[i];
			//检查bean的处理点
			if (!this.beanNameMap.containsKey(beanName)){
				this.beanNameMap.put(beanName, ProcessingPoint.CONTEXTREFRESHED);
				Class<?> beanType = this.beanFactory.getType(beanName);
				if (beanType != null){
					this.doRegister(beanName, beanType, beanFactory, definitionManager);
				}
			}
		}
	}
	
	/**
	 * 执行注册
	 * @param beanName bean名称
	 * @param beanType bean类型
	 * @param beanFactory bean工厂
	 * @param definitionManager 定义管理器
	 */
	protected void doRegister(String beanName, Class<?> beanType, ConfigurableListableBeanFactory beanFactory, 
			DefaultBeanDefinitionManager definitionManager){		
		//按Definition执行注册
		if (FactoryBean.class.isAssignableFrom(beanType)){
			FactoryBean<?> factoryBean = (FactoryBean<?>)beanFactory.getBean("&" + beanName);
			beanType = factoryBean.getObjectType();
		}
		Definition[] definitions = definitionManager.getDefinitions(beanName, beanType);
		if (definitions.length != 0){
			for (Definition definition: definitions){
				Iterator<BeanRegistryHandler> it = this.beanRegistryHandlerIterator();
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
	}
	
	/**
	 * Application Closed 事件处理
	 * <br>
	 * 如果Bean在其他BeanFactoryPostProcessor中被实例化，
	 * 则DestructionAwareBeanPostProcessor无法拦截该Bean的释放方法。
	 * 本方法则对该类的Bean补充需要取消注册处理的过程
	 * @param event
	 */
	protected void onContextClosed(ContextClosedEvent event){
		String[] beanNames = this.beanFactory.getBeanDefinitionNames();
		int size = beanNames.length;
		for (int i=0; i<size; i++){
			String beanName = beanNames[i];
			ProcessingPoint point = this.beanNameMap.get(beanName);
			if (ProcessingPoint.CONTEXTREFRESHED.equals(point)){
				this.beanNameMap.remove(beanName);
				this.doUnregister(beanName);
			}
		}	
	}
	
	/**
	 * 取消注册
	 * @param beanName
	 */
	protected void doUnregister(String beanName){
		this.registrationManager.unregister(beanName);
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
	
	/**
	 * 处理点
	 * @author huangkaihui
	 *
	 */
	private enum ProcessingPoint {
		
		/**
		 * 在Bean处理器中处理
		 */
		BEANPROCESSOR,
		
		/**
		 * 在ContextRefreshed事件中处理
		 */
		CONTEXTREFRESHED
	}
}
