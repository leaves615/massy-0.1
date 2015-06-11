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
	 * 开始启动事件
	 * @param event
	 */
	void onStarting(MassyContextEvent event);
	
	/**
	 * 完成启动事件
	 * @param event
	 */
	void onStarted(MassyContextEvent event);
	
	/**
	 * 开始停止事件
	 * @param event
	 */
	void onStopping(MassyContextEvent event);
	
	/**
	 * 完成停止事件
	 * @param event
	 */
	void onStopped(MassyContextEvent event);
}
