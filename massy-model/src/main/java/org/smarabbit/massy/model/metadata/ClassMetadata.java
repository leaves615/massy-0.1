/**
 * 
 */
package org.smarabbit.massy.model.metadata;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.smarabbit.massy.util.Asserts;

/**
 * 类元数据
 * @author huangkaihui
 *
 */
public class ClassMetadata {

	private Class<?> type;
	private Map<String, FieldMetadata>  fields = new HashMap<String, FieldMetadata>();
	/**
	 * 
	 */
	public ClassMetadata(Class<?> type) {
		Asserts.argumentNotNull(type, "type");
		this.type = type;
		this.init();
	}

	public Class<?> getType(){
		return this.type;
	}
	
	public boolean contains(String fieldName){
		return this.fields.containsKey(fieldName);
	}
	
	/**
	 * 所有字段名
	 * @return
	 */
	public String[] getFieldNames(){
		return this.fields.keySet().toArray(new String[this.fields.size()]);
	}
	/**
	 * 按字段名获取{@link FieldMetadata}
	 * @param fieldName 字段名
	 * @return
	 * 				{@link FieldMetadata}
	 */
	public FieldMetadata getFieldMetadata(String fieldName){
		Asserts.argumentNotNull(fieldName, "fieldName");
		return this.fields.get(fieldName);
	}
	
	protected void init(){
		Field[] fields = this.type.getDeclaredFields();
		for (Field field: fields){
			if (!Modifier.isTransient(field.getModifiers())){
				if (!Modifier.isStatic(field.getModifiers())){
						FieldMetadata metadata = new FieldMetadata(field);
						this.fields.put(metadata.getName(), metadata);
				}
			}
		}
	}
}
