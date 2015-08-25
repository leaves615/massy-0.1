/**
 * 
 */
package org.smarabbit.massy.model.persistent.jdbc;

/**
 * @author huangkaihui
 *
 */
public interface JdbcSchemaFactory {

	/**
	 * 创建{@link JdbcSchema}
	 * @param catagory 分类，可能为null.
	 * @return
	 * 		{@link JdbcSchema}
	 */
	JdbcSchema create(String catagory);
}
