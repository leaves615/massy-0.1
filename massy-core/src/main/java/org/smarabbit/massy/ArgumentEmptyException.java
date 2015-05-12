/**
 * 
 */
package org.smarabbit.massy;


/**
 * @author huangkaihui
 *
 */
public class ArgumentEmptyException extends MassyException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3661071408005151715L;

	private final String argumentName;
	/**
	 * 
	 */
	public ArgumentEmptyException(String argumentName) {
		this.argumentName = argumentName;
	}

	/**
	 * 参数名
	 * @return
	 * 		字符串
	 */
	public String getArgumentName(){
		return this.argumentName;
	}

	@Override
	public String getMessage() {
		return "argument [" + this.argumentName + "] cannot be Empty.";
	}
}
