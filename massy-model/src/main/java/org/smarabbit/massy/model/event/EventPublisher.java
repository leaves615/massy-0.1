/**
 * 
 */
package org.smarabbit.massy.model.event;

/**
 * @author huangkaihui
 *
 */
public interface EventPublisher {

	/**
	 * 发送事件
	 * @param event 事件
	 */
	void publish(Object event);
}
