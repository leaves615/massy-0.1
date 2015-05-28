/**
 * 
 */
package org.smarabbit.massy.web.async;

/**
 * {@link DefereedResult}处理器
 * @author huangkaihui
 *
 */
public interface AsyncResultHolder<T> {

	/**
	 * 成功
	 * @param result
	 */
	void success(T result);
	
	/**
	 * 失败
	 * @param message
	 * @param cause
	 */
	void failed(String message, Throwable cause);
}
