/**
 * 
 */
package org.smarabbit.massy.adapt;

/**
 * {@link AdaptFactory}注册凭据管理工厂
 * <br>
 * 在实现上可注解{@link org.smarabbit.massy.annotation.Order},进行排序
 * @author huangkaihui
 *
 */
public interface AdaptFactoryRegistrationManagerFactory{
		
	/**
	 * 创建{@link AdaptFactoryRegistrationManager}
	 * <br>
	 * 如果是所管理的适配类型，则直接返回创建适配注册管理器实例，否则，使用
	 * {@link AdaptFactoryRegistrationManagerFactoryChain#process(Class)}继续处理。
	 * @param adaptType 适配类型
	 * @param chain 适配注册工厂链
	 * @return
	 * 		{@link AdaptFactoryRegistrationManager},不能返回null.
	 */
	AdaptFactoryRegistrationManager<?> create(Class<?> adaptType, 
			AdaptFactoryRegistrationManagerFactoryChain chain);
}
