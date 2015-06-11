/**
 * 
 */
package org.smarabbit.massy.model.persistent.jdbc;

/**
 * Jdbc数据库模式
 * @author huangkaihui
 *
 */
public interface JdbcSchema {
	
	/**
	 * Empty对象
	 */
	static String[] EMPTY = new String[0];

	/**
	 * 获取数据定义语言集合
	 * 
	 * @return
	 * 		字符串数组，不能返回null.
	 */
	String[] getDataDefinitionLanguages();
	
	/**
	 * 获取预处理命令
	 * @return
	 * 		字符串数组，不能返回null.
	 */
	String[] getPreprocessCommands();
}
