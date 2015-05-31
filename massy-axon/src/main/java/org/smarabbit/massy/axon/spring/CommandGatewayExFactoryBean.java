/**
 * 
 */
package org.smarabbit.massy.axon.spring;

import java.util.HashMap;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.CommandGatewayFactoryBean;
import org.smarabbit.massy.Constants;
import org.smarabbit.massy.MassyUtils;
import org.smarabbit.massy.service.ServiceRegistration;
import org.smarabbit.massy.service.ServiceRegistry;
import org.smarabbit.massy.spring.MassyApplicationContext;
import org.smarabbit.massy.spring.MassyResource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author huangkaihui
 *
 */
public class CommandGatewayExFactoryBean extends CommandGatewayFactoryBean<CommandGateway>
	implements ApplicationContextAware, DisposableBean{

	private ApplicationContext applicationContext;
	private MassyResource resource;
	private ServiceRegistration registration;
	
	public CommandGatewayExFactoryBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.axonframework.commandhandling.gateway.CommandGatewayFactoryBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		super.afterPropertiesSet();
		
		CommandGateway service = this.getObject();
		this.registration = this.register(service);
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	@Override
	public void destroy() throws Exception {
		if (this.registration != null){
			this.registration.unregister();
			this.registration = null;
		}
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

	protected ServiceRegistration register(CommandGateway service){
		HashMap<String, Object> props = null;
		if (this.resource != null){
			props = new HashMap<String, Object>();
			props.put(Constants.SERVICE_CONTAINER, "spring");
			props.put(Constants.SERVICE_CONFIGFILE, this.resource.getName());
		}
		ServiceRegistry registry = MassyUtils.getDefaultContext().getService(ServiceRegistry.class);
		return registry.register(new Class<?>[]{CommandGateway.class}, service, props);
	}
}
