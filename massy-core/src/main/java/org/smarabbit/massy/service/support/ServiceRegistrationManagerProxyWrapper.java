/**
 * 
 */
package org.smarabbit.massy.service.support;

import org.smarabbit.massy.Descriptor;
import org.smarabbit.massy.bytecode.proxy.ServiceProxyFactory;
import org.smarabbit.massy.service.ServiceRegistration;
import org.smarabbit.massy.service.ServiceRegistrationManager;
import org.smarabbit.massy.spec.Specification;

/**
 * ServiceRegistrationManager代理封装
 * <br>
 * 封装ServiceRegistrationManager,并对其返回的服务创建对应的代理。
 * @author huangkaihui
 *
 */
public class ServiceRegistrationManagerProxyWrapper<S> extends
		ServiceRegistrationManagerWrapper<S> {

	public ServiceRegistrationManagerProxyWrapper(
			ServiceRegistrationManager<S> registrationManager) {
		super(registrationManager);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistrationManager#getDefaultService()
	 */
	@Override
	public S getDefaultService() {
		S service = this.getServiceRegistrationManager().getDefaultService();
		return service == null ? null : this.createProxy(service);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistrationManager#findService(org.smarabbit.massy.Descriptor)
	 */
	@Override
	public S findService(Descriptor descriptor) {
		S service = this.getServiceRegistrationManager().findService(descriptor);
		return service == null ? null : this.createProxy(service);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistrationManager#getServices()
	 */
	@Override
	public S[] getServices() {
		S[] result = this.getServiceRegistrationManager().getServices();
		int size = result.length;
		if (size > 0){
			for (int i=0; i<size; i++){
				result[i] = this.createProxy(result[i]);
			}
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistrationManager#findService(java.lang.String)
	 */
	@Override
	public S findService(String alias) {
		S service = this.getServiceRegistrationManager().findService(alias);
		return service == null ? null : this.createProxy(service);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistrationManager#findService(org.smarabbit.massy.spec.Specification)
	 */
	@Override
	public S findService(Specification<Descriptor> spec) {
		S service = this.getServiceRegistrationManager().findService(spec);
		return service == null ? null : this.createProxy(service);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.RegistrationManager#add(org.smarabbit.massy.Registration)
	 */
	@Override
	public void add(ServiceRegistration registration) {
		this.getServiceRegistrationManager().add(registration);		
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.RegistrationManager#remove(org.smarabbit.massy.Registration)
	 */
	@Override
	public void remove(ServiceRegistration registration) {
		this.getServiceRegistrationManager().remove(registration);
	}

	protected S createProxy(S delegate){
		try{
			return ServiceProxyFactory.createProxy(this.getServiceType(), delegate);
		}catch(Exception e){
			throw new RuntimeException("create proxy failed.", e);
		}
	}
}
