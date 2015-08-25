/**
 * 
 */
package org.smarabbit.massy.model.persistent;

/**
 * 存储模式处理器
 * <br>
 * 提供对存储模式的构建和数据初始化过程
 * @author huangkaihui
 *
 */
public interface PersistenceSchemaHandler {
	
	/**
	 * 构建存储模式 
	 * <br>
	 * 用于执行类似数据库的DDL语句，构建表/视图或者存储过程。
	 * @throws Exception
	 */
	void buildSchema() throws Exception;
	
	/**
	 * 预处理
	 * <br>
	 * 数据预处理过程
	 * 
	 * @throws Exception
	 */
	void preprocess() throws Exception;
}
