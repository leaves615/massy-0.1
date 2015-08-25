/**
 * 
 */
package org.smarabbit.massy;



/**
 * 服务未找到异常
 * 
 * @author huangkh
 *
 */
public class ServiceNotFoundException extends MassyException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2973686500900506793L;

	private final Class<?> serviceType;
	private final String alias;
	
	public ServiceNotFoundException(Class<?> serviceType){
		this(serviceType, null);
	}
	
	/**
	 * 
	 */
	public ServiceNotFoundException(Class<?> serviceType, String alias) {
		this.serviceType = serviceType;
		this.alias = alias;
	}
	
	/**
	 * @return 服务类型
	 */
	public Class<?> getServiceType() {
		return serviceType;
	}
	
	/**
	 * @return 服务别名
	 */
	public String getAlias() {
		return alias;
	}

	@Override
	public String getMessage() {
		return this.alias == null ? 
				"service not found: serviceType=" + this.serviceType.getName() + ".":
					"service not found: serviceType=" + this.serviceType.getName() + "; alias=" + this.alias + ".";
	}

}
