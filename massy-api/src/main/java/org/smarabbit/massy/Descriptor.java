/**
 * 
 */
package org.smarabbit.massy;

/**
 * 提供键/值对辅助说明
 * @author huangkaihui
 *
 */
public interface Descriptor {

	/**
	 * 判断属性是否存在
	 * @param name 
	 * 		属性名，非空
	 * @return
	 * 		true表示存在，否则返回false
	 */
	boolean contains(String name);
	
	/**
	 * 获取属性值
	 * @param name 
	 * 		属性名,非空
	 * @return 
	 * 		属性值，属性不存在则返回null.
	 */
	Object getProperty(String name);
	
	/**
	 * 获取属性值
	 * @param name 
	 * 		属性名,非空
	 * @param propType 
	 * 		属性类型，非空
	 * @return 
	 * 		属性值，属性不存在则返回null.
	 */
	<P> P getProperty(String name, Class<P> propType);
	
	/**
	 * 获取属性值，如果属性不存在则返回缺省值
	 * @param name 
	 * 		属性名，非空
	 * @param defaultValue 
	 * 		缺省值
	 * @param propType 
	 * 		属性类型，非空
	 * @return 
	 * 		属性值,属性不存在则返回缺省值
	 */
	<P> P getProperty(String name, P defaultValue, Class<P> propType);
	
	/**
	 * 获取所有的属性名称
	 * @return 
	 * 		字符串数组，无属性返回empty数组
	 */
	String[] getPropertyNames();
}
