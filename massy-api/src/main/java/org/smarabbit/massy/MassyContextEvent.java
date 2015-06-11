/**
 * 
 */
package org.smarabbit.massy;

import java.util.EventObject;

/**
 * MassyContext事件
 * @author huangkaihui
 *
 */
public class MassyContextEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5755990806784815644L;
	
	
	/**
	 * @param source
	 */
	public MassyContextEvent(MassyContext source) {
		super(source);
	}

	public MassyContext getMassyContext(){
		return (MassyContext)source;
	}
}
