/**
 * 
 */
package org.smarabbit.massy.spec;

import org.smarabbit.massy.util.Asserts;

/**
 * 类型相同规则检查器
 * 
 * * <br>
 * 判断目标类型是否相同。
 * @author huangkaihui
 *
 */
public final class TypeEqualSpecification<T> implements Specification<T> {

	private final Class<?> type;
	
	/**
	 * 
	 */
	public TypeEqualSpecification(Class<?> type) {
		Asserts.argumentNotNull(type, "type");
		this.type = type;
	}
	
	/* (non-Javadoc)
	 * @see org.smarabbit.massy.spec.Specification#isStaisfyBy(java.lang.Object)
	 */
	@Override
	public boolean isStaisfyBy(T target) {
		return this.type == target.getClass();
	}

	/**
	 * @return the type
	 */
	public Class<?> getType() {
		return type;
	}
	
}
