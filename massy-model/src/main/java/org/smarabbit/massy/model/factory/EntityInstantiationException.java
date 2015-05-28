/**
 * 
 */
package org.smarabbit.massy.model.factory;

import org.smarabbit.massy.MassyException;

/**
 * 实体实例化异常
 * @author huangkaihui
 *
 */
public class EntityInstantiationException extends MassyException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6307569946422457010L;
	
	/**
	 * 
	 */
	public EntityInstantiationException() {

	}

	/**
	 * @param message
	 */
	public EntityInstantiationException(String message) {
		super(message);

	}

	/**
	 * @param cause
	 */
	public EntityInstantiationException(Throwable cause) {
		super(cause);

	}

	/**
	 * @param message
	 * @param cause
	 */
	public EntityInstantiationException(String message, Throwable cause) {
		super(message, cause);

	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public EntityInstantiationException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

}
