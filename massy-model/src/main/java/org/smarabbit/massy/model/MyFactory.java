/**
 * 
 */
package org.smarabbit.massy.model;

import org.smarabbit.massy.MassyUtils;

/**
 * @author huangkaihui
 *
 */
public abstract class MyFactory {

	/**
	 * 获取当前的{@link My}
	 * @return
	 * 			{@link My},不能返回null.
	 */
	public static My getCurrent(){
		return MassyUtils.getDefaultContext().getService(My.class);
	}
	
}
