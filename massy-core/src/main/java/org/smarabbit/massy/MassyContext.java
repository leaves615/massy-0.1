/**
 * 
 */
package org.smarabbit.massy;

/**
 * 模块化执行环境的上下文，为模块化应用提供服务获取能力。<br>
 * {@link org.smarabbit.massy.service.ServiceRegistry}提供服务注册和查找，
 * 上下文简化服务获取的方法，并在服务不存在时抛出异常。<br>
 * 服务注册后，其他模块就可以从上下文处得到服务的引用，搭建起模块之间的联系。
 * 
 * <br>
 * MassyContext提供以下方法：
 * <ul>
 * <li>按服务类型或别名获取服务实例</li>
 * <li>上下文属性的存取</li>
 * </ul>
 * <br>
 * MassyContext通过{@link MassyLauncher}进行加载。
 * 
 * @author huangkh
 *
 */
public interface MassyContext extends Descriptable{
	
	/**
	 * 按服务类型获取所有服务。
	 * <br>
	 * 服务注册时，按服务类型进行归类管理，同服务类型允许注册多个服务实例。
	 * @param serviceType 
	 * 		服务类型，非空
	 * @return
	 * 		服务数组，服务不存在则返回empty数组
	 */
	<S> S[] getAllServices(Class<S> serviceType);
		
	/**
	 * 按服务类型获取已注册的服务，服务不存在则会抛出异常。
	 * <br>
	 * 服务注册时，按服务类型进行归类管理，同服务类型允许注册多个服务实例。使用本方法
	 * 查询服务时，先返回首个别名为null的服务，如无别名为null的服务，则返回首个服务。
	 * <br>
	 * 如果期望方法不抛出异常，请使用{@link org.smarabbit.massy.service.ServiceRegistry#findService(Class)}方法。
	 * @param serviceType 
	 * 		服务类型，非空
	 * @return 
	 * 		找到的服务实例，不能返回null.
	 * @throws ServiceNotFoundException 
	 * 		服务不存在则抛出异常。
	 */
	<S> S getService(Class<S> serviceType) throws ServiceNotFoundException;
	
	/**
	 * 按服务类型和别名获取已注册的服务，服务不存在则会抛出异常。
	 * <br>
	 * 服务别名，是区分其他同类型服务的方式之一，除此之外，还可以通过
	 * {@link org.smarabbit.massy.service.ServiceRegistry#findService(Class, org.smarabbit.massy.spec.Specification)}
	 * 方法查找服务。
	 * <br>
	 * 如果期望方法不抛出异常，请使用{@link org.smarabbit.massy.service.ServiceRegistry#findService(Class, String)}方法。
	 * @param serviceType
	 * 		服务类型，非空
	 * @param alias
	 * 		服务别名，非空
	 * 		注册服务时，可设置{@link Constants#SERVICE_ALIAS}属性，作为区别其他服务的关键字。
	 * @return
	 * 		返回找到的服务实例，不能返回null.
	 * @throws ServiceNotFoundException
	 * 		服务不存在则抛出异常。
	 */
	<S> S getService(Class<S> serviceType, String alias) throws ServiceNotFoundException;
}
