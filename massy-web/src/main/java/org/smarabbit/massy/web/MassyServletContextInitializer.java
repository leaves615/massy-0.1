/**
 * 
 */
package org.smarabbit.massy.web;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;

import org.apache.log4j.PropertyConfigurator;
import org.smarabbit.massy.Constants;
import org.smarabbit.massy.MassyContext;
import org.smarabbit.massy.MassyFramework;
import org.smarabbit.massy.launch.AbstractPlugInActivator;
import org.smarabbit.massy.launch.MassyLaunchException;
import org.smarabbit.massy.launch.PlugInActivator;
import org.smarabbit.massy.service.ServiceRegistry;

/**
 * Massy Servlet容器初始化器
 * <br>
 * 在Servlet3容器中，提供自动加载Massy Framework功能。
 * 
 * @author huangkaihui
 *
 */
public class MassyServletContextInitializer implements
		ServletContainerInitializer {

	private static final String PREFIX = "$.";
	
	private MassyFramework framework;
	
	/**
	 * 
	 */
	public MassyServletContextInitializer() {
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContainerInitializer#onStartup(java.util.Set, javax.servlet.ServletContext)
	 */
	@Override
	public void onStartup(Set<Class<?>> c, ServletContext ctx)
			throws ServletException {
		//log4j config
		this.configLog4J(ctx);
		
		//创建初始化参数，同时launch框架
		Map<String, Object> initParams = this.createInitParams(ctx);
		framework = new MassyFramework();
		framework.start(initParams);
		
		ctx.addListener(new ServlceContextDestory());
	}
	
	protected Map<String, Object> createInitParams(ServletContext ctx){
		Map<String, Object> result = new HashMap<String, Object>();
		
		//复制ServletContext中的以launch.起头的参数
		Enumeration<String> em = ctx.getInitParameterNames();
		int index = PREFIX.length();
		while (em.hasMoreElements()){
			String key = em.nextElement();
			if (key.startsWith(PREFIX)){
				result.put(key.substring(index),  ctx.getInitParameter(key));
			}		
		}
						
		//添加预处理的初始化器
		List<PlugInActivator> list = new ArrayList<PlugInActivator>();
		list.add(new ServletContextPlugInActivator(ctx));
		
		result.put(Constants.PREPEND_PLUGINS, list);
		
		return result;
	}
	
	/**
	 * 配置log4j
	 * @param ctx
	 * @throws ServletException
	 */
	protected void configLog4J(ServletContext ctx)
			throws ServletException {
		String location = ctx.getInitParameter("log4j.configuration");
		if (location != null){
			URL url;
			try {
				url = ctx.getResource(location);
				PropertyConfigurator.configure(url);
			} catch (MalformedURLException e) {
			}
		}
	}
	
	private class ServlceContextDestory implements ServletContextListener{

		@Override
		public void contextInitialized(ServletContextEvent sce) {
			
		}

		@Override
		public void contextDestroyed(ServletContextEvent sce) {
			MassyServletContextInitializer.this.framework.stop();			
		}
		
	}
	
	private class ServletContextPlugInActivator extends AbstractPlugInActivator {

		private ServletContext sc;
		
		public ServletContextPlugInActivator(ServletContext sc) {
			this.sc = sc;
		}
		
		/* (non-Javadoc)
		 * @see org.smarabbit.massy.launch.PlugInActivator#start(org.smarabbit.massy.MassyContext, java.util.Map)
		 */
		@Override
		public void start(MassyContext context, Map<String, Object> initParams)
				throws MassyLaunchException {
			ServiceRegistry registry = this.getServiceRegistry(context);
			Map<String, Object> props = new HashMap<String, Object>();
			props.put(Constants.DESCRIPTION, 
					"Servlet上下文，提供Web应用所需的Servlet、Filter、Listener、Session等的管理。");
			try {
				this.add(registry.register(ServletContext.class, sc, props));
			} catch (Exception e) {
				throw new MassyLaunchException(e.getMessage(),e);
			}
			
		}		
	}

}
