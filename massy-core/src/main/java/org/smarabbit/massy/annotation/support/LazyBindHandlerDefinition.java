/**
 * 
 */
package org.smarabbit.massy.annotation.support;

import java.lang.annotation.ElementType;

import org.smarabbit.massy.util.Asserts;

/**
 * 延迟绑定处理定义
 * @author huangkaihui
 *
 */
public class LazyBindHandlerDefinition implements Definition {

	private final String declaringTypeName;
	private final String methodName;
	
	private final String paramTypeName;
	private final String fieldName;
	
	/**
	 * 
	 */
	public LazyBindHandlerDefinition(String declaringTypeName, String methodName, String paramTypeName, String fieldName) {
		Asserts.argumentNotNull(declaringTypeName, "declaringTypeName");
		Asserts.argumentNotNull(methodName, "methodName");
		Asserts.argumentNotNull(paramTypeName, "paramTypeName");
		Asserts.argumentNotNull(fieldName, "fieldName");
		
		this.declaringTypeName = declaringTypeName;
		this.methodName = methodName;
		this.paramTypeName = paramTypeName;
		this.fieldName = fieldName;
	}
	
	/**
	 * @return the declaringTypeName
	 */
	public String getDeclaringTypeName() {
		return declaringTypeName;
	}

	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @return the paramTypeName
	 */
	public String getParamTypeName() {
		return paramTypeName;
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
		return ElementType.METHOD;
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
				+ ((methodName == null) ? 0 : methodName.hashCode());
		result = prime * result
				+ ((paramTypeName == null) ? 0 : paramTypeName.hashCode());
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
		LazyBindHandlerDefinition other = (LazyBindHandlerDefinition) obj;
		if (declaringTypeName == null) {
			if (other.declaringTypeName != null)
				return false;
		} else if (!declaringTypeName.equals(other.declaringTypeName))
			return false;
		if (methodName == null) {
			if (other.methodName != null)
				return false;
		} else if (!methodName.equals(other.methodName))
			return false;
		if (paramTypeName == null) {
			if (other.paramTypeName != null)
				return false;
		} else if (!paramTypeName.equals(other.paramTypeName))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LazyBindHandlerDefinition [declaringTypeName="
				+ declaringTypeName + ", methodName=" + methodName
				+ ", paramTypeName=" + paramTypeName + ", fieldName="
				+ fieldName + "]";
	}

}
