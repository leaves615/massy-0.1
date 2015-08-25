/**
 * 
 */
package org.smarabbit.massy.bytecode.proxy;

import java.lang.reflect.Constructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author huangkaihui
 *
 */
public abstract class ServiceProxyFactory {
	
	private static final Logger logger = 
			LoggerFactory.getLogger(ServiceProxyFactory.class);
	private static final TypeGenerator<Class<?>> generator =
			new DynamicProxyTypeGenerator();

	public static <S> S createProxy(Class<S> serviceType, S instance) throws Exception{
		Class<?> clazz = generator.generate(serviceType);
		return newInstance(clazz, serviceType, instance);
	}
	
	@SuppressWarnings("unchecked")
	protected static <S> S newInstance(Class<?> clazz, Class<?> serviceType, S instance ) 
			throws  Exception{
		try {
			Constructor<?> ctor =clazz.getConstructor(new Class<?>[]{serviceType});
			return (S) ctor.newInstance(instance);
		} catch (Exception e) {
			if (logger.isErrorEnabled()){
				logger.error(e.getMessage(), e);
			}
			throw e;
		}
	}
}
