/**
 * 
 */
package org.smarabbit.massy.spring.config;

import org.smarabbit.massy.service.DelegateServiceFactory;
import org.smarabbit.massy.service.ServiceFactory;
import org.smarabbit.massy.util.Asserts;
import org.springframework.beans.factory.BeanFactory;

/**
 * 单一Bean服务工厂，提供将Bean转为发布的服务
 * @author huangkaihui
 *
 */
public class BeanServiceFactory implements ServiceFactory, DelegateServiceFactory {

	private final String beanName;
	private final BeanFactory factory;
	/**
	 * 
	 */
	public BeanServiceFactory(String beanName, BeanFactory factory) {
		Asserts.argumentNotNull(beanName, "beanName");
		Asserts.argumentNotNull(factory, "factory");
		
		this.beanName = beanName;
		this.factory = factory;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceFactory#getService()
	 */
	@Override
	public Object getService() {
		return this.factory.getBean(this.beanName);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.DelegateServiceFactory#getObjectClass()
	 */
	@Override
	public Class<?> getObjectClass() {
		return this.factory.getType(this.beanName);
	}

	
}
