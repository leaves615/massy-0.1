/**
 * 
 */
package org.smarabbit.massy.axon.spring;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;

/**
 * @author huangkaihui
 *
 */
public class SimpleCommandBusFactoryBean extends 	AutoRegistryFactoryBean<SimpleCommandBus>{
	
	public SimpleCommandBusFactoryBean() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.AbstractFactoryBean#getObjectType()
	 */
	@Override
	public Class<?> getObjectType() {
		return SimpleCommandBus.class;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.AbstractFactoryBean#createInstance()
	 */
	@Override
	protected SimpleCommandBus createInstance() throws Exception {
		return new SimpleCommandBus();
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.axon.AutoRegistryCommandBusFactoryBean#getServiceTypes()
	 */
	@Override
	protected Class<?>[] getServiceTypes() {
		return new Class<?>[]{CommandBus.class, SimpleCommandBus.class};
	}

}
