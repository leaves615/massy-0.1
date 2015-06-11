/**
 * 
 */
package org.smarabbit.massy.service;

import org.smarabbit.massy.Constants;
import org.smarabbit.massy.Descriptor;
import org.smarabbit.massy.spec.Specification;

/**
 * 服务别名相同规则检查器。
 * 
 * @author huangkh
 *
 */
public class ServiceAliasMatchSpecification implements Specification<Descriptor> {

	private final String alias;
	
	/**
	 * 
	 */
	public ServiceAliasMatchSpecification(String alias) {
		this.alias = alias;
	}

	/**
	 * 判断target的属性“service.alias”是否等于别名
	 */
	@Override
	public boolean isStaisfyBy(Descriptor target) {
		if (this.alias == null){
			return target.getProperty(Constants.SERVICE_ALIAS, String.class) == null;
		}else{
			return this.alias.equals(
					target.getProperty(Constants.SERVICE_ALIAS, String.class));
		}
	}

}
