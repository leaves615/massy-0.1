/**
 * 
 */
package org.smarabbit.massy.annotation.support;

import java.lang.annotation.ElementType;

import org.smarabbit.massy.util.Asserts;

/**
 * 延迟加载字段定义
 * @author huangkaihui
 *
 */
public final class LazyBindDefinition implements Definition {

	private final String declaringTypeName;
	private final String fieldName;
	
	/**
	 * 
	 */
	public LazyBindDefinition(String declaringTypeName, String fieldName) {
		Asserts.argumentNotNull(declaringTypeName, "declaringTypeName");
		Asserts.argumentNotNull(fieldName, "fieldName");
		this.declaringTypeName = declaringTypeName;
		this.fieldName = fieldName;
	}
	
	/**
	 * @return the declaringTypeName
	 */
	public String getDeclaringTypeName() {
		return declaringTypeName;
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.annotation.support.Definition#getElementType()
	 */
	@Override
	public ElementType getElementType() {
		return ElementType.FIELD;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.annotation.support.Definition#isInherited()
	 */
	@Override
	public boolean isInherited() {
		return false;
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
		result = prime * result
				+ ((fieldName == null) ? 0 : fieldName.hashCode());
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
		LazyBindDefinition other = (LazyBindDefinition) obj;
		if (declaringTypeName == null) {
			if (other.declaringTypeName != null)
				return false;
		} else if (!declaringTypeName.equals(other.declaringTypeName))
			return false;
		if (fieldName == null) {
			if (other.fieldName != null)
				return false;
		} else if (!fieldName.equals(other.fieldName))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LazyBindDefinition [declaringTypeName=" + declaringTypeName
				+ ", fieldName=" + fieldName + "]";
	}

}
