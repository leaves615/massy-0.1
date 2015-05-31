/**
 * 
 */
package org.smarabbit.massy.web;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
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
import org.smarabbit.massy.MassyException;
import org.smarabbit.massy.MassyUtils;
import org.smarabbit.massy.instrumentation.InstrumentationFactory;
import org.smarabbit.massy.launch.MassyContextInitializer;
import org.smarabbit.massy.launch.MassyContextInitializerChain;
import org.smarabbit.massy.launch.MassyLaunchException;
import org.smarabbit.massy.launch.MassyLauncher;
import org.smarabbit.massy.service.ServiceRegistry;
import org.smarabbit.massy.util.LogUtils;

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
		//增强处理监听器
		ctx.addListener(new InstrumentationListener());
		
		//创建初始化参数，同时launch框架
		Map<String, Object> initParams = this.createInitParams(ctx);
		MassyLauncher launcher = MassyUtils.create();
		launcher.launch(initParams);
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
		List<MassyContextInitializer> list = new ArrayList<MassyContextInitializer>();
		list.add(new ServletContextInitializer(ctx));
		
		result.put(Constants.PREPEND_INITIALIZERS, list);
		
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
	
	private class ServletContextInitializer implements MassyContextInitializer {

		private ServletContext sc;
		
		public ServletContextInitializer(ServletContext sc) {
			this.sc = sc;
		}

		@Override
		public void onInit(MassyContext context, Map<String, Object> initParams, 
				MassyContextInitializerChain chain) throws MassyException {
			ServiceRegistry registry = context.getService(ServiceRegistry.class);
			Map<String, Object> props = new HashMap<String, Object>();
			props.put(Constants.DESCRIPTION, 
					"Servlet上下文，提供Web应用所需的Servlet、Filter、Listener、Session等的管理。");
			try {
				registry.register(ServletContext.class, sc, props);
			} catch (Exception e) {

				throw new MassyLaunchException(e.getMessage(),e);
			}
									
			chain.proceed(context, initParams);
		}
		
	}

	/**
	 * 停止web应用时，移除添加的ClassFileTransformer.
	 * @author huangkh
	 *
	 */
	private class InstrumentationListener implements ServletContextListener {

		private static final String METHODNAME = "removeAllClassFileTransformer";
		
		public InstrumentationListener() {
		}

		/* (non-Javadoc)
		 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
		 */
		@Override
		public void contextInitialized(ServletContextEvent sce) {
			
		}

		/* (non-Javadoc)
		 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
		 */
		@Override
		public void contextDestroyed(ServletContextEvent sce) {
			Instrumentation inst;
			try {
				inst = InstrumentationFactory.getInstrumentation();
				if (inst != null){
					Method method = inst.getClass().getDeclaredMethod(METHODNAME, new Class<?>[0]);
					if (method != null){
						method.invoke(inst, new Object[0]);
						
						if (LogUtils.isDebugEnabled()){
							LogUtils.debug("servletContext be close, remove all class transformer.");
						}
					}
				}
			} catch (Exception e) {
				if (LogUtils.isWarnEnabled()){
					LogUtils.warn("remove class transformer's failed.");
				}
			}
			
		}
	}
}
