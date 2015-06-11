/**
 * 
 */
package org.smarabbit.massy.model.persistent;

import org.smarabbit.massy.util.Asserts;

/**
 * @author huangkaihui
 *
 */
public class RebuildSchema {

	public static final String DEFAULT_LOCATIONS =
			"classpath*:META-INF/massy/config/persistence/persistence-config.xml";
	
	public RebuildSchema(String storeType){
		this(storeType, DEFAULT_LOCATIONS);
	}
	
	/**
	 * 
	 */
	public RebuildSchema(String storeType, String configurationLocations) {
		Asserts.argumentNotNull(storeType, "storeType");
		System.setProperty(PersistenceConstants.STORE_TYPE, storeType);
		
		
	}

}
