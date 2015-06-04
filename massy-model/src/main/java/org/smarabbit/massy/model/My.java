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
	 * 获取演员角色
	 * @param actorType 演员类型
	 * @return 
	 * 			{@link A},可能返回null.
	 */
	<A> A asActor(Class<A> actorType);
}
