/**
 * 
 */
package org.smarabbit.massy.spring;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.smarabbit.massy.Descriptor;
import org.smarabbit.massy.service.ServiceRegistration;
import org.smarabbit.massy.service.support.AbstractServiceRegistrationManager;
import org.smarabbit.massy.spec.Specification;
import org.springframework.context.ApplicationContext;

/**
 * {@link ApplicationContext}服务注册管理器。
 * <br>
 * 不提供查询{@link ApplicationContext}服务的实现。
 * 
 * @author huangkaihui
 *
 */
public final class ApplicationContextRegistrationManager extends
		AbstractServiceRegistrationManager<ApplicationContext> {

	private List<ServiceRegistration> registrationList =
			new CopyOnWriteArrayList<ServiceRegistration>();
	
	/**
	 * 
	 */
	public ApplicationContextRegistrationManager() {
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.support.AbstractServiceRegistrationManager#getDefaultService()
	 */
	@Override
	public ApplicationContext getDefaultService() {
		//不提供ApplicationContext服务的查找
		return null;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.support.AbstractServiceRegistrationManager#getServices()
	 */
	@Override
	public ApplicationContext[] getServices() {
		//不提供ApplicationContext服务的查找
		return null;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.support.AbstractServiceRegistrationManager#findService(java.lang.String)
	 */
	@Override
	public ApplicationContext findService(String alias) {
		//不提供ApplicationContext服务的查找
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.support.AbstractServiceRegistrationManager#findService(org.smarabbit.massy.spec.Specification)
	 */
	@Override
	public ApplicationContext findService(Specification<Descriptor> spec) {
		//不提供ApplicationContext服务的查找
		return null;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.support.AbstractRegistrationManager#getDescriptors()
	 */
	@Override
	public Descriptor[] getDescriptors() {
		return super.getDescriptors();
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.support.AbstractRegistrationManager#doAdd(org.smarabbit.massy.Registration)
	 */
	@Override
	protected void doAdd(ServiceRegistration registration) {
		this.registrationList.add(registration);
	}


	/* (non-Javadoc)
	 * @see org.smarabbit.massy.support.AbstractRegistrationManager#doRemove(org.smarabbit.massy.Registration)
	 */
	@Override
	protected void doRemove(ServiceRegistration registration) {
		this.registrationList.remove(registration);
	}


	/* (non-Javadoc)
	 * @see org.smarabbit.massy.support.AbstractRegistrationManager#getRegistrations()
	 */
	@Override
	protected Collection<ServiceRegistration> getRegistrations() {
		return this.registrationList;
	}


}
