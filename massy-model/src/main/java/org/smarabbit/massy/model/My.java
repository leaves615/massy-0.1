/**
 * 
 */
package org.smarabbit.massy.model;


/**
 * 当前用户
 * @author huangkaihui
 *
 */
public interface My extends Actable{
	
	/**
	 * 是否通过身份认证
	 * @return
	 */
	boolean isAuthenticated();
		
	/**
	 * 转换为可扮演的角色
	 * @param actorType 角色类型
	 * @return
	 * 		{@link A},可能返回null.
	 */
	<A> A runAs(Class<A> actorType);
	
}
