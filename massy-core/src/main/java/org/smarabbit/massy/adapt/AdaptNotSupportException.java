/**
 * 
 */
package org.smarabbit.massy.adapt;

import org.smarabbit.massy.MassyException;
import org.smarabbit.massy.util.Asserts;

/**
 * 不支持适配异常
 * @author huangkh
 *
 */
public class AdaptNotSupportException extends MassyException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 280189020388871304L;

	private final Class<?> targetType;
	private final Class<?> adaptType;
	
	/**
	 * 
	 */
	public AdaptNotSupportException(Class<?> targetType, Class<?> adaptType) {
		Asserts.argumentNotNull(targetType, "targetType");
		Asserts.argumentNotNull(adaptType, "adapType");
		
		this.targetType = targetType;
		this.adaptType = adaptType;
	}

	/**
	 * @return the target
	 */
	public Object getTargetType() {
		return targetType;
	}

	/**
	 * @return the adaptType
	 */
	public Class<?> getAdaptType() {
		return adaptType;
	}

	@Override
	public String getMessage() {
		return this.targetType.getName() + " cannot adapt " + this.adaptType.getName();
	}
	
	

}
