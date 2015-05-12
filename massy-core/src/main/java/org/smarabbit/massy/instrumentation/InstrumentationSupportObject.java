/**
 * 
 */
package org.smarabbit.massy.instrumentation;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;


/**
 * 同时实现{@link InstrumentationAware}和{@link ClassFileTransformer}接口的抽象类，
 * 在{@link #setInstrumentation(Instrumentation)}调用时，调用{@link Instrumentation#addTransformer(ClassFileTransformer)}
 * 注册自身.
 * 
 * @author huangkh
 *
 */
public abstract class InstrumentationSupportObject implements
		ClassFileTransformer, InstrumentationAware {

	
	/**
	 * 
	 */
	public InstrumentationSupportObject() {
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.instrumentation.InstrumentationAware#setInstrumentation(java.lang.instrument.Instrumentation)
	 */
	@Override
	public void setInstrumentation(Instrumentation inst) {
		inst.addTransformer(this);
	}
	
}
