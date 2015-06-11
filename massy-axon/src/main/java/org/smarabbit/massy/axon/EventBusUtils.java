/**
 * 
 */
package org.smarabbit.massy.axon;

import org.axonframework.domain.GenericEventMessage;
import org.axonframework.eventhandling.EventBus;
import org.smarabbit.massy.util.Asserts;

/**
 * EventBus 工具类
 * @author huangkaihui
 *
 */
public abstract class EventBusUtils {

	/**
	 * 发送EventMessage
	 * @param event {@link Object},非空
	 * @param eventBus {@link EventBus},非空
	 */
	public static <E> void sendEventMessage(E event, EventBus eventBus){
		Asserts.argumentNotNull(eventBus, "eventBus");
		Asserts.argumentNotNull(event, "event");
		
		eventBus.publish(new GenericEventMessage<E>(event));
	}
}
