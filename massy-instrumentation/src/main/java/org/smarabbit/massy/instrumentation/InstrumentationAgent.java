/**
 * 
 */
package org.smarabbit.massy.instrumentation;

import java.lang.instrument.Instrumentation;

/**
 * 增强接口代理类，实现JDK增强接口所必须的{@link #premain(String, Instrumentation)}和
 * {@link #agentmain(String, Instrumentation)}方法。
 * <br>
 * 可以支持javaagent参数方式，在程序启动时由java运行环境注入{@link Instrumentation},
 * 也可以支持JDK6动态附加方式，在程序启动后由java虚拟机动态注入{@link Instrumentation}
 * 
 * @author huangkh
 *
 */
public abstract class InstrumentationAgent {

	private static volatile Instrumentation instrumentation;
	
	/**
	 * 
	 * JDK5：设置Instrumentation
	 * @param agentArgs
	 * @param inst
	 */
	public static void premain(String agentArgs, Instrumentation inst) {
		instrumentation = new InstrumentationWrapper(inst);
	}
	
	/**
	 * JKD6：动态设置Instrumentation
	 * @param agentArgs
	 * @param inst
	 */
	public static void agentmain(String agentArgs, Instrumentation inst) {	
		instrumentation = new InstrumentationWrapper(inst);
	}
	
	/**
	 * 获取{@link Instrumentation}实例
	 * @return 返回{@link Instrumentation}，可能返回null.
	 */
	public static Instrumentation getInstrumentation(){
		return instrumentation;
	}
}
