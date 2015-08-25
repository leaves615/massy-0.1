/**
 * 
 */
package org.smarabbit.massy.service.support;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smarabbit.massy.service.ServiceEvent;
import org.smarabbit.massy.service.ServiceListener;
import org.smarabbit.massy.service.ServiceRegistration;

/**
 * {@link ServiceListener}服务注册管理器
 * @author huangkaihui
 *
 */
public class ServiceListenerRegistrationManager extends
		DefaultServiceRegistrationManager<ServiceListener> {

	private static final Logger logger =
			LoggerFactory.getLogger(ServiceListenerRegistrationManager.class);
	
	public ServiceListenerRegistrationManager() {
		super();
	}

	/**
	 * 发布服务事件
	 * @param event {@link ServiceEvent}事件
	 */
	public void notifyServiceEvent(ServiceEvent event){
		Iterator<ServiceRegistration> it = this.getRegistrations().iterator();
		while (it.hasNext()){
			ServiceRegistration registration = it.next();
			ServiceListener listener = (ServiceListener)registration.getService();
			try{
				listener.onEvent(event);
			}catch(Exception e){
				if (logger.isErrorEnabled()){
					logger.error(e.getMessage(),e);
				}
			}
		}
	}
}
