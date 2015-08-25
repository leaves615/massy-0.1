/**
 * 
 */
package org.smarabbit.massy.axon.spring;

import java.util.concurrent.TimeUnit;

import org.axonframework.eventhandling.async.RetryPolicy;
import org.springframework.beans.factory.config.AbstractFactoryBean;

/**
 * {@link RetryPollicy}bean工厂
 * @author huangkaihui
 *
 */
public class RetryPolicyFactoryBean extends AbstractFactoryBean<RetryPolicy> {

	public RetryPolicyFactoryBean() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.AbstractFactoryBean#getObjectType()
	 */
	@Override
	public Class<?> getObjectType() {
		return RetryPolicy.class;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.AbstractFactoryBean#createInstance()
	 */
	@Override
	protected RetryPolicy createInstance() throws Exception {
	
		return RetryPolicy.retryAfter(30, TimeUnit.MILLISECONDS);
	}

	
}
