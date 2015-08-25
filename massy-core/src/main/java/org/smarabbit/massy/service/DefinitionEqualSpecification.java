/**
 * 
 */
package org.smarabbit.massy.service;

import org.smarabbit.massy.Constants;
import org.smarabbit.massy.Descriptor;
import org.smarabbit.massy.annotation.support.Definition;
import org.smarabbit.massy.lazyload.LazyBinder;
import org.smarabbit.massy.spec.Specification;
import org.smarabbit.massy.util.Asserts;

/**
 * {@link LazyBinder}服务查找规则检查器
 * @author huangkaihui
 *
 */
public class DefinitionEqualSpecification implements Specification<Descriptor> {

	private final Definition definition;
	
	/**
	 * 
	 */
	public DefinitionEqualSpecification(Definition definition) {
		Asserts.argumentNotNull(definition, "definition");
		this.definition = definition;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.spec.Specification#isStaisfyBy(java.lang.Object)
	 */
	@Override
	public boolean isStaisfyBy(Descriptor target) {
		return this.definition.equals(target.getProperty(
				Constants.SERVICE_DEFINITION, this.definition.getClass()));
	}
	/**
	 * @return the definition
	 */
	public Definition getDefinition() {
		return definition;
	}

	
}
