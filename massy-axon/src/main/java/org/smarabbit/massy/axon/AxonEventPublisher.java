/**
 * 
 */
package org.smarabbit.massy.axon;

import org.axonframework.eventhandling.EventBus;
import org.smarabbit.massy.model.event.EventPublisher;
import org.smarabbit.massy.util.Asserts;

/**
 * @author huangkaihui
 *
 */
public class AxonEventPublisher implements EventPublisher {

	private EventBus eventBus ;
	
	/**
	 * 
	 */
	public AxonEventPublisher(EventBus eventBus) {
		Asserts.argumentNotNull(eventBus, "eventBus");
		this.eventBus = eventBus;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.model.support.EventPublisher#postEvent(java.lang.Object)
	 */
	@Override
	public void publish(Object event) {
		EventBusUtils.sendEventMessage(event, eventBus);
	}

}
