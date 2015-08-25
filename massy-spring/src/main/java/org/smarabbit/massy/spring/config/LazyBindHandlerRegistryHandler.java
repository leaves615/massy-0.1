/**
 * 
 */
package org.smarabbit.massy.spring.config;

import org.smarabbit.massy.MassyUtils;
import org.smarabbit.massy.Registration;
import org.smarabbit.massy.annotation.LazyBindHandler;
import org.smarabbit.massy.annotation.support.Definition;
import org.smarabbit.massy.annotation.support.LazyBindHandlerDefinition;
import org.smarabbit.massy.bytecode.proxy.LazyBinderFactory;
import org.smarabbit.massy.lazyload.LazyBinder;
import org.smarabbit.massy.service.ServiceRegistry;
import org.smarabbit.massy.spring.MassyResource;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * {@link LazyBindHandler}注册处理器
 * @author huangkaihui
 *
 */
public class LazyBindHandlerRegistryHandler implements BeanRegistryHandler {

	private static final ServiceRegistry SERVICEREGISTRY =
			MassyUtils.getDefaultContext().getService(ServiceRegistry.class);
	
	/**
	 * 
	 */
	public LazyBindHandlerRegistryHandler() {

	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.spring.config.BeanRegistryHandler#support(java.lang.Class)
	 */
	@Override
	public boolean support(Class<? extends Definition> definitionType) {
		return LazyBindHandlerDefinition.class == definitionType;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.spring.config.BeanRegistryHandler#register(java.lang.String, org.springframework.beans.factory.config.ConfigurableListableBeanFactory, org.smarabbit.massy.annotation.support.Definition)
	 */
	@Override
	public Registration register(String beanName,
			ConfigurableListableBeanFactory factory, Definition definition, MassyResource resource) {
		LazyBindHandlerDefinition lbhd = (LazyBindHandlerDefinition)definition;
		Object handler = factory.getBean(beanName);
		LazyBinder<Object> binder;
		try {
			binder = LazyBinderFactory.create(handler, lbhd);
			return SERVICEREGISTRY.register(LazyBinder.class, binder, null);
		} catch (Exception e) {
			throw new FatalBeanException(e.getMessage(),e);
		}
	}

}
