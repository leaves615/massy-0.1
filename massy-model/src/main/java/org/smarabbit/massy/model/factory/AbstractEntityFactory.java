/**
 * 
 */
package org.smarabbit.massy.model.factory;

import java.util.LinkedList;

import org.smarabbit.massy.model.metadata.ClassMetadata;
import org.smarabbit.massy.model.metadata.ClassMetadataRegistry;
import org.smarabbit.massy.model.metadata.ClassMetadataRegistryFactory;
import org.smarabbit.massy.model.metadata.FieldMetadata;
import org.smarabbit.massy.model.provider.DataProvider;
import org.smarabbit.massy.util.ClassUtils;

/**
 * 实体工厂抽象基类
 * @author huangkaihui
 *
 */
public abstract class AbstractEntityFactory {

	/**
	 * 
	 */
	public AbstractEntityFactory() {

	}
	
	/**
	 * 执行创建实例
	 * @param entityType
	 * @param descriptor
	 * @return
	 * @throws Exception
	 */
	protected <T> T doCreate(Class<T> entityType, DataProvider provider) throws  Exception{				
		T result = this.createEntity(entityType, provider);
		this.initEntity(result, provider);
		return result;
	}
	
	/**
	 * 初始化实体对象
	 * @param entity
	 * @param descriptor
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	protected  void initEntity(Object entity, DataProvider provider) 
			throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException{
		Class<?> entityType = entity.getClass();
		ClassMetadataRegistry registry = ClassMetadataRegistryFactory.getDefault();
		LinkedList<ClassMetadata> metadatas = registry.getMetadatas(entityType);
		
		String[] fieldNames = provider.getNames();
		for (String fieldName: fieldNames){
			FieldMetadata fieldMetadata = null;
			for (ClassMetadata metadata: metadatas ){
				fieldMetadata = metadata.getFieldMetadata(fieldName);
				
				if (fieldMetadata != null){
					break;
				}
			}
			
			if (fieldMetadata != null){
				fieldMetadata.setFieldValue(entity, provider.getValue(fieldName));
			}
		}
	}
		
	protected <T> T createEntity(Class<T> entityType , DataProvider mapValue) 
			throws Exception{
		return ClassUtils.newInstance(entityType);
	}
		
	/**
	 * 判断类型是否兼容
	 * @param value 值
	 * @param type 实体字段的类型
	 * @return
	 * 			true表示兼容，否则返回false.
	 */
	protected boolean isCompatibleType(Object value, Class<?> type) {
        // Do object check first, then primitives
        if (value == null || type.isInstance(value)) {
            return true;

        } else if (type.equals(Integer.TYPE) && value instanceof Integer) {
            return true;

        } else if (type.equals(Long.TYPE) && value instanceof Long) {
            return true;

        } else if (type.equals(Double.TYPE) && value instanceof Double) {
            return true;

        } else if (type.equals(Float.TYPE) && value instanceof Float) {
            return true;

        } else if (type.equals(Short.TYPE) && value instanceof Short) {
            return true;

        } else if (type.equals(Byte.TYPE) && value instanceof Byte) {
            return true;

        } else if (type.equals(Character.TYPE) && value instanceof Character) {
            return true;

        } else if (type.equals(Boolean.TYPE) && value instanceof Boolean) {
            return true;

        }
        return false;
    }
	
}
