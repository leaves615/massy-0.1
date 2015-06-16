/**
 * 
 */
package org.smarabbit.massy.test;

import java.util.HashMap;
import java.util.Map;

import org.smarabbit.massy.MassyFramework;

/**
 * Massy Framework 初始化
 * @author huangkaihui
 *
 */
public abstract class MassyFrameworkInitializer {

	private static MassyFramework INSTANCE = null;
	
	public static boolean launch(Map<String, Object> initParams){
		synchronized(MassyFrameworkInitializer.class) {
			if (INSTANCE == null){
				Map<String, Object> params = initParams == null ?
						new HashMap<String, Object>() :
							new HashMap<String, Object>(initParams);
						
				INSTANCE = new MassyFramework();
				INSTANCE.start(params);
			}
		}
		
		return INSTANCE.isRunning();
	}

	public MassyFramework getFramework(){
		return INSTANCE;
	}
}
