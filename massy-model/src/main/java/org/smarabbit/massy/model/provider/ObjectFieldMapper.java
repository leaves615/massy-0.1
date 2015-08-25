/**
 * 
 */
package org.smarabbit.massy.model.provider;

import java.util.LinkedList;

import org.smarabbit.massy.model.metadata.ClassMetadata;
import org.smarabbit.massy.model.metadata.ClassMetadataRegistryFactory;
import org.smarabbit.massy.model.metadata.FieldMetadata;
import org.smarabbit.massy.util.Asserts;

/**
 * 对象字段映射器
 * 
 * @author huangkaihui
 */
public class ObjectFieldMapper extends SimpleNamedMapper implements NamedMapper {

	private Class<?> entityType;
	private LinkedList<ClassMetadata> metadatas;
		
	/**
	 * 
	 */
	public ObjectFieldMapper(Class<?> entityType) {
		Asserts.argumentNotNull(entityType, "entityType");
		this.entityType = entityType;
		this.init();
	}
	
	/**
	 * 初始化
	 */
	protected void init(){
		this.metadatas = ClassMetadataRegistryFactory.getDefault().getMetadatas(entityType);
		
		for (ClassMetadata metadata: this.metadatas){
			String[] fieldNames = metadata.getFieldNames();
			for (String fieldName: fieldNames){
				this.add(fieldName, fieldName);
			}
		}
	}
	
	protected Object getValue(String name, Object entity){
		Asserts.argumentNotNull(name, "name");
		Asserts.argumentNotNull(entity, "entity");
		
		String fieldName = this.getMappingName(name);
		for (ClassMetadata metadata : this.metadatas){
			if (metadata.contains(fieldName)){
				FieldMetadata fieldDesc = metadata.getFieldMetadata(fieldName);
				return fieldDesc.getFieldValue(entity);
			}
		}
		return null;
	}
	
	
	/**
	 * @return the entityType
	 */
	protected Class<?> getEntityType() {
		return entityType;
	}

	/**
	 * @return the descriptors
	 */
	protected LinkedList<ClassMetadata> getClassMetadatas() {
		return this.metadatas;
	}

}
