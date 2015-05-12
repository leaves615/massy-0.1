/**
 * 
 */
package org.smarabbit.massy.launch;

import java.util.Map;

import org.smarabbit.massy.MassyContext;

/**
 * {@link MassyContext}初始化器，通过定义该接口的实现类，以ServiceLoader方式在Massy Launch时执行初始化
 * @author huangkh
 *
 */
public interface MassyContextInitializer {

	/**
	 * 初始化处理
	 * @param context 
	 * 			{@link MassyContext},非空
	 * @param initParams
	 * 			{@link Map},初始化参数，非空
	 * @param chain
	 * 			{@link MassyContextInitializerChain},调度链，非空
	 * @throws MassyLaunchException
	 * 			发生异常则抛出
	 */
	void onInit(MassyContext context, Map<String, Object> initParams, MassyContextInitializerChain chain) 
			throws MassyLaunchException;
}
