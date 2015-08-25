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
	
	public static final int STARTED    = 0;
	public static final int STOPPING = 1;
	
	private int status;
	
	/**
	 * @param source
	 */
	public MassyContextEvent(MassyContext source, int status) {
		super(source);
		this.status = status;
	}

	public MassyContext getMassyContext(){
		return (MassyContext)source;
	}
	
	public boolean isStarted(){
		return this.status == STARTED;
	}
	
	public boolean isStopping(){
		return this.status == STOPPING;
	}
}
