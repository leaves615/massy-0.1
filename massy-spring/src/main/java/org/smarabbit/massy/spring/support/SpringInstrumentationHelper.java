/**
 * 
 */
package org.smarabbit.massy.spring.support;

import java.lang.instrument.Instrumentation;

import org.smarabbit.massy.instrumentation.InstrumentationAware;
import org.springframework.instrument.InstrumentationSavingAgent;

/**
 * @author huangkaihui
 *
 */
public class SpringInstrumentationHelper implements InstrumentationAware {

	/**
	 * 
	 */
	public SpringInstrumentationHelper() {
		
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.instrumentation.InstrumentationAware#setInstrumentation(java.lang.instrument.Instrumentation)
	 */
	@Override
	public void setInstrumentation(Instrumentation inst) {
			InstrumentationSavingAgent.agentmain(null, inst);
	}

	
}
