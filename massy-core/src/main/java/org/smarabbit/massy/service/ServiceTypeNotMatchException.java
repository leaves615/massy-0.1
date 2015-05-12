/**
 * 
 */
package org.smarabbit.massy.service;

import org.smarabbit.massy.MassyException;
import org.smarabbit.massy.util.Asserts;

/**
 * 服务类型不匹配异常
 * 
 * @author huangkaihui
 *
 */
public class ServiceTypeNotMatchException extends MassyException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8181446213608649160L;
	
	private final Class<?> serviceType;
	private final Object service;
	
	/**
	 * 
	 */
	public ServiceTypeNotMatchException(Class<?> serviceType, Object service) {
		Asserts.argumentNotNull(serviceType, "serviceType");
		Asserts.argumentNotNull(service, "service");
		this.service = service;
		this.serviceType = serviceType;
	}

	/**
	 * @return the serviceType
	 */
	public Class<?> getServiceType() {
		return serviceType;
	}

	/**
	 * @return the service
	 */
	public Object getService() {
		return service;
	}

	@Override
	public String getMessage() {
		return "service[" + this.service.getClass().getName() + "] is not assigned serviceType: " + this.serviceType.getName();
	}

	
}
