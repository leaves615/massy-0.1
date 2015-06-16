/**
 * 
 */
package org.smarabbit.massy.axon;

import java.util.Map;

import org.axonframework.eventhandling.EventBus;
import org.smarabbit.massy.MassyContext;
import org.smarabbit.massy.MassyContextEvent;
import org.smarabbit.massy.MassyContextEventListener;
import org.smarabbit.massy.launch.AbstractPlugInActivator;
import org.smarabbit.massy.launch.MassyLaunchException;
import org.smarabbit.massy.model.event.EventPublisher;
import org.smarabbit.massy.service.ServiceRegistry;

/**
 * @author huangkaihui
 *
 */
public class EventPublishPlugInActivator extends AbstractPlugInActivator {

	/**
	 * 
	 */
	public EventPublishPlugInActivator() {
	}
	
	

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.launch.PlugInActivator#start(org.smarabbit.massy.MassyContext, java.util.Map)
	 */
	@Override
	public void start(MassyContext context, Map<String, Object> initParams)
			throws MassyLaunchException {
	
		ServiceRegistry registry = this.getServiceRegistry(context);
		this.add(registry.register(MassyContextEventListener.class, 
				new ContextEventListener(), null));
	}

	private class ContextEventListener implements MassyContextEventListener {

		@Override
		public void onEvent(MassyContextEvent event) {
			if (event.isStarted()){
				ServiceRegistry registry = event.getMassyContext().getService(ServiceRegistry.class);
				EventBus eventBus = registry.findService(EventBus.class);
				if (eventBus != null){
					EventPublisher publisher = new AxonEventPublisher(eventBus);
					registry.register(EventPublisher.class, publisher, null);
				}
			}
			
		}
		
	}
}
