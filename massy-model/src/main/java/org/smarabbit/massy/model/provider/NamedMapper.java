/**
 * 
 */
package org.smarabbit.massy.model.provider;

/**
 * 命名映射器，和{@link DataProvider}配合，可提供命名的映射和转换等能力
 * @author huangkaihui
 *
 */
public interface NamedMapper {

	/**
	 * 是否包含名称
	 * @param name 名称
	 * @return
	 * 			true表示包含，否则返回false.
	 */
	boolean contains(String name);
	
	/**
	 * 获取名称
	 * @return
	 */
	String[] getNames();
	
	/**
	 * 获取映射名称
	 * @param name 名称
	 * @return
	 * 			字符串，返回映射名称
	 */
	String getMappingName(String name);
}
