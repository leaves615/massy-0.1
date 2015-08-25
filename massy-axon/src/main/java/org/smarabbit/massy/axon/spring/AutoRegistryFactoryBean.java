/**
 * 
 */
package org.smarabbit.massy.axon.spring;

import java.util.HashMap;

import org.smarabbit.massy.Constants;
import org.smarabbit.massy.MassyUtils;
import org.smarabbit.massy.service.ServiceRegistration;
import org.smarabbit.massy.service.ServiceRegistry;
import org.smarabbit.massy.spring.MassyApplicationContext;
import org.smarabbit.massy.spring.MassyResource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author huangkaihui
 *
 */
public abstract class AutoRegistryFactoryBean<T> extends AbstractFactoryBean<T> 
	implements ApplicationContextAware {

	private ApplicationContext applicationContext;
	private MassyResource resource;
	private ServiceRegistration registration;

	public AutoRegistryFactoryBean(){
		
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.AbstractFactoryBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		
		T service = this.getObject();
		this.registration = this.register(service);
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.AbstractFactoryBean#destroyInstance(java.lang.Object)
	 */
	@Override
	protected void destroyInstance(T instance) throws Exception {
		if (this.registration != null){
			this.registration.unregister();
			this.registration = null;
		}
		super.destroyInstance(instance);
	}

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
		if (this.applicationContext instanceof MassyApplicationContext){
			this.resource = ((MassyApplicationContext)this.applicationContext).getMassyResource();
		}
	}
	
	protected ServiceRegistration register(T service){
		HashMap<String, Object> props = null;
		if (this.resource != null){
			props = new HashMap<String, Object>();
			props.put(Constants.SERVICE_CONTAINER, "spring");
			props.put(Constants.SERVICE_CONFIGFILE, this.resource.getName());
		}
		ServiceRegistry registry = MassyUtils.getDefaultContext().getService(ServiceRegistry.class);
		return registry.register(this.getServiceTypes(), service, props);
	}
	
	protected abstract Class<?>[] getServiceTypes();

	/**
	 * @return the applicationContext
	 */
	protected ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * @return the resource
	 */
	protected MassyResource getResource() {
		return resource;
	}
}
