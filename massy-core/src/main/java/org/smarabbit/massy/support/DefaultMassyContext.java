/**
 * 
 */
package org.smarabbit.massy.support;

import java.util.Map;

import org.smarabbit.massy.Descriptor;
import org.smarabbit.massy.MassyContext;
import org.smarabbit.massy.ServiceNotFoundException;
import org.smarabbit.massy.service.ServiceRepository;
import org.smarabbit.massy.service.support.DefaultServiceRepository;
import org.smarabbit.massy.util.Asserts;

/**
 * {@link MassyContext}实现类
 * 
 * @author huangkh
 *
 */
public class DefaultMassyContext implements MassyContext {

	private final ServiceRepository serviceRepository;
	private final Descriptor descriptor;
	
	public DefaultMassyContext(Map<String, Object> initParams){
		this(initParams, new DefaultServiceRepository());
	}
	
	/**
	 * 
	 */
	public DefaultMassyContext(
			Map<String, Object> initParams,
			ServiceRepository serviceRepository) {
		Asserts.argumentNotNull(serviceRepository, "serviceRepository");
		Asserts.argumentNotNull(initParams, "initParams");
		
		this.descriptor = new SimpleDescriptor(initParams);
		this.serviceRepository = serviceRepository;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.Descriptable#getDescriptor()
	 */
	@Override
	public Descriptor getDescriptor() {
		return this.descriptor;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.MassyContext#getAllServices(java.lang.Class)
	 */
	@Override
	public <S> S[] getAllServices(Class<S> serviceType) {
		Asserts.argumentNotNull(serviceType, "serviceType");		
		return this.serviceRepository.getAllServices(serviceType);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.MassyContext#getService(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <S> S getService(Class<S> serviceType)
			throws ServiceNotFoundException {
		Asserts.argumentNotNull(serviceType, "serviceType");
		
		//获取的是ServiceRepository服务
		if (serviceType == ServiceRepository.class){
			return (S) this.serviceRepository;
		}
		
		return this.assertServiceNotNull(
				this.serviceRepository.findService(serviceType),
				serviceType,
				null);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.MassyContext#getService(java.lang.Class, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <S> S getService(Class<S> serviceType, String alias)
			throws ServiceNotFoundException {
		Asserts.argumentNotNull(serviceType, "serviceType");
		
		//获取的是ServiceRepository服务
		if (serviceType == ServiceRepository.class){
			return (S) this.serviceRepository;
		}
		return this.assertServiceNotNull(
				this.serviceRepository.findService(serviceType, alias),
				serviceType,
				alias);
	}

	/**
	 * 断言服务是否为null.
	 * @param service 
	 * 		服务实例
	 * @param serviceType 
	 * 		服务类型,非空
	 * @param alias 
	 * 		服务别名，可以为空
	 * @return
	 * 		非空的服务实例
	 * @throws ServiceNotFound
	 * 		服务实例为null,则抛出异常
	 */
	protected <S> S assertServiceNotNull(S service, Class<S> serviceType, String alias)
		throws ServiceNotFoundException{
		if (service == null){
			throw new ServiceNotFoundException(serviceType, alias);
		}
		
		return service;
	}

		
}
