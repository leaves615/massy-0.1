/**
 * 
 */
package org.smarabbit.massy.axon.auditing;

import java.util.List;

import org.axonframework.auditing.AuditLogger;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.domain.EventMessage;
import org.smarabbit.massy.axon.EventBusSupportObject;

/**
 * @author huangkaihui
 *
 */
public class DefaultAuditLogger extends EventBusSupportObject implements AuditLogger {

	/**
	 * 
	 */
	public DefaultAuditLogger() {
		
	}

	/* (non-Javadoc)
	 * @see org.axonframework.auditing.AuditLogger#logSuccessful(org.axonframework.commandhandling.CommandMessage, java.lang.Object, java.util.List)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void logSuccessful(CommandMessage<?> command, Object returnValue,
			List<EventMessage> events) {
		String description = this.getDescription(command);
		String resourceTypeName = CommandMessageUtils.getResourceTypeName(command);
		String resourceIdentifier = CommandMessageUtils.getResourceIdentifier(command);
		
		@SuppressWarnings("unchecked")
		AuditEntry entry = new AuditEntry(command, List.class.cast(events), description, 
				resourceTypeName, resourceIdentifier, returnValue);
		CommandProcessCompletedEvent event =
				new CommandProcessCompletedEvent(entry, CommandMessageUtils.getTimestamp(command));
		
		this.sendEvent(event);
	}

	/* (non-Javadoc)
	 * @see org.axonframework.auditing.AuditLogger#logFailed(org.axonframework.commandhandling.CommandMessage, java.lang.Throwable, java.util.List)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void logFailed(CommandMessage<?> command, Throwable failureCause,
			List<EventMessage> events) {
		
		String description = CommandMessageUtils.getDescription(command);
		String resourceTypeName = CommandMessageUtils.getResourceTypeName(command);
		String resourceIdentifier = CommandMessageUtils.getResourceIdentifier(command);
		
		@SuppressWarnings("unchecked")
		AuditEntry entry = new AuditEntry(command, List.class.cast(events), description,
				resourceTypeName, resourceIdentifier, failureCause);
		CommandProcessCompletedEvent event =
				new CommandProcessCompletedEvent(entry, CommandMessageUtils.getTimestamp(command));
		
		this.sendEvent(event);
	}

	protected String getDescription(CommandMessage<?> command){
		return CommandDescriptionRegistryFactory.getDefault().getDescription(command);
	}
}
