/**
 * 
 */
package org.smarabbit.massy.spring;

import org.springframework.context.ApplicationContext;

/**
 * 扩展{@link ApplicationContext},提供{@link MassyResource}
 * @author huangkaihui
 *
 */
public interface MassyApplicationContext  extends ApplicationContext{

	/**
	 * 获取{@link MassyResource}
	 * @return
	 * 		{@link MassyResource},不能返回null.
	 */
	MassyResource getMassyResource();
}
