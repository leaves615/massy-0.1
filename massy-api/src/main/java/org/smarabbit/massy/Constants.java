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
	 * 服务定义
	 */
	static final String SERVICE_DEFINITION = "service.definition";
	
	/**
	 * 管理服务的容器，例如Spring
	 */
	static final String SERVICE_CONTAINER = "service.container";
	
	/**
	 * 定义服务的配置文件
	 */
	static final String SERVICE_CONFIGFILE= "service.configFile";
	
	/**
	 * 配置文件模式,定义按何种次序加载配置文件
	 */
	static final String CONFIGFILE_PATTERNS = "configFile.patterns";
	
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
	 * 前置插件，在ServiceLoader加载其他插件前加载.
	 */
	static final String PREPEND_PLUGINS = "prepend.plugins";

}
