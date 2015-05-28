/**
 * 
 */
package org.smarabbit.massy.model.metadata;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smarabbit.massy.util.Asserts;

/**
 * 字段元数据
 * @author huangkaihui
 *
 */
public class FieldMetadata {

	private static final Logger logger = LoggerFactory.getLogger(FieldMetadata.class);
	private final Field field;
	
	/**
	 * 
	 */
	protected FieldMetadata(Field field) {
		Asserts.argumentNotNull(field, "field");
		this.field = field;
		if (!this.field.isAccessible()){
			this.field.setAccessible(true);
		}
	}

	public String getName(){
		return this.field.getName();
	}

	/**
	 * 字段
	 * @return the field
	 */
	public Field getField() {
		return field;
	}
	
	/**
	 * 字段类型
	 * @return
	 */
	public Class<?> getType(){
		return this.field.getType();
	}
	
	/**
	 * 声明类
	 * @return
	 */
	public Class<?> getDeclaringClass(){
		return this.field.getDeclaringClass();
	}
	
	/**
	 * 获取字段值
	 * @param target 对象实例
	 * @return  字段值
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public Object getFieldValue(Object target) {
		try {
			return this.field.get(target);
		} catch (Exception e) {
			if (logger.isErrorEnabled()){
				logger.error(e.getMessage(), e);
			}
			return null;
		}
	}
	
	/**
	 * 设置字段值
	 * @param target 对象实例
	 * @param value 字段值
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setFieldValue(Object target, Object value) throws IllegalArgumentException, IllegalAccessException,
		ClassNotFoundException{
		Asserts.argumentNotNull(target, "target");
		
		Class<?> fieldType = field.getType();
		//类型兼容
		if (this.isCompatibleType(value, fieldType)){
			field.set(target, value);
		}else{
			//枚举
			 if (fieldType.isEnum()){
              	  //Class转换对应enum  
				 Class clazz = Class.forName(fieldType.getName());
				 field.set(target, Enum.valueOf(clazz, (String) value));                 
			 }
		}
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
