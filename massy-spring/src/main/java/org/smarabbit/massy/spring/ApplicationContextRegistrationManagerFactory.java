/**
 * 
 */
package org.smarabbit.massy.spring;

import org.smarabbit.massy.service.ServiceRegistrationManager;
import org.smarabbit.massy.service.ServiceRegistrationManagerFactory;
import org.springframework.context.ApplicationContext;

/**
 * {@link ApplicationContextRegistrationManager}工厂
 * @author huangkaihui
 *
 */
public class ApplicationContextRegistrationManagerFactory implements
		ServiceRegistrationManagerFactory<ApplicationContext> {

	/**
	 * 
	 */
	public ApplicationContextRegistrationManagerFactory() {
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistrationManagerFactory#autoInit()
	 */
	@Override
	public boolean autoInit() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistrationManagerFactory#getSupportType()
	 */
	@Override
	public Class<ApplicationContext> getSupportType() {
		return ApplicationContext.class;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistrationManagerFactory#create()
	 */
	@Override
	public ServiceRegistrationManager<ApplicationContext> create() {
		return new ApplicationContextRegistrationManager();
	}

}
