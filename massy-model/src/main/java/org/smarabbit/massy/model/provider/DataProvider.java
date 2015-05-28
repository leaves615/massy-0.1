/**
 * 
 */
package org.smarabbit.massy.model.provider;

/**
 * 数据提供者，用于ORM转换时按名称获取字段/数据列值
 * @author huangkaihui
 *
 */
public interface DataProvider {

	static final String[] EMPTY = new String[0];
	
	/**
	 * 是否包含指定名称
	 * @param name 名称
	 * @return 
	 * 			true表示包含，false表示不包含
	 */
	boolean contains(String name);
	
	/**
	 * 所有映射名称
	 * @return
	 */
	String[] getNames();
	
	/**
	 * 按名称获取值
	 * @param name 名称
	 * @return 
	 * 			{@link Object}
	 */
	Object getValue(String name);	
}
