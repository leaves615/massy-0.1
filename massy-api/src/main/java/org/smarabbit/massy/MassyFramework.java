/**
 * 
 */
package org.smarabbit.massy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.smarabbit.massy.launch.MassyLaunchException;
import org.smarabbit.massy.launch.MassyLauncher;
import org.smarabbit.massy.launch.PlugInActivator;
import org.smarabbit.massy.launch.PlugInLoader;
import org.smarabbit.massy.service.ServiceRegistration;
import org.smarabbit.massy.service.ServiceRegistry;
import org.smarabbit.massy.util.Asserts;
import org.smarabbit.massy.util.CollectionUtils;
import org.smarabbit.massy.util.ServiceLoaderUtils;

/**
 * Massy Framework
 * @author huangkaihui
 *
 */
public class MassyFramework  extends PlugInLoader{
	
	private ClassLoader loader;
	private List<ServiceRegistration> registrations =
			new ArrayList<ServiceRegistration>();
		
	/**
	 * 
	 */
	public MassyFramework() {
		this(Thread.currentThread().getContextClassLoader());
	}
	
	public MassyFramework(ClassLoader loader){
		Asserts.argumentNotNull(loader, "loader");
		this.loader = loader;
	}
	
	/**
	 *  初始化
	 */
	public void install(){
		
	}
	
	/**
	 * 反初始化
	 */
	public void uninstall(){
		
	}
	
	/**
	 * 启动
	 * @param params 参数
	 * @throws MassyLaunchException 发生启动异常
	 */
	public synchronized void start(Map<String, Object> params) 
			throws MassyLaunchException{
		if (this.isRunning()){
			return;
		}
		
		Map<String, Object> initParams = params == null ? 
				new HashMap<String, Object>() :
					new HashMap<String, Object>(params);
		
		MassyContext context = this.doLaunch(initParams);
		MassyUtils.setDefaultContext(context);
				
		this.startPlugins(context, initParams);
		this.notifyEvent(context, MassyContextEvent.STARTED);
	}
	
	/**
	 * 停止
	 */
	public synchronized void stop(){
		if (MassyUtils.INSTANCE == null){
			return;
		}
		
		MassyContext context = MassyUtils.getDefaultContext();
		this.notifyEvent(context, MassyContextEvent.STOPPING);
		this.stopPlugins(context);
		this.unregisterPlugInActivators();
		
		MassyUtils.setDefaultContext(null);
	}
	
	/**
	 * 是否处于运行状态
	 * @return
	 */
	public boolean isRunning(){
		//判断是否存在MassyContext实例
		return MassyUtils.INSTANCE != null;
	}
	
	/**
	 * 启动
	 * @param initParams
	 * @return
	 * @throws MassyLaunchException
	 */
	protected MassyContext doLaunch(Map<String, Object> initParams)
		throws MassyLaunchException{
		MassyLauncher launcher  = ServiceLoaderUtils.loadFirstService(MassyLauncher.class, this.loader);
		if (launcher == null){
			throw new MassyLaunchException("cannot found MassyLauncher from ServieLoader.");
		}
		
		MassyContext result = launcher.launch(initParams);
		
		List<PlugInActivator> plugins = this.loadPlugInActivators(initParams, loader);
		ServiceRegistry registry = result.getService(ServiceRegistry.class);
		this.registerPlugInActivators(plugins, registry);
		
		return result;
	}
	
	/**
	 * 启动所有插件
	 * @param context
	 * @param initParams
	 */
	protected void startPlugins(MassyContext context, Map<String, Object> initParams){
		PlugInActivator[] activators = 
				context.getAllServices(PlugInActivator.class);
		
		int size = activators.length;
		if (size > 0){
			for (int i=0; i<size; i++){
				PlugInActivator activator = activators[i];
				activator.start(context, initParams);
			}
		}
	}
	
	/**
	 * 停止所有插件
	 * @param context
	 */
	protected void stopPlugins(MassyContext context){
		PlugInActivator[] activators = 
				context.getAllServices(PlugInActivator.class);
		
		int size = activators.length;
		if (size > 0){
			for (int i=0; i<size; i++){
				PlugInActivator activator = activators[i];
				activator.stop(context);
			}
		}
	}
	
	/**
	 * 事件通知发送
	 * @param status
	 */
	protected void notifyEvent(MassyContext context, int status){
		MassyContextEventListener[] listeners = 
				context.getAllServices(MassyContextEventListener.class);
		int size = listeners.length;
		if (size >0){
			MassyContextEvent event =
					new MassyContextEvent(context, status);
			for (int i=0; i<size; i++){
				listeners[i].onEvent(event);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.launch.PlugInLoader#addRegistration(org.smarabbit.massy.service.ServiceRegistration)
	 */
	@Override
	protected synchronized void addRegistration(ServiceRegistration registration) {
			this.registrations.add(registration);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.launch.PlugInLoader#unregisterAll()
	 */
	@Override
	protected synchronized void unregisterPlugInActivators() {
		if (!CollectionUtils.isEmpty(this.registrations)){
			for (ServiceRegistration registration: registrations){
				registration.unregister();
			}
		}
		
		this.registrations.clear();
	}
	
	/**
	 * 设置ClassLoader
	 * @param loader
	 */
	protected void setClassLoader(ClassLoader loader){
		Asserts.argumentNotNull(loader, "loader");
		this.loader = loader;
	}
}
