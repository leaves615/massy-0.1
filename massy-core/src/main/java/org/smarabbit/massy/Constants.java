/**
 * 
 */
package org.smarabbit.massy;

/**
 * 常量
 * 
 * @author huangkaihui
 *
 */
public interface Constants {

	/**
	 * 服务编号,系统会为每一个服务分配一个唯一的服务编号。
	 */
	static final String SERVICE_ID = "service.id";
	
	/**
	 * 服务类型
	 */
	static final String SERVICE_TYPES = "service.types";
	
	/**
	 * 服务别名，用于区分同类型服务。
	 */
	static final String SERVICE_ALIAS = "service.alias";
	
	/**
	 * 服务配置文件
	 */
	static final String SERVICE_CONFIGFILE = "service.configFile";
	
	/**
	 * 说明
	 */
	static final String DESCRIPTION = "description";
	
	/**
	 * 对象类型
	 */
	static final String OBJECT_CLASS = "object.class";
	
	/**
	 * 代理工厂类型
	 */
	static final String PROXYFACTORY_CLASS = "proxyFactory.class";
	
	/**
	 * 适配类型
	 */
	static final String ADAPT_TYPE = "adapt.type";
		
	/**
	 * 前置初始化器，在ServiceLoader加载的初始化器前执行初始化,List<MassyContextInitializer>对象.
	 */
	static final String PREPEND_INITIALIZERS = "prepend.initializers";

	/**
	 * 应用程序属性
	 */
	static final String APPLICATION_PROPERTIES = "/META-INF/massy/config/application.properties";
}
