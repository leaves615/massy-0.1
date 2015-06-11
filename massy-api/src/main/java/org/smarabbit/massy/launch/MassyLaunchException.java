/**
 * 
 */
package org.smarabbit.massy.launch;

import org.smarabbit.massy.MassyException;

/**
 * @author huangkaihui
 *
 */
public class MassyLaunchException extends MassyException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -64738151393601274L;

	/**
	 * 
	 */
	public MassyLaunchException() {

	}

	/**
	 * @param message
	 */
	public MassyLaunchException(String message) {
		super(message);

	}

	/**
	 * @param cause
	 */
	public MassyLaunchException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MassyLaunchException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public MassyLaunchException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
