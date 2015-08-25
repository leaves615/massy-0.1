/**
 * 
 */
package org.smarabbit.massy.model.metadata;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * {@link ClassMetadata}注册器
 * @author huangkaihui
 *
 */
public class ClassMetadataRegistry {

	private Map<Class<?>, ClassMetadata> map =
			new HashMap<Class<?>, ClassMetadata>();
	/**
	 * 
	 */
	public ClassMetadataRegistry() {
		
	}
	
	/**
	 * 获取对象对应的{@link ClassMetadata}
	 * @param obj {@link Object}
	 * @return
	 * 			{@link ClassMetadata},不能返回null.
	 */
	public ClassMetadata getMetadata(Object obj){
		return this.getMetadata(obj.getClass());
	}
	
	/**
	 * 获取类型对应的{@link ClassMetadata}
	 * @param type {@link Class}
	 * @return
	 * 			{@link ClassMetadata},不能返回null.
	 */
	public ClassMetadata getMetadata(Class<?> type){
		ClassMetadata result = map.get(type);
		if (result == null){
			this.resolve(type);
			result = map.get(type);
		}
		
		return result;
	}
	
	/**
	 * 按类型获取所有层次{@link ClassMetadata}
	 * @param type {@link Class}
	 * @return 
	 * 		{@link LinkedList},不能返回null.
	 */
	public LinkedList<ClassMetadata> getMetadatas(Class<?> type){
		List<Class<?>> hierarchy = getClassHierarchy(type);
		LinkedList<ClassMetadata> result = new LinkedList<ClassMetadata>();
		for (Class<?> clazz : hierarchy) {
			ClassMetadata desc = this.getMetadata(clazz);
			if (desc!=null) {
				result.add(desc);
			}
		}
		return result;
	}

	protected LinkedList<Class<?>> getClassHierarchy(Class<?> type){
		LinkedList<Class<?>> result = new LinkedList<Class<?>>();
		while (type!=null && !Object.class.equals(type)) {
			result.add(type);
			type = type.getSuperclass();
		}
		Collections.reverse(result);
		return result;
	}
	
	/**
	 * 解析类型，创建对应的{@link ClassMetadata}
	 * @param type
	 */
	protected void resolve(Class<?> type){
		if (type == null) return;
		if (type == Object.class) return;
		
		ClassMetadata metadata = new ClassMetadata(type);
		this.map.put(type, metadata);
		
		//继续对父类解析
		Class<?> parent = type.getSuperclass();
		if (!this.map.containsKey(parent)){
			this.resolve(parent);
		}
	}
}
