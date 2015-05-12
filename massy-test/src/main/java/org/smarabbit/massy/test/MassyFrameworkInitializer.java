/**
 * 
 */
package org.smarabbit.massy.test;

import java.util.HashMap;
import java.util.Map;

import org.smarabbit.massy.MassyUtils;
import org.smarabbit.massy.launch.MassyLauncher;

/**
 * Massy Framework 初始化
 * @author huangkaihui
 *
 */
public abstract class MassyFrameworkInitializer {

	private static boolean LAUNCHED = false;
	
	public static boolean launch(Map<String, Object> initParams){
		synchronized(MassyFrameworkInitializer.class) {
			if (!LAUNCHED){
				Map<String, Object> params = initParams == null ?
						new HashMap<String, Object>() :
							new HashMap<String, Object>(initParams);
						
				MassyLauncher launcher = MassyUtils.create();
				launcher.launch(params);
				
				LAUNCHED = true;
			}
		}
		
		return LAUNCHED;
	}

}
