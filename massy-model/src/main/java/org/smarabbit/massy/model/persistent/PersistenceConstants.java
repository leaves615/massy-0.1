/**
 * 
 */
package org.smarabbit.massy.model.persistent;

/**
 * 存储常量定义
 * @author huangkaihui
 *
 */
public interface PersistenceConstants {

	/**
	 * 存储类型
	 */
	static final String STORE_TYPE = "store.type"; 
	
	/**
	 * mySql 存储类型
	 */
	static final String STORE_TYPE_MYSQL = "mysql";
	
	/**
	 * mongodb 存储类型
	 */
	static final String STORE_TYPE_MONGO = "mongo";
	
}
