/**
 * 
 */
package org.smarabbit.massy.service;

import java.util.Map;

import org.smarabbit.massy.Descriptor;
import org.smarabbit.massy.spec.Specification;

/**
 * 服务管理容器，提供者将服务按类型注册到仓储中，服务的消费者通过类型查找所需的服务。
 * 
 * <br>
 * 相同的类型可以有多个服务实例，消费者可通过服务别名或者规则范式查找所需服务。
 * 
 * @author huangkaihui
 *
 */
public interface ServiceRepository {

	/**
	 * 获取所有服务类型
	 * <br>
	 * 服务注册时，按服务类型进行归类管理，同服务类型允许注册多个服务实例。
	 * @return 
	 * 		{@link Class}数组，无服务类型返回empty数组
	 */
	Class<?>[] getServiceTypes();
	
	/**
	 * 按服务类型查找服务
	 * <br>
	 * 服务查找将被转发给对应服务类性的{@link ServiceRegistrationManager}进行处理。<br>
	 * 通过实现自定义的{@link ServiceRegistrationManager}可控制服务查询获取规则。
	 * @param serviceType
	 * 		服务类型，非空
	 * @return
	 * 		服务实例，服务不存在返回null.
	 */
	<S> S findService(Class<S> serviceType);
	
	/**
	 * 按{@link Descriptor}查找服务
	 * <br>
	 * Descriptor必须是通过通过ServiceRepository查询得到的实例。
	 * @param descriptor {@link Descriptor}，非空
	 * @param serviceType {@link Class},非空
	 * @return
	 * 			{@link Descriptor}对应的服务，可能返回null.
	 */
	<S> S findService(Class<S> serviceType, Descriptor descriptor);
	
	/**
	 * 按服务实例和别名查找服务
	 * <br>
	 * 服务查找将被转发给对应服务类性的{@link ServiceRegistrationManager}进行处理。<br>
	 * 通过实现自定义的{@link ServiceRegistrationManager}可控制服务查询获取规则。
	 * @param serviceType
	 * 		服务类型，非空
	 * @param alias
	 * 		服务别名，非空
	 * @return
	 * 		服务实例，服务不存在返回null.
	 */
	<S> S findService(Class<S> serviceType, String alias);
	
	/**
	 * 按服务类型和规则检查器查找服务
	 * <br>
	 * 服务查找将被转发给对应服务类性的{@link ServiceRegistrationManager}进行处理。<br>
	 * 通过实现自定义的{@link ServiceRegistrationManager}可控制服务查询获取规则。
	 * @param serviceType
	 * 		服务类型，非空
	 * @param spec
	 * 		规则检查器，非空
	 * @return
	 * 		满足规则的服务，服务不存在返回null.
	 */
	<S> S findService(Class<S> serviceType, Specification<Descriptor> spec);
	
	
	
	/**
	 * 按服务类型获取所有服务实例
	 * @param serviceType 
	 * 		服务类型，非空
	 * @return
	 * 		服务数组，服务不存在则返回empty数组
	 */
	<S> S[] getAllServices(Class<S> serviceType);
	
	/**
	 * 获取默认服务的描述
	 * @param serviceType 服务类型
	 * @return {@link Descriptor},服务不存在返回null.
	 */
	Descriptor findServiceDescriptor(Class<?> serviceType);
	
	/**
	 * 获取指定别名服务的描述
	 * @param serviceType 服务类型
	 * @param alias 服务别名
	 * @return {@link Descriptor},服务不存在返回null.
	 */
	Descriptor findServiceDescriptor(Class<?> serviceType, String alias);
	
	/**
	 * 获取指定别名服务的描述
	 * @param serviceType 服务类型
	 * @param spec 规则检查器
	 * @return {@link Descriptor},服务不存在返回null.
	 */
	Descriptor findServiceDescriptor(Class<?> serviceType, Specification<Descriptor> spec);
	
	/**
	 * 获取所有同服务类型服务的描述
	 * @param serviceType
	 * 		服务类型，非空
	 * @return 
	 * 		{@link Descriptor}数组，无注册服务则返回empty.
	 */
	Descriptor[] getServiceDescriptors(Class<?> serviceType);
	
	/**
	 * 注册单一服务类型的服务
	 * <br>
	 * 方法首先为注册对象生成对应的{@link ServiceRegistration},
	 * 然后根据服务类型创建或者查找对应的{@link ServiceRegistrationManager}，
	 * 将{@link ServiceRegistration}交付{@link ServiceRegistrationManager#add(org.smarabbit.massy.Registration)}进行管理.
	 * 
	 * @param serviceType 
	 * 		服务类型,服务使用者需指定服务类型才能获取服务。
	 * @param service
	 *      服务实例，服务实例必须是服务类型的实现者，或者是{@link ServiceFactory}实例。
	 * @param props
	 *      属性，提供对服务的说明，可根据说明区分服务。具体见{@link #findService(Class, Specification)}
	 * @return 
	 * 		{@link ServiceRegistration}实例，不能返回null.
	 * @throws InstantiationException
	 * 		创建{@link RegistrationManager}实例发生实例化异常
	 * @throws IllegalAccessException
	 * 		创建{@link RegistrationManager}实例发生存取异常
	 * @throws MassyException
	 * 		{@link MassyException}异常
	 */
	<S> ServiceRegistration register(Class<S> serviceType, S service, Map<String, Object> props)
			throws RegisterServiceException;
	
	/**
	 * 注册多服务类型的服务
	 * <br>
	 * 方法首先为注册对象生成对应的{@link ServiceRegistration},
	 * 然后循环按服务类型创建或者查找对应的{@link ServiceRegistrationManager}，
	 * 将{@link ServiceRegistration}交付{@link ServiceRegistrationManager#add(org.smarabbit.massy.Registration)}进行管理.
	 * <br>
	 * 如果add方法失败，则回滚。
	 * 
	 * @param serviceTypes
	 * 		服务类型数组,服务使用者需指定服务类型才能获取服务。
	 * @param service
	 * 		服务实例，服务实例必须是服务类型的实现者，或者是{@link ServiceFactory}实例。
	 * @param props
	 *      属性，提供对服务的说明，可根据说明区分服务。具体见{@link #findService(Class, Specification)}
	 * @return 
	 * 		{@link ServiceRegistration}实例，不能返回null.
	 * @throws InstantiationException
	 * 		创建{@link RegistrationManager}实例发生实例化异常
	 * @throws IllegalAccessException
	 * 		创建{@link RegistrationManager}实例发生存取异常
	 * @throws MassyException
	 * 		{@link MassyException}异常
	 */
	ServiceRegistration register(Class<?>[] serviceTypes, Object service, Map<String, Object> props)
			throws RegisterServiceException;
}
