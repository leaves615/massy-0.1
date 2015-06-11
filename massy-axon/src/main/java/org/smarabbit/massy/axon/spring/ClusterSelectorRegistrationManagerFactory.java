/**
 * 
 */
package org.smarabbit.massy.axon.spring;

import org.axonframework.eventhandling.ClusterSelector;
import org.smarabbit.massy.service.ServiceRegistrationManager;
import org.smarabbit.massy.service.ServiceRegistrationManagerFactory;
import org.smarabbit.massy.service.ServiceRegistrationManagerFactoryChain;
import org.smarabbit.massy.support.AutoInitializeRegistrationFactory;

/**
 * @author huangkaihui
 *
 */
public class ClusterSelectorRegistrationManagerFactory implements
		ServiceRegistrationManagerFactory, AutoInitializeRegistrationFactory {
	/**
	 * 
	 */
	public ClusterSelectorRegistrationManagerFactory() {
		
	}

	

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistrationManagerFactory#create(java.lang.Class, org.smarabbit.massy.service.ServiceRegistrationManagerFactoryChain)
	 */
	/* (non-Javadoc)
	 * @see org.smarabbit.massy.support.AutoInitializable#getSupport()
	 */
	@Override
	public Class<?> getSupportType() {
		return ClusterSelector.class;
	}

	@Override
	public ServiceRegistrationManager<?> create(Class<?> serviceType,
			ServiceRegistrationManagerFactoryChain chain) {
		return new ClusterSelectorRegistrationManager();
	}

}
