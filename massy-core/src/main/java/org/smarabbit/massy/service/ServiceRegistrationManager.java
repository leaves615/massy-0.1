/**
 * 
 */
package org.smarabbit.massy.service;

import org.smarabbit.massy.Descriptor;
import org.smarabbit.massy.RegistrationManager;
import org.smarabbit.massy.spec.Specification;

/**
 * 管理同服务类型的{@link ServiceRegistration}，并提供服务查询和获取的方法.
 * 
 * <br>
 * 注册服务时，{@link ServiceRegistry}会根据服务类型
 * 上的{@link ManagerType}注解，加载对应的ServiceRegistrationManager实现来管理
 * {@link ServiceRegistration}。<br>
 * 缺省情况下，系统使用{@link GenericServiceFactoryRegistrationManager}管理{@link ServiceRegistration}。<br>
 * 如果{@link GenericServiceRegistrationManager}不满足需要，可以自定义ServiceRegistrationManager的实现，并在服务类型上增加{@link ManagerType}的注解。
 * 
 * @author huangkh
 *
 */
public interface ServiceRegistrationManager<S> extends RegistrationManager<ServiceRegistration> {

	/**
	 * 绑定管理的服务类型
	 * @param serviceType
	 * 			服务类型				
	 */
	void bind(Class<S> serviceType);
	
	/**
	 * 获取缺省服务,
	 * <br>
	 *  返回首个别名为null的服务，如无别名为null的服务，则返回首个服务。
	 * @return
	 * 		服务实例，服务不存在返回null.
	 */
	S getDefaultService();
	
	/**
	 * 按{@link Descriptor}获取服务实例
	 * @param descriptor {@link Descriptor},非空
	 * @return
	 * 			{@link S}.
	 */
	S findService(Descriptor descriptor);
	
	/**
	 * 获取所有服务
	 * 
	 * @return
	 * 		服务实例数组，服务不存在返回empty数组
	 */
	S[] getServices();
	
	/**
	 * 管理的服务类型
	 * @return
	 */
	Class<?> getServiceType();
	
	/**
	 * 查找服务,返回首个别名符合要求的服务，
	 * @param alias
	 * 		服务别名，非空
	 * @return
	 * 		服务实例，服务不存在返回null.
	 */
	S findService(String alias);
	
	/**
	 * 查找服务,返回首个符合规则要求的服务，
	 * @param spec
	 * 		规则检查器，非空
	 * @return
	 * 		满足规则的服务，服务不存在返回null.
	 */
	S findService(Specification<Descriptor> spec);
	
	/**
	 * 获取缺省服务的描述
	 * @return
	 * 			{@link Descriptor}，服务不存在返回null.
	 */
	Descriptor getDefaultServiceDescriptor();
	
	/**
	 * 查找指定别名服务的描述
	 * @param alias 服务别名
	 * @return {@link Descriptor}，服务不存在返回null.
	 */
	Descriptor findServiceDescriptor(String alias);
	
	/**
	 * 查找服务，并返回首个符合规则要求服务的描述
	 * @param spec 
	 * 		规则检查器，非空
	 * @return
	 * 		满足规则的服务，服务不存在返回null.
	 */
	Descriptor findServiceDescriptor(Specification<Descriptor> spec);
}
