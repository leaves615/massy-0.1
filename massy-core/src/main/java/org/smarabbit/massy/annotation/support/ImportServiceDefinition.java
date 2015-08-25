/**
 * 
 */
package org.smarabbit.massy.annotation.support;

import java.lang.annotation.ElementType;

import org.smarabbit.massy.util.Asserts;

/**
 * 引入服务定义
 * @author huangkaihui
 *
 */
public final class ImportServiceDefinition implements Definition {

	private String declaringTypeName;
	private String fieldName;
	
	private Class<?> serviceType;
	private String alias;
	private boolean allowNull;
	/**
	 * 
	 */
	public ImportServiceDefinition(String declaringTypeName, String fieldName, 
			Class<?> serviceType, String alias, boolean allowNull) {
		Asserts.argumentNotNull(declaringTypeName, "declaringTypeName");
		Asserts.argumentNotNull(serviceType, "serviceType");
		
		this.declaringTypeName = declaringTypeName;
		this.fieldName = fieldName;
		if ((alias!= null) && (!alias.equals(""))){
			this.alias = alias;
		}
		this.allowNull = allowNull;
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



	/**
	 * @return the serviceType
	 */
	public Class<?> getServiceType() {
		return serviceType;
	}

	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @return the allowNull
	 */
	public boolean isAllowNull() {
		return allowNull;
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
		ImportServiceDefinition other = (ImportServiceDefinition) obj;
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

	
}
