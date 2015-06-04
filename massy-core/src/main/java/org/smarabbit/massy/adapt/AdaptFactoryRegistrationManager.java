/**
 * 
 */
package org.smarabbit.massy.adapt;

import org.smarabbit.massy.Descriptor;
import org.smarabbit.massy.spec.Specification;
import org.smarabbit.massy.RegistrationManager;

/**
 * 管理同适配类型的{@link AdaptFactoryRegistration}.
 * 
 * <br>
 * 注册{@link AdaptFactory}时，{@link AdaptFactoryRegistry}会根据{@link AdaptFactory}
 * 上的{@link ManagerType}注解，加载对应的AdaptFactoryRegistrationManager实现来管理
 * {@link AdaptFactoryRegistration}。<br>
 * 缺省情况下，系统使用{@link GenericAdaptFactoryRegistrationManager}管理{@link AdaptFactoryRegistration}。<br>
 * 如果{@link GenericAdaptFactoryRegistrationManager}不满足需要，可以自定义AdaptFactoryRegistrationManager的实现，并在适配类型上增加{@link ManagerType}的注解。
 * 
 * @author huangkh
 *
 */
public interface AdaptFactoryRegistrationManager<A> extends 
	RegistrationManager<AdaptFactoryRegistration<A>>{

	/**
	 * 绑定适配类型
	 * @param adaptType
	 */
	void bind(Class<A> adaptType);
	
	/**
	 * 获取绑定的适配类型
	 * @return
	 */
	Class<A> getAdaptType();
	
	/**
	 * 获得适配对象
	 * @param target 
	 * 		目标对象，非空
	 * @return
	 * 		适配对象，不支持返回null.
	 */
	A getAdaptor(Object target);
	
	/**
	 * 获得适配对象
	 * @param target 
	 * 		目标对象，非空
	 * @param spec
	 * 		{@link Specification},非空
	 * @return
	 * 		适配对象，不支持返回null.
	 */
	A getAdaptor(Object target, Specification<Descriptor> spec);
}
