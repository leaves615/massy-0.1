/**
 * 
 */
package org.smarabbit.massy.spring.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.smarabbit.massy.Constants;
import org.smarabbit.massy.MassyUtils;
import org.smarabbit.massy.launch.MassyLaunchException;
import org.smarabbit.massy.service.ServiceRegistration;
import org.smarabbit.massy.service.ServiceRegistry;
import org.smarabbit.massy.spring.MassyApplicationContext;
import org.smarabbit.massy.spring.MassyResource;
import org.smarabbit.massy.spring.context.MassyXmlWebApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

/**
 * 派生于Spring的{@link ContextLoaderListener}。
 * <br>
 * 增加以下功能：
 * <ul>
 * <li>调整默认加载配置文件为"classpath*:META-INF/massy/context/webcontext.xml"。</li>
 * <li>ApplicationContext的服务注册和注销</li>
 * <li>
 * </ul>
 * @author huangkh
 *
 */
public class MassyContextLoaderListener extends ContextLoaderListener {

	private ServiceRegistration registration;
	/**
	 * 
	 */
	public MassyContextLoaderListener() {
	}

	/**
	 * @param context
	 */
	public MassyContextLoaderListener(WebApplicationContext context) {
		super(context);
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
	
		//注册ApplicationContext服务
		ServletContext servletContext = event.getServletContext();		
		WebApplicationContext wac = (WebApplicationContext)servletContext.getAttribute(
				WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		
		if (this.registration == null){
			Map<String, Object> props = null;
			if (wac instanceof MassyApplicationContext){
				props = new HashMap<String, Object>();
				MassyResource resource = ((MassyApplicationContext)wac).getMassyResource();
				props.put(Constants.SERVICE_CONTAINER, "spring");
				props.put(Constants.SERVICE_CONFIGFILE, resource.getName());
			}
			
			//注册ApplicationContext服务
			try {
				ServiceRegistry registry = MassyUtils.getDefaultContext().getService(ServiceRegistry.class);
				this.registration = registry.register(ApplicationContext.class, wac, props);
			} catch (Exception e) {
				throw new MassyLaunchException(e.getMessage(),e);
			}
		}
		
	}
	
	/**
	 * 注销服务
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event){
		if (this.registration != null){
			this.registration.unregister();
			this.registration = null;
		}
		super.contextDestroyed(event);
	}
	
	@Override
	protected Class<?> determineContextClass(ServletContext servletContext) {
		String contextClassName = servletContext.getInitParameter(CONTEXT_CLASS_PARAM);
		if (contextClassName != null) {
			try {
				return ClassUtils.forName(contextClassName, ClassUtils.getDefaultClassLoader());
			}
			catch (ClassNotFoundException ex) {
				throw new ApplicationContextException(
						"Failed to load custom context class [" + contextClassName + "]", ex);
			}
		}
		else {
			return MassyXmlWebApplicationContext.class;
		}
	}
}
