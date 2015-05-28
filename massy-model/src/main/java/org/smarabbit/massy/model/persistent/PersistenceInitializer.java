/**
 * 
 */
package org.smarabbit.massy.model.persistent;

import java.util.Map;

import org.smarabbit.massy.MassyContext;
import org.smarabbit.massy.launch.MassyContextInitializer;
import org.smarabbit.massy.launch.MassyContextInitializerChain;
import org.smarabbit.massy.launch.MassyLaunchException;

/**
 * @author huangkaihui
 *
 */
public class PersistenceInitializer implements MassyContextInitializer{

	/**
	 * 
	 */
	public PersistenceInitializer() {
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.launch.MassyContextInitializer#onInit(org.smarabbit.massy.MassyContext, java.util.Map, org.smarabbit.massy.launch.MassyContextInitializerChain)
	 */
	@Override
	public void onInit(MassyContext context, Map<String, Object> initParams,
			MassyContextInitializerChain chain) throws MassyLaunchException {
		if (initParams.containsKey(PersistenceConstants.STORE_TYPE)){
			String value = initParams.get(PersistenceConstants.STORE_TYPE).toString();
			System.setProperty(PersistenceConstants.STORE_TYPE, value);
		}else{
			String value = System.getProperty(PersistenceConstants.STORE_TYPE);
			if (value != null){
				initParams.put(PersistenceConstants.STORE_TYPE, value);
			}
		}

		chain.proceed(context, initParams);
	}

}
