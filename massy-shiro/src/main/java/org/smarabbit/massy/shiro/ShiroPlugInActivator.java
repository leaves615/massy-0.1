/**
 * 
 */
package org.smarabbit.massy.shiro;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.util.Factory;
import org.smarabbit.massy.Constants;
import org.smarabbit.massy.MassyContext;
import org.smarabbit.massy.MassyContextEvent;
import org.smarabbit.massy.MassyContextEventListener;
import org.smarabbit.massy.MassyException;
import org.smarabbit.massy.annotation.Order;
import org.smarabbit.massy.annotation.Ordered;
import org.smarabbit.massy.launch.AbstractPlugInActivator;
import org.smarabbit.massy.launch.MassyLaunchException;
import org.smarabbit.massy.model.My;
import org.smarabbit.massy.service.OrderComparator;
import org.smarabbit.massy.service.ServiceRegistration;
import org.smarabbit.massy.service.ServiceRegistry;
import org.smarabbit.massy.util.CollectionUtils;

/**
 * Shiro上下文初始化
 * @author huangkaihui
 *
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ShiroPlugInActivator extends AbstractPlugInActivator {

	public static final String INIPATH = "/META-INF/massy/config/shiro.ini";
	/**
	 * 
	 */
	public ShiroPlugInActivator() {
	}
	
	

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.launch.PlugInActivator#start(org.smarabbit.massy.MassyContext, java.util.Map)
	 */
	@Override
	public void start(MassyContext context, Map<String, Object> initParams)
			throws MassyLaunchException {
		ServiceRegistry registry =
				this.getServiceRegistry(context);
		this.add(registry.register(MassyContextEventListener.class, 
				new ContextEventListener(), null));
	}
	
	private class ContextEventListener implements MassyContextEventListener{

		@Override
		public void onEvent(MassyContextEvent event) {
			if (event.isStarted()){
				ServiceRegistry registry = event.getMassyContext().getService(ServiceRegistry.class);
				if (!this.hasServletContext(registry)){
					//非ServletContext模式下，创建Shiro安全组件
					try{			
						ShiroPlugInActivator.this.add( 
								this.registerSecurityManager(registry));
					}catch(Exception e){
						throw new MassyLaunchException(e.getMessage(),e);
					}
				}
				
				ShiroPlugInActivator.this.add(this.registerMyServiceFactory(registry));
			}
		}
		
		protected ServiceRegistration registerMyServiceFactory(ServiceRegistry registry){
			return registry.register(new Class<?>[]{My.class}, new MyServiceFactory(), null);
		}

		/**
		 * 注册Shiro SecurityManager
		 * @param repository
		 * @return
		 * @throws IllegalAccessException 
		 * @throws InstantiationException 
		 * @throws MassyException 
		 * @throws IOException 
		 * @throws ConfigurationException 
		 */
		protected ServiceRegistration registerSecurityManager(ServiceRegistry registry) 
				throws Exception{
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			URL url = loader.getResource(INIPATH);
			Ini ini = new Ini();
			if (url != null){
				ini.load(url.openStream());
			}
			
			Factory<SecurityManager> factory = new IniSecurityManagerFactory(ini) ;
			SecurityUtils.setSecurityManager(factory.getInstance());
	        if (factory.getInstance() instanceof DefaultSecurityManager){
	        	DefaultSecurityManager securityManager =(DefaultSecurityManager)factory.getInstance();
	        	//绑定Realm
	        	this.bindRealms(registry, securityManager);
	        }
	        
	        Map<String, Object> props = new HashMap<String, Object>();
	        props.put(Constants.DESCRIPTION, "shiro安全管理组件");
	       return registry.register(SecurityManager.class, factory.getInstance(), props);
		}
		
		/**
		 * 绑定Realm
		 * @param repository
		 * @param securityManager
		 */
		protected void bindRealms(ServiceRegistry registry, DefaultSecurityManager securityManager){
			//查找所有Realm服务
			Realm[] realms =registry.getAllServices(Realm.class);
			List<Realm> list = Arrays.asList(realms);
			
			//附加SecurityManager的Realms
			Collection<Realm> c = securityManager.getRealms();
			if (!CollectionUtils.isEmpty(c)){
				
				//排重
				for (Realm realm :c){
					if (!list.contains(realm)){
						list.add(realm);
					}
				}
			}
			
			if (!list.isEmpty()){
				//排序
				Collections.sort(list, new OrderComparator());
				securityManager.setRealms(list);
			}
		}
		
		/**
		 * 判断是否存在ServletContext上下文
		 * @param repository
		 * 			{@link ServiceRegistry}，服务仓储
		 * @return
		 * 			true表示存在，false表示不存在
		 */
		protected boolean hasServletContext(ServiceRegistry registry){
			try{
				//通过是否存在ServletContext服务来判断
				Class<?> serviceType = Thread.currentThread().getContextClassLoader().loadClass("javax.servlet.ServletContext");
				return registry.findService(serviceType) != null;
			}catch(Throwable e){
				return false;
			}
		}
		
	}
	
	
}
