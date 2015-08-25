/**
 * 
 */
package org.smarabbit.massy.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.smarabbit.massy.model.AnonymousUser;
import org.smarabbit.massy.service.PrototypeServiceFactory;
import org.smarabbit.massy.service.ServiceFactory;

/**
 * @author huangkaihui
 *
 */
public class MyServiceFactory implements ServiceFactory,
		PrototypeServiceFactory {

	private static final AnonymousUser annonymouse =
			new AnonymousUser();
	
	/**
	 * 
	 */
	public MyServiceFactory() {
		
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.ServiceFactory#getService()
	 */
	@Override
	public Object getService() {
		Subject subject = SecurityUtils.getSubject();
		
		if (subject.isAuthenticated()){
			return subject.getPrincipal();
		}else{
			return annonymouse;
		}
	}

}
