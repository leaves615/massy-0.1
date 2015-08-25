/**
 * 
 */
package org.smarabbit.massy.axon.auditing;

/**
 *{@link CommandDescriptionRegistry}工厂 
 * @author huangkaihui
 *
 */
public abstract class CommandDescriptionRegistryFactory {
	
	private static CommandDescriptionRegistry INSTANCE =
			new CommandDescriptionRegistry();

	public static CommandDescriptionRegistry getDefault(){
		return INSTANCE;
	}
}
