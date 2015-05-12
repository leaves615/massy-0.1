/**
 * 
 */
package org.smarabbit.massy.service;

import java.util.EventObject;

import org.smarabbit.massy.Constants;
import org.smarabbit.massy.Descriptor;
import org.smarabbit.massy.util.Asserts;

/**
 * @author huangkh
 *
 */
public class ServiceEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3989874353715225590L;
		
	private final RegistState state;
	private final Descriptor descriptor;

	/**
	 * @param source
	 */
	public ServiceEvent(Object service, Descriptor descriptor, RegistState state) {
		super(service);
		Asserts.argumentNotNull(descriptor, "descriptor");
		this.descriptor = descriptor;
		this.state = state;
	}
	
	/**
	 * 所有注册服务类型
	 * @return
	 */
	public Class<?>[] getServiceTypes(){
		return (Class<?>[])this.descriptor.getProperty(Constants.SERVICE_TYPES);
	}
	
	/**
	 * 注册对象类型
	 * @return
	 */
	public Class<?> getRegistedObjectType(){
		return this.descriptor.getProperty(Constants.OBJECT_CLASS, Class.class);
	}
	
	/**
	 * 获取服务
	 * @return
	 * 		服务实例，不能为null.
	 */
	public Object getService(){
		return this.source instanceof ServiceFactory ?
				((ServiceFactory)this.source).getService() :
					this.source;
	}
	
	public Descriptor getDescriptor(){
		return this.descriptor;
	}
	
	/**
	 * 获取注册状态
	 * @return 
	 * 		状态
	 */
	public RegistState getState(){
		return this.state;
	}

}
