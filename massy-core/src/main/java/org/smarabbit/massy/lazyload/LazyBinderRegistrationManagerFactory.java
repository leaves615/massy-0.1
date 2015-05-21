/**
 * 
 */
package org.smarabbit.massy.lazyload;

import org.smarabbit.massy.service.ServiceRegistrationManager;
import org.smarabbit.massy.service.ServiceRegistrationManagerFactory;

/**
 * {@link LazyBinderRegistrationManager}工厂
 * @author huangkaihui
 */
@SuppressWarnings("rawtypes")
public class LazyBinderRegistrationManagerFactory implements
		ServiceRegistrationManagerFactory<LazyBinder> {

	/**
	 * 
	 */
	public LazyBinderRegistrationManagerFactory() {
		
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
	public Class<LazyBinder> getSupportType() {
		return LazyBinder.class;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistrationManagerFactory#create()
	 */
	@Override
	public ServiceRegistrationManager<LazyBinder> create() {
		return new LazyBinderRegistrationManager();
	}

	
	
}
