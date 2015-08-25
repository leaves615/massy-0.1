/**
 * 
 */
package org.smarabbit.massy.launch;

import java.util.Map;

import org.smarabbit.massy.MassyContext;
import org.smarabbit.massy.MassyException;
import org.smarabbit.massy.support.DefaultMassyContext;
import org.smarabbit.massy.util.Asserts;

/**
 * @author huangkaihui
 *
 */
public class DefaultMassyLauncher implements MassyLauncher {

	/**
	 * 
	 */
	public DefaultMassyLauncher() {
		
	}
	
	/* (non-Javadoc)
	 * @see org.smarabbit.massy.launch.MassyLauncher#launch(java.util.Map)
	 */
	@Override
	public MassyContext launch(Map<String, Object> initParams)
			throws MassyException {
		Asserts.argumentNotNull(initParams, "initParams");
		
		MassyContext result = new DefaultMassyContext(initParams);
		
		return result;
	}

}
