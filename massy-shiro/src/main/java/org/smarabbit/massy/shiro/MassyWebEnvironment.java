/**
 * 
 */
package org.smarabbit.massy.shiro;

import org.apache.shiro.web.env.DefaultWebEnvironment;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.smarabbit.massy.MassyUtils;

/**
 * @author huangkaihui
 *
 */
public class MassyWebEnvironment extends DefaultWebEnvironment {

	/**
	 * 
	 */
	public MassyWebEnvironment() {
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.web.env.DefaultWebEnvironment#getWebSecurityManager()
	 */
	@Override
	public WebSecurityManager getWebSecurityManager() {
		WebSecurityManager securityManager = MassyUtils.getDefaultContext().getService(WebSecurityManager.class);
		return securityManager;
	}

	
}
