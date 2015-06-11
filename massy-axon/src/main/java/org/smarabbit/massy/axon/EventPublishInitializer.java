/**
 * 
 */
package org.smarabbit.massy.axon;

import java.util.Map;

import org.axonframework.eventhandling.EventBus;
import org.smarabbit.massy.MassyContext;
import org.smarabbit.massy.launch.MassyContextInitializer;
import org.smarabbit.massy.launch.MassyContextInitializerChain;
import org.smarabbit.massy.launch.MassyLaunchException;
import org.smarabbit.massy.model.event.EventPublisher;
import org.smarabbit.massy.service.ServiceRegistry;

/**
 * @author huangkaihui
 *
 */
public class EventPublishInitializer implements MassyContextInitializer {

	/**
	 * 
	 */
	public EventPublishInitializer() {
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.launch.MassyContextInitializer#onInit(org.smarabbit.massy.MassyContext, java.util.Map, org.smarabbit.massy.launch.MassyContextInitializerChain)
	 */
	@Override
	public void onInit(MassyContext context, Map<String, Object> initParams,
			MassyContextInitializerChain chain) throws MassyLaunchException {
		chain.proceed(context, initParams);
		
		ServiceRegistry registry = context.getService(ServiceRegistry.class);
		EventBus eventBus = registry.findService(EventBus.class);
		if (eventBus != null){
			EventPublisher publisher = new AxonEventPublisher(eventBus);
			registry.register(EventPublisher.class, publisher, null);
		}
	}

}
