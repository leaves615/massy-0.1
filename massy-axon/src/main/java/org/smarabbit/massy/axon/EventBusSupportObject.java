/**
 * 
 */
package org.smarabbit.massy.axon;

import javax.annotation.Resource;

import org.axonframework.domain.GenericEventMessage;
import org.axonframework.eventhandling.EventBus;

/**
 * {@link EventBus}支持对象，提供同步和异步发送事件方法
 * @author huangkaihui
 *
 */
public class EventBusSupportObject {

	private EventBus eventBus;
	
	/**
	 * 
	 */
	public EventBusSupportObject() {
		
	}
	
	/**
	 * 同步发送EventMessage
	 * @param event
	 */
	protected <E> void sendEvent(E event){
		this.getEventBus().publish(new GenericEventMessage<E>(event));
	}
	
	/**
	 * @return the eventBus
	 */
	public EventBus getEventBus() {
		return eventBus;
	}

	/**
	 * @param eventBus the eventBus to set
	 */
	@Resource(type= EventBus.class)
	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}

}
