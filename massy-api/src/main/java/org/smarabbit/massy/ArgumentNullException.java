/**
 * 
 */
package org.smarabbit.massy;


/**
 * 参数为Null异常
 * @author huangkaihui
 *
 */
public class ArgumentNullException extends MassyException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 916339658289539841L;
	private final String argumentName;

	/**
	 * 
	 */
	public ArgumentNullException(String argumentName) {
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
		return "argument [" + this.argumentName + "] cannot be NULL.";
	}
	
	
}
