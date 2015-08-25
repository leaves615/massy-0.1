/**
 * 
 */
package org.smarabbit.massy.spring.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.smarabbit.massy.Registration;
import org.smarabbit.massy.util.Asserts;

/**
 * @author huangkaihui
 *
 */
public class DefaultBeanRegistrationManager implements BeanRegistrationManager {

	private Map<String, List<Registration>> registationMap =
			new HashMap<String, List<Registration>>();
	
	/**
	 * 
	 */
	public DefaultBeanRegistrationManager() {

	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.spring.config.BeanRegistrationManager#addRegistration(java.lang.String, javax.servlet.Registration)
	 */
	@Override
	public synchronized <R extends Registration> void addRegistration(
			String beanName, R registration) {
		Asserts.argumentNotNull(beanName, "beanName");
		Asserts.argumentNotNull(registration, "registration");
		
		List<Registration> list = this.registationMap.get(beanName);
		if (list == null){
			list = new ArrayList<Registration>();
			this.registationMap.put(beanName, list);
		}
		list.add(registration);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.spring.config.BeanRegistrationManager#unregister(java.lang.String)
	 */
	@Override
	public synchronized void unregister(String beanName) {
		List<Registration> list = this.registationMap.remove(beanName);
		if (list != null){
			for (Registration registration: list){
				registration.unregister();
			}
		}
	}

}
