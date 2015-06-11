/**
 * 
 */
package org.smarabbit.massy.service.support;

import org.smarabbit.massy.Descriptor;
import org.smarabbit.massy.service.ServiceRegistrationManager;
import org.smarabbit.massy.spec.Specification;
import org.smarabbit.massy.util.Asserts;

/**
 * {@link ServiceRegistrationManager}封装器
 * @author huangkaihui
 *
 */
public abstract class ServiceRegistrationManagerWrapper<S>  implements ServiceRegistrationManager<S> {

	private ServiceRegistrationManager<S> registrationManager;
	
	/**
	 * 
	 */
	public ServiceRegistrationManagerWrapper(ServiceRegistrationManager<S> registrationManager) {
		Asserts.argumentNotNull(registrationManager, "registration");
		this.registrationManager = registrationManager;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.RegistrationManager#getDescriptors()
	 */
	@Override
	public Descriptor[] getDescriptors() {
		return this.registrationManager.getDescriptors();
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistrationManager#bind(java.lang.Class)
	 */
	@Override
	public void bind(Class<S> serviceType) {
		this.registrationManager.bind(serviceType);		
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistrationManager#getServiceType()
	 */
	@Override
	public Class<S> getServiceType() {
		return this.registrationManager.getServiceType();
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistrationManager#getDefaultServiceDescriptor()
	 */
	@Override
	public Descriptor getDefaultServiceDescriptor() {
		return this.registrationManager.getDefaultServiceDescriptor();
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistrationManager#findServiceDescriptor(java.lang.String)
	 */
	@Override
	public Descriptor findServiceDescriptor(String alias) {
		return this.registrationManager.findServiceDescriptor(alias);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistrationManager#findServiceDescriptor(org.smarabbit.massy.spec.Specification)
	 */
	@Override
	public Descriptor findServiceDescriptor(Specification<Descriptor> spec) {
		return this.registrationManager.findServiceDescriptor(spec);
	}

	protected ServiceRegistrationManager<S> getServiceRegistrationManager(){
		return this.registrationManager;
	}
}
