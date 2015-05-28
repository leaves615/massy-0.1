/**
 * 
 */
package org.smarabbit.massy.model.persistent;


/**
 * @author huangkaihui
 *
 */
public class InitSchemaException extends PersistentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6183559646182593068L;

	/**
	 * 
	 */
	public InitSchemaException() {
	}

	/**
	 * @param message
	 */
	public InitSchemaException(String message) {
		super(message);

	}

	/**
	 * @param cause
	 */
	public InitSchemaException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InitSchemaException(String message, Throwable cause) {
		super(message, cause);

	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public InitSchemaException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

}
