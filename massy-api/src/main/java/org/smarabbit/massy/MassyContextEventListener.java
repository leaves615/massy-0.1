/**
 * 
 */
package org.smarabbit.massy;

/**
 * @author huangkaihui
 *
 */
public interface MassyContextEventListener {
	
	/**
	 * 事件触发
	 * @param event {@link MassyContextEvent},非空
	 */
	void onEvent(MassyContextEvent event);
	
}
