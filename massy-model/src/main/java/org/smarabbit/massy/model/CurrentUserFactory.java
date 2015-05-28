/**
 * 
 */
package org.smarabbit.massy.model;

import org.smarabbit.massy.MassyContext;
import org.smarabbit.massy.MassyUtils;

/**
 * @author huangkaihui
 *
 */
public abstract class CurrentUserFactory {

	/**
	 * 获取当前的{@link CurrentUser}
	 * @return
	 * 			{@link CurrentUser},不能返回null.
	 */
	public static CurrentUser getCurrent(){
		MassyContext context = MassyUtils.getDefaultContext();
		return MassyUtils.adapt(context, CurrentUser.class);
	}
	
}
