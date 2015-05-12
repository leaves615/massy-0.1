/**
 * 
 */
package org.smarabbit.massy.instrumentation;

import java.lang.instrument.Instrumentation;
import java.util.Map;

import org.smarabbit.massy.MassyContext;
import org.smarabbit.massy.annotation.Order;
import org.smarabbit.massy.launch.MassyContextInitializer;
import org.smarabbit.massy.launch.MassyContextInitializerChain;
import org.smarabbit.massy.launch.MassyLaunchException;
import org.smarabbit.massy.support.Ordered;
import org.smarabbit.massy.util.LogUtils;

/**
 * 增强代理初始化器，为应用系统提供动态增强功能支持。 <br>
 * 只能用于jdk1.6以上版本
 * 
 * @author huangkh
 * 
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class InstrumentationAgentInitializer implements MassyContextInitializer {

	/**
	 * 
	 */
	public InstrumentationAgentInitializer() {
	}

	@Override
	public void onInit(MassyContext context, Map<String, Object> initParams,
			MassyContextInitializerChain chain) throws MassyLaunchException {

		Instrumentation inst;
		try {
			inst = InstrumentationFactory.getInstrumentation();
			if (inst == null) {
				if (LogUtils.isWarnEnabled()) {
					LogUtils.warn("attch Instrumentation failed.");
				}
			} else {
				if (LogUtils.isDebugEnabled()) {
					LogUtils.debug("attch Instrumentation success.");
				}
			}
		} catch (Exception e) {
			if (LogUtils.isWarnEnabled()) {
				LogUtils.warn("attch Instrumentation failed.");
			}
		}

		// 继续链调度
		chain.proceed(context, initParams);
	}

}
