/**
 * 
 */
package org.smarabbit.massy.adapt;

/**
 * @author huangkaihui
 *
 */
public interface AdaptFactoryRegistrationManagerFactory<A> {
	
	/**
	 * 指明是否在MassyContext初始化时自动构建对应的{@link AdaptFactoryRegistrationManager}实例
	 * @return 
	 * 			true表示是，否则返回false
	 */
	boolean autoInit();
	
	/**
	 * 获取支持的适配类型
	 * @return
	 */
	Class<A> getSupportType();
	
	/**
	 * 创建{@link AdaptFactoryRegistrationManager}
	 * @return
	 * 		{@link AdaptFactoryRegistrationManager},不能返回null.
	 */
	AdaptFactoryRegistrationManager<A> create();
}
