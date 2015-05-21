/**
 * 
 */
package org.smarabbit.massy.lazyload;

import org.smarabbit.massy.MassyException;

/**
 * @author huangkaihui
 *
 */
public class LazyLoadException extends MassyException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6880888402214533069L;

	
	/**
	 * @param message
	 */
	public LazyLoadException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public LazyLoadException(String message, Throwable cause) {
		super(message, cause);
	}

}
