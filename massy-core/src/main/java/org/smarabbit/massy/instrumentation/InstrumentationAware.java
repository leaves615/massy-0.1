/**
 * 
 */
package org.smarabbit.massy.instrumentation;

import java.lang.instrument.Instrumentation;

/**
 * {@link Instrumentation}感知器
 * <br>
 * framework将以ServiceLoader方式加载所有实现{@link InstrumentationAware}的类，注入
 * {@link Instrumentation}实例。
 * <br>
 * 支持二进制增强的工具，可通过实现该接口的服务，获取到{@link Instrumentation}接口。
 * 
 * @author huangkh
 *
 */
public interface InstrumentationAware {

	/**
	 * 设置{@link Instrumentation}
	 * @param inst {@link Instrumentation}，不能为null.
	 */
	void setInstrumentation(Instrumentation inst);
}
