/**
 * 
 */
package org.smarabbit.massy.axon.spring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.CommandHandlerInterceptor;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.unitofwork.TransactionManager;
import org.smarabbit.massy.service.OrderComparator;
import org.smarabbit.massy.util.CollectionUtils;
import org.springframework.context.ApplicationContextAware;

/**
 * @author huangkaihui
 *
 */
public class SimpleCommandBusFactoryBean extends AutoRegistryFactoryBean<SimpleCommandBus> 
	implements ApplicationContextAware{
	
	private TransactionManager<?> transactionManager;
	
	/**
	 * 
	 */
	public SimpleCommandBusFactoryBean() {
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.AbstractFactoryBean#getObjectType()
	 */
	@Override
	public Class<?> getObjectType() {
		return SimpleCommandBus.class;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.AbstractFactoryBean#createInstance()
	 */
	@Override
	protected SimpleCommandBus createInstance() throws Exception {
		SimpleCommandBus result =  new SimpleCommandBus();
		if (this.transactionManager != null){
			this.setTransactionManager(this.transactionManager);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.AbstractFactoryBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		this.bindInterceptors();
	}
	
	/* (non-Javadoc)
	 * @see org.smarabbit.massy.axon.spring.AutoRegistryFactoryBean#getServiceTypes()
	 */
	@Override
	protected Class<?>[] getServiceTypes() {
		return new Class<?>[]{CommandBus.class, SimpleCommandBus.class};
	}

	protected void bindInterceptors() throws Exception{
		SimpleCommandBus commandBus = this.getObject();
		Map<String, CommandHandlerInterceptor> beanMaps =
				this.getApplicationContext().getBeansOfType(CommandHandlerInterceptor.class);
		if (!CollectionUtils.isEmpty(beanMaps)){
			List<CommandHandlerInterceptor> interceptors =
				new ArrayList<CommandHandlerInterceptor>(beanMaps.values());
			Collections.sort(interceptors, new OrderComparator());
			commandBus.setHandlerInterceptors(interceptors);
		}
	}

	/**
	 * @return the transactionManager
	 */
	public TransactionManager<?> getTransactionManager() {
		return transactionManager;
	}

	/**
	 * @param transactionManager the transactionManager to set
	 */
	@Resource
	public void setTransactionManager(TransactionManager<?> transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	
}
