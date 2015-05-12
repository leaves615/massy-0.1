/**
 * 
 */
package org.smarabbit.massy;

import org.smarabbit.massy.util.Asserts;

/**
 * 属性不存在异常
 * @author huangkaihui
 *
 */
public class PropertyNotFoundException extends MassyException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4044443200393468517L;

	private final String propertyName;
	
	/**
	 * 
	 */
	public PropertyNotFoundException(String propertyName) {
		Asserts.argumentNotNull(propertyName, "propertyName");
		this.propertyName = propertyName;
	}

	/**
	 * @return the propertyName
	 */
	public String getPropertyName() {
		return propertyName;
	}

	@Override
	public String getMessage() {
		return "property not found: " + this.propertyName;
	}

	
}
