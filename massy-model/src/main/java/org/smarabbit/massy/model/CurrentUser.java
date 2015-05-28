/**
 * 
 */
package org.smarabbit.massy.model;


/**
 * 当前用户
 * @author huangkaihui
 *
 */
public interface CurrentUser {

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
	
	/**
	 * 是否通过身份认证
	 * @return
	 */
	boolean isAuthenticated();
}
