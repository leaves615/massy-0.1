/**
 * 
 */
package org.smarabbit.massy.adapt;

import org.smarabbit.massy.MassyException;

/**
 * 注册适配工厂异常
 * @author huangkaihui
 *
 */
public class RegisterAdaptFactoryException extends MassyException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7935373154251008037L;

	/**
	 * 
	 */
	public RegisterAdaptFactoryException() {
	}

	/**
	 * @param message
	 */
	public RegisterAdaptFactoryException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public RegisterAdaptFactoryException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public RegisterAdaptFactoryException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public RegisterAdaptFactoryException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
