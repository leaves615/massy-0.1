/**
 * 
 */
package org.smarabbit.massy.axon.auditing;

import java.util.HashMap;
import java.util.Map;

import org.axonframework.commandhandling.CommandMessage;
import org.smarabbit.massy.annotation.Description;
import org.smarabbit.massy.util.Asserts;

/**
 * @author huangkaihui
 *
 */
public class CommandDescriptionRegistry {

	private Map<Class<?>, String> map =
			new HashMap<Class<?>, String>();
	
	/**
	 * 
	 */
	public CommandDescriptionRegistry() {
		
	}

	public String getDescription(CommandMessage<?> command){
		Asserts.argumentNotNull(command, "command");
		Class<?> clazz = command.getPayloadType();
		if (map.containsKey(clazz)){
			return map.get(clazz);
		}else{
			Description description = clazz.getAnnotation(Description.class);
			String text = description == null ? null : description.value();
			map.put(clazz, text);
			
			return text;
		}
	}
}
