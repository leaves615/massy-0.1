/**
 * 
 */
package org.smarabbit.massy;

import org.smarabbit.massy.adapt.AdaptFactoryRepository;
import org.smarabbit.massy.adapt.AdaptNotSupportException;
import org.smarabbit.massy.launch.DefaultMassyLauncher;
import org.smarabbit.massy.launch.MassyLaunchException;
import org.smarabbit.massy.launch.MassyLauncher;
import org.smarabbit.massy.util.ServiceLoaderUtils;

/**
 * @author huangkaihui
 *
 */
public abstract class MassyUtils {

	private static MassyContext INSTANCE;
	private static AdaptFactoryRepository adaptRepo;
	
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
	public static void setDefaultContext(MassyContext context){
		if (INSTANCE == null){
			INSTANCE = context;
			
			if (INSTANCE != null){
				adaptRepo = INSTANCE.getService(AdaptFactoryRepository.class);
			}
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
		
		return adaptRepo.adapt(target, adaptType);
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
		
		A result = adaptRepo.adapt(target, adaptType);
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
	 * 创建{@link MassyLauncher}
	 * @return
	 * 		{@link MassyLauncher},不能返回null.
	 */
	public static MassyLauncher create(){
		MassyLauncher result = ServiceLoaderUtils.loadFirstService(MassyLauncher.class);
		if (result == null){
			result = new DefaultMassyLauncher();
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
