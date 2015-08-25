/**
 * 
 */
package org.smarabbit.massy.service.support;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.smarabbit.massy.service.ServiceRegistration;

/**
 * 通用的缺省服务注册凭据管理器
 * @author huangkaihui
 *
 */
public class DefaultServiceRegistrationManager<S> extends
		AbstractServiceRegistrationManager<S> {

	private List<ServiceRegistration> registrationList =
			new CopyOnWriteArrayList<ServiceRegistration>();
	
	public DefaultServiceRegistrationManager() {
		super();
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
