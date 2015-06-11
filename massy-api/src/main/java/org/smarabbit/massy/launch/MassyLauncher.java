/**
 * 
 */
package org.smarabbit.massy.launch;

import java.util.Map;

import org.smarabbit.massy.MassyContext;
import org.smarabbit.massy.MassyException;

/**
 * Massy框架加载器,创建{@link MassyContext},并加载
 * {@link MassyContextInitializer}实例，执行初始化。
 * 
 * @author huangkh
 *
 */
public interface MassyLauncher {

	/**
	 * 创建并初始化{@link MassyContext}.
	 * 
	 * @param initParams 
	 * 		初始化参数, 非空
	 * @throws MassyException
	 * 		加载异常则抛出 
	 */
	MassyContext launch(Map<String, Object> initParams) throws MassyException;
}
