/**
 * 
 */
package org.smarabbit.massy.annotation.support;

import java.lang.annotation.ElementType;
import java.util.HashMap;
import java.util.Map;

import org.smarabbit.massy.Constants;
import org.smarabbit.massy.util.Asserts;

/**
 * 输出服务定义
 * @author huangkaihui
 *
 */
public final class ExportServiceDefinition implements Definition {

	//声明类型
	private String declaringTypeName;	
	//服务类型
	private Class<?>[] serviceTypes;
	
	//属性
	private Map<String, Object> props;
		
	public ExportServiceDefinition(String declaringTypeName, Class<?>[] serviceTypes){
		this(declaringTypeName, serviceTypes, null);
	}
	
	public ExportServiceDefinition(String declaringTypeName, Class<?>[] serviceTypes, Map<String, Object> props){
		Asserts.argumentNotNull(declaringTypeName, "declaringTypeName");
		this.declaringTypeName = declaringTypeName;
		this.serviceTypes = serviceTypes;
		this.props = props;
	}
	
	public ExportServiceDefinition(Class<?>[] serviceTypes, Map<String, Object> props){
		Asserts.argumentNotEmpty(serviceTypes, "serviceTypes");
		this.serviceTypes = serviceTypes;
		this.props = props;
	}
		
	public Class<?>[] getServiceTypes(){
		if (this.serviceTypes.length != 0){
			return this.serviceTypes;
		}
		
		try{
			Class<?> declaringType = Thread.currentThread().getContextClassLoader().loadClass(this.declaringTypeName);
			this.serviceTypes = new Class<?>[]{declaringType};
		}catch(Exception e){
			
		}

		return this.serviceTypes;
	}
	
	/**
	 * 设置服务别名
	 * @param alias
	 */
	public void setAlias(String alias){
		if  ((alias != null) && (alias.length() >0)){
			this.getOrCreateProperties().put(Constants.SERVICE_ALIAS, alias);
		}
	}
	
	public Map<String, Object> getProperties(){
		return this.props;
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
		
	protected synchronized Map<String, Object> getOrCreateProperties(){
		if (this.props == null){
			this.props = new HashMap<String, Object>();
		}
		
		return this.props;
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
		ExportServiceDefinition other = (ExportServiceDefinition) obj;
		if (declaringTypeName == null) {
			if (other.declaringTypeName != null)
				return false;
		} else if (!declaringTypeName.equals(other.declaringTypeName))
			return false;
		return true;
	}

	
}
