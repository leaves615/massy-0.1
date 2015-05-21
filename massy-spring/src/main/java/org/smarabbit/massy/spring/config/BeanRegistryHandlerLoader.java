/**
 * 
 */
package org.smarabbit.massy.spring.config;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.smarabbit.massy.support.ObjectOrderUtils;
import org.smarabbit.massy.util.ServiceLoaderUtils;

/**
 * {@link BeanRegistrationHandler}加载器，以ServiceLoader方式加载{@link BeanRegistrationHandler}
 * @author huangkaihui
 *
 */
public class BeanRegistryHandlerLoader {

	private static List<BeanRegistryHandler> HANDLERS;
	static{
		HANDLERS = ServiceLoaderUtils.loadServices(BeanRegistryHandler.class);
		ObjectOrderUtils.sort(HANDLERS);
	}
		
	/**
	 * 
	 */
	public BeanRegistryHandlerLoader() {

	}
			
	@SuppressWarnings("unchecked")
	protected Iterator<BeanRegistryHandler> iterator(){
		if (HANDLERS == null){
			return Collections.EMPTY_LIST.iterator();
		}
		return HANDLERS.iterator();
	}
}
