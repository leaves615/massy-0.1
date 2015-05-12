/**
 * 
 */
package org.smarabbit.massy;

/**
 * 提供对注册凭据的添加/移除的管理
 * 
 * @author huangkaihui
 *
 */
public interface RegistrationManager<R extends Registration> {
	
	/**
	 * 添加{@link Registration}
	 * @param registration
	 * 		{@link Registration},非空.
	 * @return
	 * 		true表示添加成功，否则返回false.
	 */
	void add(R registration);
	
	/**
	 * 移除{@link Registration}
	 * @param registration
	 * @return
	 * 		true表示移除成功，否则返回false.
	 */
	void remove(R registration);
	
	/**
	 * 获取所有{@link Registration}的描述
	 * @return
	 * 		{@link Descriptor}
	 */
	Descriptor[] getDescriptors();
	
}
