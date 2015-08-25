/**
 * 
 */
package org.smarabbit.massy.launch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.smarabbit.massy.MassyContext;
import org.smarabbit.massy.Registration;
import org.smarabbit.massy.adapt.AdaptFactoryRegistry;
import org.smarabbit.massy.service.ServiceRegistry;
import org.smarabbit.massy.util.Asserts;
import org.smarabbit.massy.util.CollectionUtils;

/**
 * @author huangkaihui
 *
 */
public abstract class AbstractPlugInActivator implements PlugInActivator {

	private List<Registration> registrationList;
	
	/**
	 * 
	 */
	public AbstractPlugInActivator() {

	}
	
	
	/* (non-Javadoc)
	 * @see org.smarabbit.massy.launch.PlugInActivator#stop(org.smarabbit.massy.MassyContext)
	 */
	@Override
	public void stop(MassyContext context) {
		this.release();
	}

	/**
	 * 获取{@link ServiceRegistry}服务
	 * @param context
	 * @return
	 */
	public ServiceRegistry getServiceRegistry(MassyContext context){
		return context.getService(ServiceRegistry.class);
	}
	
	/**
	 * 获取{@link AdaptFactoryRegistry}服务
	 * @param context
	 * @return
	 */
	public AdaptFactoryRegistry getAdaptFactoryRegistry(MassyContext context){
		return context.getService(AdaptFactoryRegistry.class);
	}

	/**
	 * 添加注册凭据
	 * @param registration {@link Registration},非空
	 */
	public synchronized void add(Registration registration){
		Asserts.argumentNotNull(registration, "registration");
		if (this.registrationList == null){
			this.registrationList = new ArrayList<Registration>();
		}
		
		this.registrationList.add(registration);
	}
	
	/**
	 * 批量添加注册凭据
	 * @param registrations
	 */
	public synchronized void addAll(Collection<Registration> registrations){
		Asserts.argumentNotNull(registrations, "registrations");
		if (this.registrationList == null){
			this.registrationList = new ArrayList<Registration>();
		}
		
		this.registrationList.addAll(registrations);
	}

	/**
	 * 释放所有注册凭据
	 */
	protected synchronized void release(){
		if (!CollectionUtils.isEmpty(this.registrationList)){
			for (Registration registration: this.registrationList){
				registration.unregister();
			}
			
			this.registrationList.clear();
		}
	}
}
