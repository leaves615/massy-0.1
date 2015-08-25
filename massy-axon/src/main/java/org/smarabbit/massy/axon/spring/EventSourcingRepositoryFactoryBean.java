/**
 * 
 */
package org.smarabbit.massy.axon.spring;

import org.axonframework.eventsourcing.EventSourcingRepository;
import org.springframework.beans.factory.config.AbstractFactoryBean;

/**
 * @author huangkaihui
 *
 */
public class EventSourcingRepositoryFactoryBean<T extends EventSourcingRepository<?>> extends AbstractFactoryBean<T> {

	public EventSourcingRepositoryFactoryBean() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.AbstractFactoryBean#getObjectType()
	 */
	@Override
	public Class<?> getObjectType() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.AbstractFactoryBean#createInstance()
	 */
	@Override
	protected T createInstance() throws Exception {
		return null;
	}

	
}
