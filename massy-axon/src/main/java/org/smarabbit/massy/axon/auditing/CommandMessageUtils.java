/**
 * 
 */
package org.smarabbit.massy.axon.auditing;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.smarabbit.massy.util.Asserts;

/**
 * @author huangkaihui
 *
 */
public abstract class CommandMessageUtils {

	private static final String RESOURCE = "Resourceable";
	private static final String RESOURCE_TYPENAME = "Resourceable.TypeName";
	private static final String RESOURCE_IDENTIFIER = "Resourceable.Identifier";
	private static final String CREATE_TIMESTAMP = "Create.Timestamp";
	
	/**
	 * 创建CommandMessage
	 * @param command 命令
	 * @param resource 资源实例
	 * @param identifier 资源编号
	 * @return
	 * 			{@link CommandMessage},不能返回null.
	 */
	public static <C> CommandMessage<C> createCommandMessage(C command, Object resource, String identifier){
		Asserts.argumentNotNull(command, "command");
		Asserts.argumentNotNull(resource, "resource");
		Asserts.argumentNotNull(identifier, "identifier");
		
		Map<String, Object> metadata = new HashMap<String, Object>();
		metadata.put(RESOURCE, resource);
		metadata.put(RESOURCE_TYPENAME, resource.getClass().getName());
		metadata.put(RESOURCE_IDENTIFIER, identifier);
		metadata.put(CREATE_TIMESTAMP, new Timestamp(System.currentTimeMillis()));
		GenericCommandMessage<C> result =
				new GenericCommandMessage<C>(command, metadata);
		
		return result;
	}
	
	/**
	 * 创建CommandMessage
	 * @param command 命令
	 * @param resourceType 资源类型
	 * @param identifier 资源编号
	 * @return
	 * 			{@link CommandMessage},不能返回null.
	 */
	public static <C> CommandMessage<C> createCommandMessage(C command, Class<?> resourceType, String identifier){
		Asserts.argumentNotNull(command, "command");
		Asserts.argumentNotNull(resourceType, "resourceType");
		Asserts.argumentNotNull(identifier, "identifier");
		
		Map<String, Object> metadata = new HashMap<String, Object>();
		metadata.put(RESOURCE_TYPENAME, resourceType.getName());
		metadata.put(RESOURCE_IDENTIFIER, identifier);
		metadata.put(CREATE_TIMESTAMP, new Timestamp(System.currentTimeMillis()));
		GenericCommandMessage<C> result =
				new GenericCommandMessage<C>(command, metadata);
		
		return result;
	}
	
	/**
	 * 创建CommandMessage
	 * @param command 命令
	 * @param resourceType 资源类型
	 * @return
	 * 			{@link CommandMessage},不能返回null.
	 */
	public static <C> CommandMessage<C> createCommandMessage(C command, Class<?> resourceType){
		Asserts.argumentNotNull(command, "command");
		Asserts.argumentNotNull(resourceType, "resourceType");
		Map<String, Object> metadata = new HashMap<String, Object>();
		
		metadata.put(RESOURCE_TYPENAME, resourceType.getName());
		metadata.put(CREATE_TIMESTAMP, new Timestamp(System.currentTimeMillis()));
		GenericCommandMessage<C> result =
				new GenericCommandMessage<C>(command, metadata);
		
		return result;
	}
	
	public static <C> CommandMessage<C> createCommandMessage(C command){
		Asserts.argumentNotNull(command, "command");
		Map<String, Object> metadata = new HashMap<String, Object>();
		metadata.put(CREATE_TIMESTAMP, new Timestamp(System.currentTimeMillis()));
		
		GenericCommandMessage<C> result =
				new GenericCommandMessage<C>(command, metadata);
		
		return result;
	}
	
	/**
	 * 读取资源
	 * @param command {@link CommandMessage},不能为null.
	 * @return
	 */
	public static Object getResource(CommandMessage<?> command){
		Asserts.argumentNotNull(command, "command");
		
		return command.getMetaData().get(RESOURCE);
	}
	
	/**
	 * 
	 * @param command
	 * @return
	 */
	public static String getResourceTypeName(CommandMessage<?> command){
		Asserts.argumentNotNull(command, "command");
		
		return (String)command.getMetaData().get(RESOURCE_TYPENAME);
	}
	
	/**
	 * 获取资源的编号
	 * @param command {@link CommandMessage},不能为null.
	 * @return
	 * 			字符串，资源编号
	 */
	
	public static String getResourceIdentifier(CommandMessage<?> command){
		Asserts.argumentNotNull(command, "command");
		
		return (String)command.getMetaData().get(RESOURCE_IDENTIFIER);
	}
	
	/**
	 * 获取命令说明
	 * @param command  {@link CommandMessage},不能为null.
	 * @return
	 * 			字符串，命令的说明
	 */
	public static String getDescription(CommandMessage<?> command){
		Asserts.argumentNotNull(command, "command");
		
		return CommandDescriptionRegistryFactory.getDefault().getDescription(command);
	}
	
	/**
	 * 获取创建时间
	 * <br>
	 * 如果不存在，则取当前时间
	 * @param command
	 * @return
	 */
	public static Timestamp getTimestamp(CommandMessage<?> command){
		Asserts.argumentNotNull(command, "command");
		
		return command.getMetaData().containsKey(CREATE_TIMESTAMP) ?
					(Timestamp)command.getMetaData().get(CREATE_TIMESTAMP) :
						new Timestamp(System.currentTimeMillis());
		
	}
	
}
