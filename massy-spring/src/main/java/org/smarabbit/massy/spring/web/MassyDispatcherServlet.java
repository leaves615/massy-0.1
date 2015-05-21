/**
 * 
 */
package org.smarabbit.massy.spring.web;


import java.util.HashMap;
import java.util.Map;

import org.smarabbit.massy.Constants;
import org.smarabbit.massy.MassyUtils;
import org.smarabbit.massy.Registration;
import org.smarabbit.massy.launch.MassyLaunchException;
import org.smarabbit.massy.service.ServiceRepository;
import org.smarabbit.massy.spring.MassyApplicationContext;
import org.smarabbit.massy.spring.MassyResource;
import org.smarabbit.massy.spring.context.MassyXmlWebApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * 派生于Spring的{@link DispatcherServlet}，增加ApplicationContext服务的注册与注销
 * 
 * @author huangkh
 *
 */
public class MassyDispatcherServlet extends DispatcherServlet {

	private Registration registration;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2651123838521146676L;
	
	private Class<?> contextClass = MassyXmlWebApplicationContext.class;

	/**
	 * 
	 */
	public MassyDispatcherServlet() {
	}

	/**
	 * @param webApplicationContext
	 */
	public MassyDispatcherServlet(WebApplicationContext webApplicationContext) {
		super(webApplicationContext);
	}
	

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.FrameworkServlet#initWebApplicationContext()
	 */
	@Override
	protected WebApplicationContext initWebApplicationContext() {
		WebApplicationContext wac =  super.initWebApplicationContext();
	
		//注册ApplicationContext服务		
		Map<String, Object>  props = null;
		if (wac instanceof MassyApplicationContext){
			props = new HashMap<String, Object>();
			MassyResource resource = ((MassyApplicationContext)wac).getMassyResource();
			props.put(Constants.SERVICE_CONTAINER, "spring");
			props.put(Constants.SERVICE_CONFIGFILE, resource.getName());
		}
		
		try{
			//注册ApplicationContext服务
			ServiceRepository repository = MassyUtils.getDefaultContext().getService(ServiceRepository.class);
			this.registration = repository.register(ApplicationContext.class, wac, props);
		}catch(Exception e){
				throw new MassyLaunchException( e);
		}
				
		return wac;
	}
	
	

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.FrameworkServlet#getContextClass()
	 */
	@Override
	public Class<?> getContextClass() {
		return this.contextClass;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.FrameworkServlet#destroy()
	 */
	@Override
	public void destroy() {
		//取消注册
		if (this.registration != null){
			this.registration.unregister();
			this.registration = null;
		}
		super.destroy();
	}
	
	
}
