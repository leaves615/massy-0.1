/**
 * 
 */
package org.smarabbit.massy.axon;

import javax.annotation.Resource;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.smarabbit.massy.util.Asserts;

/**
 * {@link CommandGateway}支持对象，提供{@link CommandGateway}对象实例
 * @author huangkaihui
 *
 */
public class CommandGatewaySupportObject {

	private CommandGateway commandGateway;
	
	/**
	 * 
	 */
	public CommandGatewaySupportObject() {
		
	}
	
	public CommandGatewaySupportObject(CommandGateway commandGateway){
		Asserts.argumentNotNull(commandGateway, "commandGateway");
		this.commandGateway = commandGateway;
	}
	/**
	 * @return the commandGateway
	 */
	public CommandGateway getCommandGateway() {
		return commandGateway;
	}

	/**
	 * @param commandGateway the commandGateway to set
	 */
	@Resource(type = CommandGateway.class)
	public void setCommandGateway(CommandGateway commandGateway) {
		this.commandGateway = commandGateway;
	}

}
