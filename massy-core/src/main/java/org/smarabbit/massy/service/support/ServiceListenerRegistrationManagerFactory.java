/**
 * 
 */
package org.smarabbit.massy.service.support;

import org.smarabbit.massy.service.ServiceListener;
import org.smarabbit.massy.service.ServiceRegistrationManager;
import org.smarabbit.massy.service.ServiceRegistrationManagerFactory;

/**
 * {@link ServiceListenerRegistrationManager}工厂
 * @author huangkaihui
 *
 */
public class ServiceListenerRegistrationManagerFactory implements
		ServiceRegistrationManagerFactory<ServiceListener> {

	/**
	 * 
	 */
	public ServiceListenerRegistrationManagerFactory() {
		
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
	public Class<ServiceListener> getSupportType() {
		return ServiceListener.class;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistrationManagerFactory#create()
	 */
	@Override
	public ServiceRegistrationManager<ServiceListener> create() {
		return new ServiceListenerRegistrationManager();
	}

	
}
