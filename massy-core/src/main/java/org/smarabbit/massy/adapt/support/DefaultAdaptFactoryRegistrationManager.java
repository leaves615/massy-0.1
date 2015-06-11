/**
 * 
 */
package org.smarabbit.massy.adapt.support;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.smarabbit.massy.adapt.AdaptFactoryRegistration;
import org.smarabbit.massy.adapt.AdaptFactoryRegistrationManager;

/**
 * {@link AdaptFactoryRegistrationManager}的缺省实现
 * @author huangkaihui
 *
 */
public class DefaultAdaptFactoryRegistrationManager<A> extends
		AbstractAdaptFactoryRegistrationManager<A> {

	private final List<AdaptFactoryRegistration<A>> registrationList
			= new CopyOnWriteArrayList<AdaptFactoryRegistration<A>>();
	
	public DefaultAdaptFactoryRegistrationManager() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see org.smarabbit.massy.support.AbstractRegistrationManager#doAdd(org.smarabbit.massy.Registration)
	 */
	@Override
	protected void doAdd(AdaptFactoryRegistration<A> registration) {
		this.registrationList.add(registration);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.support.AbstractRegistrationManager#doRemove(org.smarabbit.massy.Registration)
	 */
	@Override
	protected void doRemove(AdaptFactoryRegistration<A> registration) {
		this.registrationList.remove(registration);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.support.AbstractRegistrationManager#getRegistrations()
	 */
	@Override
	protected Collection<AdaptFactoryRegistration<A>> getRegistrations() {
		return this.registrationList;
	}

}
