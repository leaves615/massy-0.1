/**
 * 
 */
package org.smarabbit.massy.service.support;

import java.util.HashMap;
import java.util.Map;

import org.smarabbit.massy.Constants;
import org.smarabbit.massy.service.DelegateServiceFactory;
import org.smarabbit.massy.service.ServiceFactory;
import org.smarabbit.massy.service.ServiceRegistration;
import org.smarabbit.massy.support.AbstractRegistration;
import org.smarabbit.massy.support.DescriptionUtils;
import org.smarabbit.massy.support.SimpleDescriptor;
import org.smarabbit.massy.util.Asserts;

/**
 * 实现{@link ServiceRegistration}的抽象类
 * @author huangkaihui
 *
 */
public abstract class AbstractServiceRegistration extends AbstractRegistration
		implements ServiceRegistration {

	private final Object registedObject;
	
	public AbstractServiceRegistration(long id, Class<?>[] serviceTypes, Object registedObject, Map<String, Object> props){
		super(id);
		Asserts.argumentNotEmpty(serviceTypes, "serviceTypes");
		Asserts.argumentNotNull(registedObject, "registedObject");
		
		// 创建新的Map,并复制附加属性
		Map<String, Object> map = props == null ? new HashMap<String, Object>()
				: new HashMap<String, Object>(props);

		//编号
		map.put(Constants.SERVICE_ID, id);
		// 适配类型
		map.put(Constants.SERVICE_TYPES, serviceTypes);
		
		// 服务类型
		Class<?> clazz =(Class<?>) map.get(Constants.OBJECT_CLASS);
		if (clazz == null){
			clazz = registedObject.getClass();
			
			if (DelegateServiceFactory.class.isAssignableFrom(clazz)){
				map.put(Constants.PROXYFACTORY_CLASS, clazz);
				
				clazz = ((DelegateServiceFactory)registedObject).getObjectClass();
			}
			map.put(Constants.OBJECT_CLASS, clazz);
		}

		// 说明
		String description = DescriptionUtils.getDescription(clazz);
		if (description != null) {
			map.put(Constants.DESCRIPTION, description);
		}

		this.registedObject = registedObject;
		this.setDescriptor(new SimpleDescriptor(map));
	}
	
	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistration#getServiceTypes()
	 */
	@Override
	public Class<?>[] getServiceTypes() {
		return (Class<?>[])this.getDescriptor().getProperty(Constants.SERVICE_TYPES);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistration#getRegistedObject()
	 */
	@Override
	public Object getRegistedObject() {
		return this.registedObject;
	}
	

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistration#getRegistedObjectType()
	 */
	@Override
	public Class<?> getRegistedObjectType() {
		return this.getDescriptor().getProperty(Constants.OBJECT_CLASS, Class.class);
	}
	
	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceRegistration#getService()
	 */
	@Override
	public Object getService() {
		return this.registedObject instanceof ServiceFactory ?
				((ServiceFactory)this.registedObject).getService():
					this.registedObject;
	}

}
