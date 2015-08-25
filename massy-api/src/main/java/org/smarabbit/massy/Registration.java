/**
 * 
 */
package org.smarabbit.massy;

/**
 * 注册凭据，持有注册凭据可撤销注册项。
 * @author huangkaihui
 *
 */
public interface Registration  extends Descriptable{

	/**
	 * 编号，由系统顺机生成
	 * @return
	 */
	long getId();
	
	/**
	 * 撤销注册
	 */
	void unregister();
}
