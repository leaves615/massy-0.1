/**
 * 
 */
package org.smarabbit.massy.model;

/**
 * 缓存仓库
 * @author huangkaihui
 *
 */
public interface CachedRepository {

	/**
	 * 添加缓存记录
	 * @param type
	 * @param key
	 * @param object
	 */
	<K, V>void addEntry(Class<?> type, K identifier,  V object);
	
	/**
	 * 按键值查找缓存记录
	 * @param type
	 * @param identifier
	 * @return
	 */
	<K, V> V  findById(Class<?> type, K identifier);
	
	/**
	 * 移除缓存记录
	 * @param type
	 * @param identifier
	 */
	<K> void remove(Class<?> type, K identifier);
}
