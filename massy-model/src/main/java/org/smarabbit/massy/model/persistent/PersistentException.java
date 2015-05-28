/**
 * 
 */
package org.smarabbit.massy.model.persistent;

import org.smarabbit.massy.MassyException;

/**
 * 持久化异常
 * @author huangkaihui
 *
 */
public class PersistentException extends MassyException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7679772915512384861L;

	/**
	 * 
	 */
	public PersistentException() {
	}

	/**
	 * @param message
	 */
	public PersistentException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public PersistentException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PersistentException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public PersistentException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
