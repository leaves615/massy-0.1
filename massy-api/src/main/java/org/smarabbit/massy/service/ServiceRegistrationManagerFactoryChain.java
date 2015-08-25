/**
 * 
 */
package org.smarabbit.massy.service;


/**
 * @author huangkaihui
 *
 */
public interface ServiceRegistrationManagerFactoryChain {

	<S> ServiceRegistrationManager<S> proceed(Class<S> serviceType);
}
