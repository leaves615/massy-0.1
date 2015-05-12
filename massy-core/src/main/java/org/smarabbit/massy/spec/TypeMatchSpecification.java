/**
 * 
 */
package org.smarabbit.massy.spec;

import org.smarabbit.massy.util.Asserts;

/**
 * 类型匹配规则检查
 * 
 * <br>
 * 符合Class.isInstance(target)语义进行类型匹配检查。
 * 
 * @author huangkaihui
 *
 */
public class TypeMatchSpecification<T> implements Specification<T> {

	private final Class<?> type;
	
	/**
	 * 
	 */
	public TypeMatchSpecification(Class<?> type) {
		Asserts.argumentNotNull(type, "type");
		this.type = type;
	}
	
	/* (non-Javadoc)
	 * @see org.smarabbit.massy.spec.Specification#isStaisfyBy(java.lang.Object)
	 */
	@Override
	public boolean isStaisfyBy(T target) {
		return this.type.isInstance(target);
	}

	/**
	 * @return the type
	 */
	public Class<?> getType() {
		return type;
	}

}
