/**
 * 
 */
package org.smarabbit.massy.launch;

import java.util.Map;

import org.smarabbit.massy.MassyContext;
import org.smarabbit.massy.MassyException;


/**
 * {@link MassyContextInitializer}调度链
 * @author huangkh
 *
 */
public interface MassyContextInitializerChain {

	/**
	 * 调用下一个链初始化
	 * @param context 
	 * 		{@link MassyContext},非空
	 * @param initParams
	 * 		{@link Map},初始化参数，非空
	 * @throws MassyException 
	 * 		初始化发生异常抛出
	 */
	void proceed(MassyContext context, Map<String, Object> initParams) throws MassyLaunchException;
}
