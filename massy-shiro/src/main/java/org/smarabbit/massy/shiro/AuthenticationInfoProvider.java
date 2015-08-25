/**
 * 
 */
package org.smarabbit.massy.shiro;

import org.apache.shiro.authc.AuthenticationInfo;

/**
 * {@link AuthenticationInfo}提供者,提供指定对象的{@link AuthenticationInfo}
 * 
 * @author huangkh
 *
 */
public interface AuthenticationInfoProvider {

	/**
	 * 获取指定编号的{@link AuthenticationInfo}
	 * @param principal 主角标识，具有唯一值
	 * @return 返回{@link AuthenticationInfo},无对应返回null.
	 */
	AuthenticationInfo getAuthenticationInfo(Object principal);
}
