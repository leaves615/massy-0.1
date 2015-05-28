/**
 * 
 */
package org.smarabbit.massy.axon;

import org.axonframework.domain.GenericEventMessage;
import org.axonframework.eventhandling.EventBus;
import org.smarabbit.massy.annotation.ImportService;

/**
 * {@link EventBus}支持对象，提供同步和异步发送事件方法
 * @author huangkaihui
 *
 */
public class EventBusSupportObject {

	@ImportService
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
	 * 异步发送EventMessage
	 * @param event
	 */
	protected <E> void postEvent(final E event){
		new Thread(new Runnable(){

			@Override
			public void run() {
				EventBusSupportObject.this.sendEvent(event);
			}
			
		});
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
	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}

}
