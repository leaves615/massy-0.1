/**
 * 
 */
package org.smarabbit.massy;

/**
 * 异常基础类
 * @author huangkaihui
 *
 */
public abstract class MassyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6886090107285060103L;

	/**
	 * 
	 */
	public MassyException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public MassyException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public MassyException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MassyException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public MassyException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
