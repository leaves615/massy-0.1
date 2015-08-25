/**
 * 
 */
package org.smarabbit.massy.launch;

import java.util.Map;

import org.smarabbit.massy.MassyContext;

/**
 * @author huangkaihui
 *
 */
public interface PlugInActivator {

	/**
	 * 启动
	 * @param context {@link MassyContext},非空
	 * @param initParams {@link Map},初始化参数，非空
	 * @throws MassyLaunchException
	 * 		发生启动异常
	 */
	void start(MassyContext context, Map<String, Object> initParams)
			throws MassyLaunchException;
	
	/**
	 * 停止
	 * @param context
	 */
	void stop(MassyContext context);
}
