/**
 * 
 */
package org.smarabbit.massy;

/**
 * @author huangkaihui
 *
 */
public interface MassyContextEventListener {

	void onStarting(MassyContextEvent event);
	
	void onStarted(MassyContextEvent event);
	
	void onStopping(MassyContextEvent event);
	
	void onStopped(MassyContextEvent event);
}
