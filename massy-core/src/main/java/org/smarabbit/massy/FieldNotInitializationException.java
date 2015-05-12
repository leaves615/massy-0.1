/**
 * 
 */
package org.smarabbit.massy;

/**
 * @author huangkaihui
 *
 */
public class FieldNotInitializationException extends MassyException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8530353813871574813L;

	private final String fieldName;
	/**
	 * 
	 */
	public FieldNotInitializationException(String fieldName) {
		this.fieldName = fieldName;
	}
	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}
	
	@Override
	public String getMessage() {
		return "field [" + this.fieldName + "] is not initialize, please set it.";
	}

}
