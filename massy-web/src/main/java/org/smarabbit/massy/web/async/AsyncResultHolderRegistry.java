/**
 * 
 */
package org.smarabbit.massy.web.async;

import java.util.concurrent.ConcurrentHashMap;

import org.smarabbit.massy.util.Asserts;

/**
 * 抽象的异步结果句柄注册器，提供{@link AsyncResultHolder}注册方法
 * @author huangkaihui
 *
 */
public abstract class AsyncResultHolderRegistry<K, T> {

	private ConcurrentHashMap<K, AsyncResultHolder<T>> holderMap =
			new ConcurrentHashMap<K, AsyncResultHolder<T>>();
	
	/**
	 * 
	 */
	public AsyncResultHolderRegistry() {
		
	}

	/**
	 * 添加{@link AsyncResultHolder}
	 * @param key 键值
	 * @param holder {@link AsyncResultHolder},非空
	 */
	public void addHolder(K key, AsyncResultHolder<T> holder){
		Asserts.argumentNotNull(key, "key");
		Asserts.argumentNotNull(holder, "holder");
		
		this.holderMap.put(key, holder);
	}
	
	/**
	 * 移除{@link AsyncResultHolder}
	 * @param key 键值
	 * @return
	 * 		{@link AsyncResultHolder},可能返回null.
	 */
	protected AsyncResultHolder<T> remove(K key){
		Asserts.argumentNotNull(key, "key");
		return this.holderMap.remove(key);
	}
}