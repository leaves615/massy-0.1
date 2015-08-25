/**
 * 
 */
package org.smarabbit.massy.model.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smarabbit.massy.MassyUtils;
import org.smarabbit.massy.service.ServiceRegistry;

/**
 * @author huangkaihui
 *
 */
public abstract class EventPublisherUtils {

	private static final Logger logger =
			LoggerFactory.getLogger(EventPublisherUtils.class);
	private static volatile EventPublisher publisher;
		
	public static void publish(Object event){
		if (publisher != null){
			publisher.publish(event);
		}else{
			ServiceRegistry registry = MassyUtils.getDefaultContext().getService(ServiceRegistry.class);
			publisher = registry.findService(EventPublisher.class);
			if (publisher != null){
				publisher.publish(event);
			}else{
				if (logger.isWarnEnabled()){
					logger.warn("cannot publish event, because EventPublisher service not found.");
				}
			}
		}
	}
}
