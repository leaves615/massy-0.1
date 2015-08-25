/**
 * 
 */
package org.smarabbit.massy.service.support;

import java.util.Map;

import org.smarabbit.massy.Descriptor;
import org.smarabbit.massy.service.RegistState;
import org.smarabbit.massy.service.RegisterServiceException;
import org.smarabbit.massy.service.ServiceEvent;
import org.smarabbit.massy.service.ServiceFactory;
import org.smarabbit.massy.service.ServiceListener;
import org.smarabbit.massy.service.ServiceRegistration;
import org.smarabbit.massy.service.ServiceRegistrationManager;
import org.smarabbit.massy.service.ServiceRegistry;
import org.smarabbit.massy.service.ServiceTypeNotMatchException;
import org.smarabbit.massy.spec.Specification;
import org.smarabbit.massy.util.ArrayUtils;
import org.smarabbit.massy.util.Asserts;
import org.smarabbit.massy.util.LogUtils;

/**
 * @author huangkaihui
 *
 */
public class DefaultServiceRegistry extends
		ServiceRegistrationManagerInitializer implements ServiceRegistry {

	/**
	 * 
	 */
	public DefaultServiceRegistry() {

	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRepository#findService(java.lang.Class)
	 */
	@Override
	public <S> S findService(Class<S> serviceType) {
		Asserts.argumentNotNull(serviceType, "serviceType");
		ServiceRegistrationManager<S> registrationManager =  
				this.getRegistrationManager(serviceType);
		return registrationManager == null ? null : registrationManager.getDefaultService();
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRepository#findService(java.lang.Class, org.smarabbit.massy.Descriptor)
	 */
	@Override
	public <S> S findService(Class<S> serviceType, Descriptor descriptor) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRepository#findService(java.lang.Class, java.lang.String)
	 */
	@Override
	public <S> S findService(Class<S> serviceType, String alias) {
		Asserts.argumentNotNull(serviceType, "serviceType");
		Asserts.argumentNotNull(alias, "alias");
		ServiceRegistrationManager<S> registrationManager = 
				this.getRegistrationManager(serviceType);
		return registrationManager == null ? null : registrationManager.findService(alias);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRepository#findService(java.lang.Class, org.smarabbit.massy.spec.Specification)
	 */
	@Override
	public <S> S findService(Class<S> serviceType,
			Specification<Descriptor> spec) {
		Asserts.argumentNotNull(serviceType, "serviceType");
		Asserts.argumentNotNull(spec, "spec");
		ServiceRegistrationManager<S> registrationManager = 
				this.getRegistrationManager(serviceType);
		return registrationManager == null ? null : registrationManager.findService(spec);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRepository#getAllServices(java.lang.Class)
	 */
	@Override
	public <S> S[] getAllServices(Class<S> serviceType) {
		Asserts.argumentNotNull(serviceType, "serviceType");
		ServiceRegistrationManager<S> registrationManager = 
				this.getRegistrationManager(serviceType);
		return registrationManager == null ? 
				ArrayUtils.newArrayByClass(serviceType, 0): 
					registrationManager.getServices();
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRepository#findServiceDescriptor(java.lang.Class)
	 */
	@Override
	public Descriptor findServiceDescriptor(Class<?> serviceType) {
		Asserts.argumentNotNull(serviceType, "serviceType");
		
		ServiceRegistrationManager<?> registrationManager =  
				this.getRegistrationManager(serviceType);
		return registrationManager == null ? null : registrationManager.getDefaultServiceDescriptor();
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRepository#findServiceDescriptor(java.lang.Class, java.lang.String)
	 */
	@Override
	public Descriptor findServiceDescriptor(Class<?> serviceType, String alias) {
		Asserts.argumentNotNull(serviceType, "serviceType");
		
		ServiceRegistrationManager<?> registrationManager =  
				this.getRegistrationManager(serviceType);
		return registrationManager == null ? null : registrationManager.findServiceDescriptor(alias);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRepository#findServiceDescriptor(java.lang.Class, org.smarabbit.massy.spec.Specification)
	 */
	@Override
	public Descriptor findServiceDescriptor(Class<?> serviceType,
			Specification<Descriptor> spec) {
		Asserts.argumentNotNull(serviceType, "serviceType");
		
		ServiceRegistrationManager<?> registrationManager =  
				this.getRegistrationManager(serviceType);
		return registrationManager == null ? null : registrationManager.findServiceDescriptor(spec);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRepository#getServiceDescriptors(java.lang.Class)
	 */
	@Override
	public Descriptor[] getServiceDescriptors(Class<?> serviceType) {
		Asserts.argumentNotNull(serviceType, "serviceType");
		ServiceRegistrationManager<?> registrationManager = 
				this.getRegistrationManager(serviceType);
		return registrationManager == null ? 
				new Descriptor[0]: 
					registrationManager.getDescriptors();
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRepository#register(java.lang.Class, java.lang.Object, java.util.Map)
	 */
	@Override
	public <S> ServiceRegistration register(Class<S> serviceType, S service,
			Map<String, Object> props) throws RegisterServiceException {
		Asserts.argumentNotNull(serviceType, "serviceType");
		return this.register(new Class<?>[]{serviceType}, service, props);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRepository#register(java.lang.Class[], java.lang.Object, java.util.Map)
	 */
	@Override
	public ServiceRegistration register(Class<?>[] serviceTypes,
			Object service, Map<String, Object> props)
			throws RegisterServiceException {
		Asserts.argumentNotEmpty(serviceTypes, "serviceTypes");
		Asserts.argumentNotNull(service, "service");
		
		ServiceRegistrationImpl result = new ServiceRegistrationImpl(
				ServiceReferenceCounterFactory.getReferenceCount().increase(),
				serviceTypes, 
				service, 
				props);

		try{
			for (Class<?> serviceType : serviceTypes){
				this.validateServiceMatch(serviceType, service);
				
				ServiceRegistrationManager<?> manager = this.getRegistrationManager(serviceType);
				if (manager == null){
					manager =  this.createRegistrationManager(serviceType);
				}
				manager.add(result);
			}
		}catch(Exception e){
			this.doUnregister(result, false);
			throw new RegisterServiceException(e.getMessage(), e);
		}
		
		//日志
		if (LogUtils.isInfoEnabled()){
			for (Class<?> serviceType : serviceTypes){
				if (LogUtils.isInfoEnabled()){
					LogUtils.info("register service:[serviceType=" + serviceType.getName() + 
							",service=" + service.getClass().getName() + "].");
				}
			}
		}
		
		//发送服务注册事件
		this.notifyEvent(service, result.getDescriptor(), RegistState.REGIESTED);
		return result;
	}
	
	/**
	 * 撤销注册
	 * @param registration
	 * @param logged
	 */
	protected void doUnregister(ServiceRegistrationImpl registration, boolean logged){
		Class<?>[] serviceTypes = registration.getServiceTypes();
		
		for (Class<?> serviceType: serviceTypes){
			ServiceRegistrationManager<?> manager = this.getRegistrationManager(serviceType);
			if (manager != null){
				manager.remove(registration);
				if ((logged) && (LogUtils.isInfoEnabled())){
					LogUtils.info("remove service:[serviceType=" + serviceType.getName() + 
							",service=" + registration.getRegistedObject().getClass().getName() + ".");
				}
			}
		}
		
		if (logged){
			//发送服务注册事件
			this.notifyEvent(
					registration.getRegistedObject(), registration.getDescriptor(), RegistState.UNREGISTING);
		}
	}

	/**
	 * 验证服务和注册类型的匹配情况
	 * @param serviceType
	 * @param service
	 * @throws ServiceTypeNotMatchException
	 */
	protected void validateServiceMatch(Class<?> serviceType, Object service)
		throws ServiceTypeNotMatchException{
		if (service instanceof ServiceFactory) 
			return;
		
		if (!serviceType.isAssignableFrom(service.getClass())){
			throw new ServiceTypeNotMatchException(serviceType, service);
		}
	}
	
	/**
	 * 发送服务事件
	 * @param service
	 * @param serviceDescriptor
	 * @param state
	 */
	protected void notifyEvent(Object service, Descriptor serviceDescriptor, RegistState state){
		ServiceListenerRegistrationManager manager =
				(ServiceListenerRegistrationManager)this.managerMap.get(ServiceListener.class);
		if (manager != null){
			ServiceEvent event = new ServiceEvent(service, serviceDescriptor, state);
			manager.notifyServiceEvent(event);
		}
	}

	/**
	 * 服务注册凭据实现类
	 * @author huangkaihui
	 *
	 */
	private class ServiceRegistrationImpl extends AbstractServiceRegistration{

		public ServiceRegistrationImpl(long id, Class<?>[] serviceTypes,
				Object registedObject, Map<String, Object> props) {
			super(id, serviceTypes, registedObject, props);
		}

		/* (non-Javadoc)
		 * @see org.smarabbit.massy.Registration#unregister()
		 */
		@Override
		public void unregister() {
			doUnregister(this,true);
		}
	}
}
