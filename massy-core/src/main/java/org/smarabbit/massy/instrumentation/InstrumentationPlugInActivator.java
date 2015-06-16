/**
 * 
 */
package org.smarabbit.massy.instrumentation;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.util.Map;

import org.smarabbit.massy.MassyContext;
import org.smarabbit.massy.annotation.Order;
import org.smarabbit.massy.launch.AbstractPlugInActivator;
import org.smarabbit.massy.launch.MassyLaunchException;
import org.smarabbit.massy.util.LogUtils;

/**
 * 增强代理初始化器，为应用系统提供动态增强功能支持。 <br>
 * 只能用于jdk1.6以上版本
 * 
 * @author huangkh
 * 
 */
@Order(Integer.MIN_VALUE)
public class InstrumentationPlugInActivator extends AbstractPlugInActivator {

	private static final String METHODNAME = "removeAllClassFileTransformer";
	
	/**
	 * 
	 */
	public InstrumentationPlugInActivator() {
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.launch.PlugInActivator#start(org.smarabbit.massy.MassyContext, java.util.Map)
	 */
	@Override
	public void start(MassyContext context, Map<String, Object> initParams)
			throws MassyLaunchException {
		Instrumentation inst;
		try {
			inst = InstrumentationFactory.getInstrumentation();
			if (inst == null) {
				if (LogUtils.isWarnEnabled()) {
					LogUtils.warn("attch Instrumentation failed.");
				}
			} else {
				if (LogUtils.isInfoEnabled()) {
					LogUtils.info("attch Instrumentation success.");
				}
			}
		} catch (Exception e) {
			if (LogUtils.isWarnEnabled()) {
				LogUtils.warn("attch Instrumentation failed.");
			}
		}	
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.launch.AbstractPlugInActivator#stop(org.smarabbit.massy.MassyContext)
	 */
	@Override
	public void stop(MassyContext context) {
		super.stop(context);
		this.removeInstrumentation();
	}
	
	protected void removeInstrumentation(){
		Instrumentation inst;
		try {
			inst = InstrumentationFactory.getInstrumentation();
			if (inst != null){
				Method method = inst.getClass().getDeclaredMethod(METHODNAME, new Class<?>[0]);
				if (method != null){
					method.invoke(inst, new Object[0]);
					
					if (LogUtils.isDebugEnabled()){
						LogUtils.debug("servletContext be close, remove all class transformer.");
					}
				}
			}
		} catch (Exception e) {
			if (LogUtils.isWarnEnabled()){
				LogUtils.warn("remove class transformer's failed.");
			}
		}
	}
}
