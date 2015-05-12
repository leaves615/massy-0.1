/**
 * 
 */
package org.smarabbit.massy.support;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 引用计数器
 * @author huangkaihui
 *
 */
public class ReferenceCounter {

	//引用计数
	private AtomicLong count;
	
	/**
	 * 
	 */
	public ReferenceCounter(long initialValue) {
		this.count = new AtomicLong(initialValue);
	}
	
	/**
	 * 增加引用计数
	 * @return
	 */
	public long increase(){
		return this.count.getAndAdd(1);
	}
	
	/**
	 * 获取当前计数
	 * @return
	 */
	public long get(){
		return this.count.get();
	}

}
