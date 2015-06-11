/**
 * 
 */
package org.smarabbit.massy.model;

/**
 * 可扮演解色
 * @author huangkaihui
 *
 */
public interface Actable {

	/**
	 * 编号
	 * @return
	 */
	String getId();
	
	/**
	 * 显示名称
	 * <br>
	 * 昵称或者登录名
	 * @return
	 */
	String getDisplayName();
}
