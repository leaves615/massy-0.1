/**
 * 
 */
package org.smarabbit.massy.axon.auditing;

import java.io.Serializable;
import java.util.List;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.domain.EventMessage;
import org.smarabbit.massy.util.Asserts;

/**
 * 审计记录
 * @author huangkaihui
 *
 */
public class AuditEntry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2690367812774819573L;

	private final String description;
	
	private final CommandMessage<?>  command;
	private final List<EventMessage<?>> events;
	
	private final String resourceTypeName;
	private final String resourceIdentifier;
	
	private final boolean hasSuccess;
	private final Object result;
	private final Throwable cause;
		
	/**
	 * 
	 */
	public AuditEntry(CommandMessage<?> command, List<EventMessage<?>> events,
			String description, String resourceTypeName, String resourceIdentifier, Object result) {
		Asserts.argumentNotNull(command, "command");
		Asserts.argumentNotNull(events, "events");

		
		this.hasSuccess = true;
		
		this.command = command;
		this.events = events;
		this.description = description;
		
		this.resourceTypeName = resourceTypeName;
		this.resourceIdentifier = resourceIdentifier;
		
		this.result = result;
		
		this.cause = null;
	}
	
	public AuditEntry(CommandMessage<?> command, List<EventMessage<?>> events,
			String description, String resourceTypeName, String resourceIdentifier,  Throwable cause){
		Asserts.argumentNotNull(command, "command");
		Asserts.argumentNotNull(events, "events");
		
		this.hasSuccess = false;
		
		this.command = command;
		this.events = events;
		this.description = description;
		
		this.resourceTypeName = resourceTypeName;
		this.resourceIdentifier = resourceIdentifier;
		
		this.cause = cause;
				
		this.result = null;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the command
	 */
	public CommandMessage<?> getCommandMessage() {
		return command;
	}

	/**
	 * @return the events
	 */
	public List<EventMessage<?>> getEventMessages() {
		return events;
	}

	/**
	 * @return the hasSuccess
	 */
	public boolean hasSuccess() {
		return this.hasSuccess;
	}

	/**
	 * @return the result
	 */
	public Object getResult() {
		return result;
	}

	/**
	 * @return the cause
	 */
	public Throwable getCause() {
		return cause;
	}

	/**
	 * @return the resourceTypeName
	 */
	public String getResourceTypeName() {
		return resourceTypeName;
	}

	/**
	 * @return the resourceIdentifier
	 */
	public String getResourceIdentifier() {
		return resourceIdentifier;
	}

}
