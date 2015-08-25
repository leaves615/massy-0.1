/**
 * 
 */
package org.smarabbit.massy;

import org.smarabbit.massy.adapt.AdaptFactoryRegistry;
import org.smarabbit.massy.adapt.AdaptNotSupportException;
import org.smarabbit.massy.launch.MassyLaunchException;
import org.smarabbit.massy.service.ServiceRegistry;
import org.smarabbit.massy.util.Asserts;

/**
 * Massy工具，提供服务查询、适配和属性获取的方法
 * @author huangkaihui
 *
 */
public abstract class MassyUtils {

	protected static MassyContext INSTANCE;
	private static AdaptFactoryRegistry adaptRegistry;
	private static ServiceRegistry serviceRegistry;
	
	/**
	 * 获取{@link MassyContext}
	 * @return
	 * 		{@link MassyContext}
	 */
	public static MassyContext getDefaultContext(){
		checkStart();
		return INSTANCE;
	}
	
	/**
	 * 设置{@link MassyContext}
	 * @param context {@link MassyContext}
	 */
	protected static void setDefaultContext(MassyContext context){
		INSTANCE = context;
		
		if (INSTANCE != null){
			adaptRegistry = INSTANCE.getService(AdaptFactoryRegistry.class);
			serviceRegistry = INSTANCE.getService(ServiceRegistry.class);
		}else{
			adaptRegistry = null;
			serviceRegistry = null;
		}
	}
	
	/**
	 * 适配
	 * @param target
	 * 		目标对象，不能为null.
	 * @param adapType
	 * 		适配类型，不能为null.
	 * @return
	 * 		适配对象，不支持适配则返回null.
	 */
	public static <A> A adapt(Object target, Class<A> adaptType){
		checkStart();
		
		return adaptRegistry.adapt(target, adaptType);
	}
	
	/**
	 * 获取服务
	 * @param serviceType {@link Class},服务类型
	 * @param alias 服务别名
	 * @return 返回服务实例
	 * @throws ServiceNotFoundException
	 *  	服务不存在则抛出异常
	 */
	public static <S> S getService(Class<S> serviceType, String alias) throws
		ServiceNotFoundException {
		return getService(serviceType, alias, false);
	}
	
	/**
	 * 获取服务
	 * @param serviceType 服务类型，非空
	 * @param alias 别名
	 * @return 返回服务实例
	 * @throws ServiceNotFoundException
	 *  	服务不存在则抛出异常
	 */
	public static <S> S getService(Class<S> serviceType, String alias, boolean allowNull) 
			throws ServiceNotFoundException{
		checkStart();
		
		Asserts.argumentNotNull(serviceType,"serviceType");
		if (allowNull){
			return alias == null ?
					serviceRegistry.findService(serviceType) :
						serviceRegistry.findService(serviceType, alias);
		}else{
			return alias == null ?
					INSTANCE.getService(serviceType) :
						INSTANCE.getService(serviceType, alias);
		}
	}
		
	/**
	 * 适配且不允许返回null.
	 * @param target
	 * 		目标对象，不能为null.
	 * @param adapType
	 * 		适配类型，不能为null.
	 * @return
	 * 		适配对象，不能返回null.
	 * @throws AdaptNotSupportException
	 * 		不支持适配时抛出异常
	 */
	public static <A> A adaptAndNotNull(Object target, Class<A> adaptType)
		throws AdaptNotSupportException{
		checkStart();
		
		A result = adaptRegistry.adapt(target, adaptType);
		if (result == null){
			throw new AdaptNotSupportException(target.getClass(), adaptType);
		}
		return result;
	}
	
	/**
	 * 获取属性
	 * @param name 属性名称	
	 * @return 
	 * 			Object
	 * @throws PropertyNotFoundException
	 * 			.返回值为null，则抛出异常
	 */
	public static Object getProperty(String name) throws PropertyNotFoundException{
		Object result = MassyUtils.getDefaultContext().getDescriptor().getProperty(name);
		if (result == null){
			throw new PropertyNotFoundException(name);
		}
		
		return result;

	}
	
	/**
	 * 获取Massy属性
	 * @param name 属性名称
	 * @param propType 属性类型
	 * @return 
	 * 			{@link A}
	 * @throws PropertyNotFoundException
	 * 			.返回值为null，则抛出异常
	 */
	public static <P> P getProperty(String name, Class<P> propType) throws PropertyNotFoundException{	
		P result = MassyUtils.getDefaultContext().getDescriptor().getProperty(name, propType);
		if (result == null){
			throw new PropertyNotFoundException(name);
		}
		
		return result;
	}
	
	/**
	 * 获取属性
	 * @param name 属性名
	 * @param defaultValue 缺省值
	 * @param propType 属性类型
	 * @return {@link P}，属性不存在则返回缺省值
	 */
	public static <P> P getProperty(String name, P defaultValue, Class<P> propType){
		P result = MassyUtils.getDefaultContext().getDescriptor().getProperty(name, defaultValue, propType);
		if (result == null){
			throw new PropertyNotFoundException(name);
		}
		
		return result;
	}
	
	/**
	 * 检查是否启动
	 */
	private static void checkStart(){
		if (INSTANCE == null){
			throw new MassyLaunchException("massy framework cannot launch, please do it.");
		}
	}
}
