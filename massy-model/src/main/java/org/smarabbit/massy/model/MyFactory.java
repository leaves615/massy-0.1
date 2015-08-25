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
	
	/**
	 * 转换可扮演角色
	 * @param actorType
	 * @return
	 */
	public static <A extends Actable> A runAs(Class<A> actorType){
		return getCurrent().runAs(actorType);
	}
}
