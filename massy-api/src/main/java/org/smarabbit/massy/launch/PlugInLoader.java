/**
 * 
 */
package org.smarabbit.massy.launch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.smarabbit.massy.Constants;
import org.smarabbit.massy.service.OrderComparator;
import org.smarabbit.massy.service.ServiceRegistration;
import org.smarabbit.massy.service.ServiceRegistry;
import org.smarabbit.massy.util.CollectionUtils;
import org.smarabbit.massy.util.ServiceLoaderUtils;

/**
 * 插件加载器
 * @author huangkaihui
 *
 */
public abstract class PlugInLoader {

	/**
	 * 
	 */
	public PlugInLoader() {
		
	}
	
	/**
	 * 加载插件
	 * @param initParams
	 * @param loader
	 * @return
	 */
	protected List<PlugInActivator> loadPlugInActivators(Map<String, Object> initParams, ClassLoader loader){
		List<PlugInActivator> result =
				this.doParsePlugInActivators(initParams);
		result.addAll(this.doLoadPlugInActivators(loader));
		
		if (!CollectionUtils.isEmpty(result)){
			Collections.sort(result, new OrderComparator());
		}
		return result;
	}
	
	/**
	 * 注册插件
	 * @param plugins
	 * @param registry
	 */
	protected void registerPlugInActivators(List<PlugInActivator> plugins, ServiceRegistry registry){
		if (!CollectionUtils.isEmpty(plugins)){
			for (PlugInActivator plugin : plugins){
				ServiceRegistration registration =
						registry.register(PlugInActivator.class, plugin, null);
				this.addRegistration(registration);
			}
		}
	}
	
	/**
	 * 解析初始化参数中的PlugInActivator.
	 * @param initParams
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<PlugInActivator> doParsePlugInActivators(Map<String, Object> initParams){
		List<PlugInActivator> plugins = 
				(List<PlugInActivator>)initParams.remove(Constants.PREPEND_PLUGINS);
		
		List<PlugInActivator> result = plugins == null ?
				new ArrayList<PlugInActivator>() :
					new ArrayList<PlugInActivator>(plugins);
						
		return result;
	}
	
	/**
	 * 通过ServiceLoader加载插件并排序
	 * @param loader
	 * @return
	 */
	protected List<PlugInActivator> doLoadPlugInActivators(ClassLoader loader){
		return		ServiceLoaderUtils.loadServices(PlugInActivator.class, loader);
	}

	/**
	 * 添加插件注册凭据
	 * @param registration
	 */
	protected abstract void addRegistration(ServiceRegistration registration);
	
	/**
	 * 撤销所有注册
	 */
	protected abstract void unregisterPlugInActivators();
}
