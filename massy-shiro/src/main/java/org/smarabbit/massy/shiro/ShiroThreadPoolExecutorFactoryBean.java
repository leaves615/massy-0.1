/**
 * 
 */
package org.smarabbit.massy.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.SubjectRunnable;
import org.apache.shiro.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;

/**
 * Shiro线程池执行器工厂
 * @author huangkaihui
 *
 */
public class ShiroThreadPoolExecutorFactoryBean extends
		ThreadPoolExecutorFactoryBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5640787364488230674L;
	private final Logger logger = LoggerFactory.getLogger(ShiroThreadPoolExecutorFactoryBean.class);
	/**
	 * 
	 */
	public ShiroThreadPoolExecutorFactoryBean() {
	}

	/* (non-Javadoc)
	 * @see org.springframework.util.CustomizableThreadCreator#createThread(java.lang.Runnable)
	 */
	@Override
	public Thread createThread(Runnable runnable) {
		return super.createThread(
				maybeToShiroRunnable(runnable));
	}

	/**
	 * 如果支持Shiro场景，就转化为ShiroRunnable
	 * @param runnable
	 * @return
	 */
	protected Runnable maybeToShiroRunnable(Runnable runnable){
		Subject subject = null;
		
		try{
			subject = ThreadContext.getSubject();
		}catch(Exception e){
			if (logger.isWarnEnabled()){
				logger.warn("cannot found subject from current thread.");
			}
		}
		
		return  subject != null ?
				new SubjectRunnable(subject, runnable) :
					runnable;
	}
}
