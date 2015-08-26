/**
 * 
 */
package org.smarabbit.massy.service.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.smarabbit.massy.Constants;
import org.smarabbit.massy.Descriptor;
import org.smarabbit.massy.service.ServiceAliasMatchSpecification;
import org.smarabbit.massy.service.ServiceRegistration;
import org.smarabbit.massy.service.ServiceRegistrationManager;
import org.smarabbit.massy.spec.Specification;
import org.smarabbit.massy.support.AbstractRegistrationManager;
import org.smarabbit.massy.util.ArrayUtils;
import org.smarabbit.massy.util.Asserts;

/**
 * @author huangkaihui
 *
 */
public abstract class AbstractServiceRegistrationManager<S> extends
		AbstractRegistrationManager<ServiceRegistration> implements ServiceRegistrationManager<S> {

	private Class<S> serviceType;

	public AbstractServiceRegistrationManager() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistrationManager#bind(java.lang.Class)
	 */
	@Override
	public void bind(Class<S> serviceType) {
		Asserts.argumentNotNull(serviceType, "serviceTpe");
		this.serviceType = serviceType;
	}
	
	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistrationManager#getDefaultService()
	 */
	@Override
	public S getDefaultService() {
		ServiceRegistration defaultRegistration =
				this.getDefaultServiceRegistration();
		
		return defaultRegistration == null ?
				null :
					this.getServiceFromRegistration(defaultRegistration);
	}
	
	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistrationManager#getService(org.smarabbit.massy.Descriptor)
	 */
	@Override
	public S findService(Descriptor descriptor) {
		Asserts.argumentNotNull(descriptor, "descriptor");
		
		ServiceRegistration find = null;
		Collection<ServiceRegistration> c = this.getRegistrations();
		Iterator<ServiceRegistration> it = c.iterator();
		while (it.hasNext()){
			ServiceRegistration registration = it.next();
			if (registration.getDescriptor() == descriptor){
				find = registration;
				break;
			}
		}
		return find != null ?
				this.getServiceFromRegistration(find) :
					null;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistrationManager#getServices()
	 */
	@Override
	public S[] getServices() {
		Collection<ServiceRegistration> c = this.getRegistrations();
		Iterator<ServiceRegistration> it = c.iterator();
		
		int size = c.size();
		S[] result = (S[])ArrayUtils.newArrayByClass(this.serviceType, size);
		for (int i=0; i<size; i++){
			ServiceRegistration registration = it.next();
			result[i] = (S)this.getServiceFromRegistration(registration);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistrationManager#findService(java.lang.String)
	 */
	@Override
	public S findService(String alias) {
		Asserts.argumentNotNull(alias, "alias");
		Collection<ServiceRegistration> c = this.getRegistrations();
		
		for (ServiceRegistration registration : c){
			if (alias.equals(
					registration.getDescriptor().getProperty(Constants.SERVICE_ALIAS, String.class))){
				return this.getServiceFromRegistration(registration);
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistrationManager#findService(org.smarabbit.massy.spec.Specification)
	 */
	@Override
	public S findService(Specification<Descriptor> spec) {
		Asserts.argumentNotNull(spec, "spec");
		Collection<ServiceRegistration> c = this.getRegistrations();
		ServiceRegistration registration =this.doFind(c, spec);
		return registration == null ? null :
			this.getServiceFromRegistration(registration);
	}
	
	
	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistrationManager#findServices(org.smarabbit.massy.spec.Specification)
	 */
	@Override
	public S[] findServices(Specification<Descriptor> spec) {
		Asserts.argumentNotNull(spec, "spec");
		Collection<ServiceRegistration> c = this.getRegistrations();
		List<ServiceRegistration> registrations =this.doFindAll(c, spec);
		
		int size = registrations.size();
		S[] result = (S[])ArrayUtils.newArrayByClass(this.serviceType, size);
		for (int i=0; i<size; i++){
			result[i] = this.getServiceFromRegistration(registrations.get(i));
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistrationManager#getDefaultServiceDescriptor()
	 */
	@Override
	public Descriptor getDefaultServiceDescriptor() {
		ServiceRegistration defaultRegistration =
				this.getDefaultServiceRegistration();
		
		return defaultRegistration == null ?
				null :
					defaultRegistration.getDescriptor();
	}
	
	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistrationManager#findServiceDescriptor(java.lang.String)
	 */
	@Override
	public Descriptor findServiceDescriptor(String alias) {
		Asserts.argumentNotNull(alias, "alias");
		Collection<ServiceRegistration> c = this.getRegistrations();
		
		for (ServiceRegistration registration : c){
			if (alias.equals(
					registration.getDescriptor().getProperty(Constants.SERVICE_ALIAS, String.class))){
				return registration.getDescriptor();
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistrationManager#findServiceDescriptor(org.smarabbit.massy.spec.Specification)
	 */
	@Override
	public Descriptor findServiceDescriptor(Specification<Descriptor> spec) {
		Asserts.argumentNotNull(spec, "spec");
		Collection<ServiceRegistration> c = this.getRegistrations();
		ServiceRegistration registration =this.doFind(c, spec);
		return registration == null ? null : registration.getDescriptor();
	}

	/**
	 * @return the serviceType
	 */
	public Class<S> getServiceType() {
		return serviceType;
	}
	
	/**
	 * 获取缺省服务的ServiceRegistration
	 * @return
	 * 			{@link ServiceRegistration},可能返回null.
	 */
	protected ServiceRegistration getDefaultServiceRegistration(){
		Collection<ServiceRegistration> c = this.getRegistrations();
		if (c.isEmpty()) return null;
		
		//先获取别名为null的首个服务
		ServiceRegistration registration = this.doFind(
				c, new ServiceAliasMatchSpecification(null));
		if (registration != null){
			return registration;
		}
		
		//否则返回第一个服务
		Iterator<ServiceRegistration> it = c.iterator();
		if (it.hasNext()){
			return it.next();
		}
		
		return null;
	}
	
	/**
	 * 查找服务
	 * @param list {@link List}集合
	 * @param spec 规则检查器
	 * @return
	 * 		服务实例，可以返回null.
	 */
	protected ServiceRegistration doFind(Collection<ServiceRegistration> list, Specification<Descriptor> spec){
		Iterator<ServiceRegistration> it = list.iterator();
		while (it.hasNext()){
			ServiceRegistration registration = it.next();
			if (spec.isStaisfyBy(registration.getDescriptor())){
				return registration;
			}
		}
		
		return null;
	}
	
	protected List<ServiceRegistration> doFindAll(Collection<ServiceRegistration> list, Specification<Descriptor> spec){
		List<ServiceRegistration> result = new ArrayList<ServiceRegistration>();
		Iterator<ServiceRegistration> it = list.iterator();
		while (it.hasNext()){
			ServiceRegistration registration = it.next();
			if (spec.isStaisfyBy(registration.getDescriptor())){
				result.add(registration);
			}
		}
		
		return result;
	}
	
	/**
	 * 从ServiceRegistration中获取服务
	 * @param registration
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected S getServiceFromRegistration(ServiceRegistration registration){
		Asserts.argumentNotNull(registration, "registration");
		return (S)registration.getService();
	}
}
