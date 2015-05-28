/**
 * 
 */
package org.smarabbit.massy.shiro;

import org.apache.shiro.authz.AuthorizationInfo;


/**
 * {@link AuthorizationInfo}提供者,提供指定对象的{@link AuthorizationInfo}
 * @author huangkh
 *
 */
public interface AuthorizationInfoProvider{

	/**
	 * 获取指定编号对象的{@link AuthorizationInfo}
	 * @param principal 主角标识，具有唯一值
	 * @return 返回{@link AuthorizationInfo},不能返回null.
	 */
	AuthorizationInfo getAuthorizationInfo(Object principal);
}
