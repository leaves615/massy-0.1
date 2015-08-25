/**
 * 
 */
package org.smarabbit.massy.spring.config;

import org.smarabbit.massy.service.PrototypeServiceFactory;
import org.springframework.beans.factory.BeanFactory;

/**
 * 多态的BeanServiceFactory
 * @author huangkaihui
 *
 */
public class PrototypeBeanServiceFactory extends BeanServiceFactory implements
		PrototypeServiceFactory {

	/**
	 * @param beanName
	 * @param factory
	 */
	public PrototypeBeanServiceFactory(String beanName, BeanFactory factory) {
		super(beanName, factory);
	}

}
