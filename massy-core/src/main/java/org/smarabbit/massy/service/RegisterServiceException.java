/**
 * 
 */
package org.smarabbit.massy.service;

import org.smarabbit.massy.MassyException;

/**
 * 注册服务时发生的异常
 * @author huangkaihui
 *
 */
public class RegisterServiceException extends MassyException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9136490731645075965L;
	
	/**
	 * 
	 */
	public RegisterServiceException() {
	}

	/**
	 * @param message
	 */
	public RegisterServiceException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public RegisterServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public RegisterServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public RegisterServiceException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	

	@Override
	public String getMessage() {
		return "register service failed.";
	}

	
}
