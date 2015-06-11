/**
 * 
 */
package org.smarabbit.massy.adapt.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.smarabbit.massy.Descriptor;
import org.smarabbit.massy.adapt.AdaptFactory;
import org.smarabbit.massy.adapt.AdaptFactoryRegistration;
import org.smarabbit.massy.adapt.AdaptFactoryRegistrationManager;
import org.smarabbit.massy.adapt.AdaptFactoryRegistry;
import org.smarabbit.massy.adapt.RegisterAdaptFactoryException;
import org.smarabbit.massy.spec.Specification;
import org.smarabbit.massy.util.Asserts;
import org.smarabbit.massy.util.LogUtils;

/**
 * @author huangkaihui
 *
 */
public class DefaultAdaptFactoryRepository extends
		AdaptFactoryRegistrationManagerInitializer implements
		AdaptFactoryRegistry {

	/**
	 * 
	 */
	public DefaultAdaptFactoryRepository() {
		
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.adapt.AdaptFactoryRepository#adapt(java.lang.Object, java.lang.Class)
	 */
	@Override
	public <A> A adapt(Object target, Class<A> adaptType) {
		Asserts.argumentNotNull(target, "target");
		Asserts.argumentNotNull(adaptType, "adaptType");

		// 适配处理
		AdaptFactoryRegistrationManager<A> manager = this
				.getRegistrationManager(adaptType);
		if (manager != null) {
			return (A) manager.getAdaptor(target);
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.adapt.AdaptFactoryRepository#adapt(java.lang.Object, java.lang.Class, org.smarabbit.massy.spec.Specification)
	 */
	@Override
	public <A> A adapt(Object target, Class<A> adaptType,
			Specification<Descriptor> spec) {
		Asserts.argumentNotNull(target, "target");
		Asserts.argumentNotNull(adaptType, "adaptType");
		Asserts.argumentNotNull(spec, "spec");
		
		// 适配处理
		AdaptFactoryRegistrationManager<A> manager = this
				.getRegistrationManager(adaptType);
		if (manager != null) {
			return (A) manager.getAdaptor(target, spec);
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.adapt.AdaptFactoryRepository#getAdaptFactoryDescriptors(java.lang.Class)
	 */
	@Override
	public Descriptor[] getAdaptFactoryDescriptors(Class<?> adaptType) {
		Asserts.argumentNotNull(adaptType, "adaptType");

		AdaptFactoryRegistrationManager<?> manager = this.getRegistrationManager(adaptType);
		return manager == null ? new Descriptor[0] : manager.getDescriptors();
	}
	
	/* (non-Javadoc)
	 * @see org.smarabbit.massy.adapt.AdaptFactoryRegistry#getSupports(java.lang.Class)
	 */
	@Override
	public Class<?>[] getAdaptTypesFromObject(Object object) {
		Asserts.argumentNotNull(object, "object");
		
		Class<?> clazz = object.getClass();
		
		List<Class<?>> c = new ArrayList<Class<?>>();
		Collection<AdaptFactoryRegistrationManager<?>> registrationManagers =
				this.managerMap.values();
		for (AdaptFactoryRegistrationManager<?> registrationManager : registrationManagers){
			if (registrationManager.supports(clazz)){
				c.add(registrationManager.getAdaptType());
			}
		}
		return c.toArray(new Class<?>[c.size()]);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.adapt.AdaptFactoryRepository#register(java.lang.Class, org.smarabbit.massy.adapt.AdaptFactory, java.util.Map)
	 */
	@Override
	public <A> AdaptFactoryRegistration<A> register(Class<A> adaptType,
			AdaptFactory<A> factory, Map<String, Object> props)
			throws RegisterAdaptFactoryException {
		Asserts.argumentNotNull(adaptType, "adaptType");
		Asserts.argumentNotNull(factory, "factory");

		// 创建RegistrationImpl
		AdaptFactoryRegistrationImpl<A> result = new AdaptFactoryRegistrationImpl<A>(
				AdaptFactoryReferenceCounterFactory.getReferenceCount().increase(),	
				adaptType, 
				factory, 
				props);

		AdaptFactoryRegistrationManager<A> manager = this.getRegistrationManager(adaptType);
		if (manager == null){
			manager = this.createRegistrationManager(adaptType);
		}
		manager.add(result);
		if (LogUtils.isInfoEnabled()) {
			LogUtils.info("register AdaptFactory:[adaptType="
					+ adaptType.getName() + ",adaptFactory="
					+ factory.getClass().getName() + ".");
		}
		
		return result;
	}
	
	/**
	 * 撤销注册
	 * 
	 * @param registration
	 */
	protected <A> void doUnregister(AdaptFactoryRegistrationImpl<A> registration) {
		Class<A> adaptType = registration.getAdaptType();
		AdaptFactoryRegistrationManager<A> manager = this.getRegistrationManager(adaptType);
		if (manager != null) {
			manager.remove(registration);
			if (LogUtils.isInfoEnabled()) {
				LogUtils.info("unregister AdaptFactory:[adaptType="
						+ adaptType.getName() + ",adaptFactory="
						+ registration.getAdaptFactory().getClass().getName()
						+ ".");
			}
		}
		
	}

	private class AdaptFactoryRegistrationImpl<A> extends
		AbstractAdaptFactoryRegistration<A> {
	
		public AdaptFactoryRegistrationImpl(long id, Class<A> adaptType,
				AdaptFactory<A> factory, Map<String, Object> props) {
			super(id, adaptType, factory, props);
		}
		
		/*
		 * (non-Javadoc)
		 * 
		 * @see org.smarabbit.massy.Registration#unregister()
		 */
		@Override
		public void unregister() {
			doUnregister(this);
		}
	}
}
