/**
 * 
 */
package org.smarabbit.massy.adapt;

/**
 * {@link AdaptFactoryRegistrationFactory}链
 * @author huangkaihui
 *
 */
public interface AdaptFactoryRegistrationManagerFactoryChain {

	/**
	 * 继续创建adaptType对应的{@link AdaptFactoryRegistrationManager}
	 * @param adaptType 适配类型 
	 * @return
	 * 		{@link AdaptFactoryRegistrationManager},可以返回null.
	 */
	<A> AdaptFactoryRegistrationManager<A> proceed(Class<A> adaptType);
}
