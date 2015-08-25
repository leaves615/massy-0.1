/**
 * 
 */
package org.smarabbit.massy.spring.config;

import org.smarabbit.massy.MassyUtils;
import org.springframework.beans.factory.config.AbstractFactoryBean;

/**
 * @author huangkaihui
 *
 */
public class ImportServiceFactoryBean<T> extends AbstractFactoryBean<T> {

	private Class<T> serviceType;
	private String alias;
	
	public ImportServiceFactoryBean() {
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.AbstractFactoryBean#getObjectType()
	 */
	@Override
	public Class<?> getObjectType() {
		return this.serviceType;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.AbstractFactoryBean#createInstance()
	 */
	@Override
	protected T createInstance() throws Exception {
		return (T)MassyUtils.getService(this.serviceType, this.alias);
	}

	/**
	 * @return the serviceType
	 */
	public Class<T> getServiceType() {
		return serviceType;
	}

	/**
	 * @param serviceType the serviceType to set
	 */
	public void setServiceType(Class<T> serviceType) {
		this.serviceType = serviceType;
	}

	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

}
