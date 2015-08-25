/**
 * 
 */
package org.smarabbit.massy.annotation.support;

import java.lang.annotation.ElementType;

import org.smarabbit.massy.annotation.Adapt;
import org.smarabbit.massy.util.Asserts;

/**
 * 适配定义
 * @author huangkaihui
 *
 */
public class AdaptDefinition implements Definition {

	//声明类型名称
	private String declaringTypeName;
	//适配类型，当为Void时，等同于声明类型
	private Class<?> adaptType;
	//适配宿主类型指定
	private Class<?>[] specificStrategy;
	//适配宿主类型扩展
	private Class<?>[] extendsStrategy;
	
	public AdaptDefinition(String declaringTypeName, Adapt anno){
		this(declaringTypeName, anno.adaptType(), anno.specificStrategy(), anno.extendStrategy());
	}

	/**
	 * 
	 */
	public AdaptDefinition(String declaringTypeName, Class<?> adaptType, 
			Class<?>[] specificStrategy, Class<?>[] extendsStrategy) {
		Asserts.argumentNotNull(declaringTypeName, "declaringTypeName");
		Asserts.argumentNotNull(adaptType, "adaptType");
		Asserts.argumentNotNull(specificStrategy, "specificStrategy");
		Asserts.argumentNotNull(extendsStrategy, "extendsStrategy");
		
		this.declaringTypeName = declaringTypeName;
		this.adaptType = adaptType;
		this.specificStrategy = specificStrategy;
		this.extendsStrategy = extendsStrategy;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.annotation.support.Definition#getElementType()
	 */
	@Override
	public ElementType getElementType() {
		return ElementType.TYPE;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.annotation.support.Definition#isInherited()
	 */
	@Override
	public boolean isInherited() {
		return true;
	}

	/**
	 * @return the declaringTypeName
	 */
	public String getDeclaringTypeName() {
		return declaringTypeName;
	}

	/**
	 * @return the adaptType
	 */
	public Class<?> getAdaptType() {
		return adaptType;
	}

	/**
	 * @return the specificStrategy
	 */
	public Class<?>[] getSpecificStrategy() {
		return specificStrategy;
	}

	/**
	 * @return the extendsStrategy
	 */
	public Class<?>[] getExtendsStrategy() {
		return extendsStrategy;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((declaringTypeName == null) ? 0 : declaringTypeName
						.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdaptDefinition other = (AdaptDefinition) obj;
		if (declaringTypeName == null) {
			if (other.declaringTypeName != null)
				return false;
		} else if (!declaringTypeName.equals(other.declaringTypeName))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AdaptDefinition [declaringTypeName=" + declaringTypeName
				+ ", adaptType=" + adaptType + "]";
	}
	
}
