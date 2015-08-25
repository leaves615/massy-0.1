/**
 * 
 */
package org.smarabbit.massy.adapt.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.smarabbit.massy.adapt.AdaptFactoryRegistry;
import org.smarabbit.massy.service.ServiceRegistration;
import org.smarabbit.massy.service.support.AbstractServiceRegistration;
import org.smarabbit.massy.service.support.AbstractServiceRegistrationManager;
import org.smarabbit.massy.service.support.ServiceReferenceCounterFactory;
import org.smarabbit.massy.util.LogUtils;

/**
 * @author huangkaihui
 *
 */
public class DefaultAdaptFactoryRepositoryRegistrationManager extends
		AbstractServiceRegistrationManager<AdaptFactoryRegistry> {
	
	private List<ServiceRegistration> list;

	public DefaultAdaptFactoryRepositoryRegistrationManager() {
		super();
		this.list = new ArrayList<ServiceRegistration>();
		long id = ServiceReferenceCounterFactory.getReferenceCount().get();
		this.list.add(
				new RegistrationImpl(id, new Class<?>[]{AdaptFactoryRegistry.class},
						new DefaultAdaptFactoryRepository(), null)
				);
	}

	@Override
	protected void doAdd(ServiceRegistration registration) {
		if (LogUtils.isWarnEnabled()){
			LogUtils.warn("cannot add AdaptFactoryRepository,");
		}
	}

	@Override
	protected void doRemove(ServiceRegistration registration) {
		if (LogUtils.isWarnEnabled()){
			LogUtils.warn("cannot remove AdaptFactoryRepository,");
		}
		
	}

	@Override
	protected Collection<ServiceRegistration> getRegistrations() {
		return this.list;
	}

	private class RegistrationImpl extends AbstractServiceRegistration {

		public RegistrationImpl(long id, Class<?>[] serviceTypes,
				Object registedObject, Map<String, Object> props) {
			super(id, serviceTypes, registedObject, props);
		}

		@Override
		public void unregister() {
						
		}
		
	}
}
