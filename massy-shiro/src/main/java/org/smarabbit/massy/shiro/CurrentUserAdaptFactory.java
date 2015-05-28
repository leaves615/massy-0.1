/**
 * 
 */
package org.smarabbit.massy.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smarabbit.massy.MassyContext;
import org.smarabbit.massy.adapt.AdaptFactory;
import org.smarabbit.massy.annotation.Adapt;
import org.smarabbit.massy.annotation.Description;
import org.smarabbit.massy.model.AnonymousUser;
import org.smarabbit.massy.model.CurrentUser;

/**
 * CurrentUser适配工厂
 * @author huangkaihui
 *
 */
@Adapt(adaptType = CurrentUser.class, extendStrategy = {MassyContext.class })
@Description("Shiro的CurrentUser 适配工厂")
public class CurrentUserAdaptFactory implements AdaptFactory<CurrentUser> {

	private static final Logger logger = LoggerFactory.getLogger(CurrentUserAdaptFactory.class);
	private static AnonymousUser annoymous = new AnonymousUser();
	
	/**
	 * 
	 */
	public CurrentUserAdaptFactory() {

	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.adapt.AdaptFactory#getAdaptor(java.lang.Object)
	 */
	@Override
	public CurrentUser getAdaptor(Object target) {
		try{
			Subject subject = SecurityUtils.getSubject();
			Object obj = subject.getPrincipal();

			if (obj instanceof CurrentUser){
				return (CurrentUser)subject.getPrincipal();
			}else{
				return annoymous;
			}
		}catch(Exception e){
			if (logger.isWarnEnabled()){
				logger.warn(e.getMessage(), e);
			}
			return null;
		}
	}

	
}
