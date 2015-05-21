/**
 * 
 */
package org.smarabbit.massy.lazyload;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import org.smarabbit.massy.Descriptor;
import org.smarabbit.massy.annotation.support.LazyBindDefinition;
import org.smarabbit.massy.service.DefinitionEqualSpecification;
import org.smarabbit.massy.service.RegisterServiceException;
import org.smarabbit.massy.service.ServiceRegistration;
import org.smarabbit.massy.service.support.AbstractServiceRegistrationManager;
import org.smarabbit.massy.spec.Specification;

/**
 * {@link LazyBinder}服务注册管理器
 * @author huangkaihui
 *
 */
@SuppressWarnings("rawtypes")
public class LazyBinderRegistrationManager extends
		AbstractServiceRegistrationManager<LazyBinder> {

	private ConcurrentHashMap<LazyBindDefinition, ServiceRegistration>  registrationMap =
			new ConcurrentHashMap<LazyBindDefinition, ServiceRegistration>();
	
	public LazyBinderRegistrationManager() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.support.AbstractServiceRegistrationManager#doFind(java.util.Collection, org.smarabbit.massy.spec.Specification)
	 */
	@Override
	protected ServiceRegistration doFind(Collection<ServiceRegistration> list,
			Specification<Descriptor> spec) {
		if (spec instanceof DefinitionEqualSpecification){
			return this.registrationMap.get(((DefinitionEqualSpecification)spec).getDefinition());
		}
		return super.doFind(list, spec);
	}



	/* (non-Javadoc)
	 * @see org.smarabbit.massy.support.AbstractRegistrationManager#doAdd(org.smarabbit.massy.Registration)
	 */
	@Override
	protected void doAdd(ServiceRegistration registration) {
			LazyBindDefinition definition = this.getServiceFromRegistration(registration).getDefinition();
						
			if (this.registrationMap.contains(definition)){
				throw new RegisterServiceException("already exist same lazyBinder instance: " + definition + ".");
			}
			
			this.registrationMap.putIfAbsent(definition, registration);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.support.AbstractRegistrationManager#doRemove(org.smarabbit.massy.Registration)
	 */
	@Override
	protected void doRemove(ServiceRegistration registration) {
		LazyBindDefinition definition = this.getServiceFromRegistration(registration).getDefinition();
		this.registrationMap.remove(definition);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.support.AbstractRegistrationManager#getRegistrations()
	 */
	@Override
	protected Collection<ServiceRegistration> getRegistrations() {
		return this.registrationMap.values();
	}

	
}
