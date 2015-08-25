/**
 * 
 */
package org.smarabbit.massy.adapt;

import java.util.Map;

import org.smarabbit.massy.Descriptor;
import org.smarabbit.massy.Registration;
import org.smarabbit.massy.annotation.AutoInitializeServiceType;
import org.smarabbit.massy.spec.Specification;

/**
 * {@link AdaptFactory}仓储，提供{@link AdaptFactory}的注册和查询。
 * <br>
 * 通过{@link #adapt(Object, Class)}方法能动态扩展对象的适配能力，
 * 为不支持{@link Adaptable}的类增加适配可能。
 * 
 * @author huangkh
 *
 */
@AutoInitializeServiceType
public interface AdaptFactoryRegistry{
	
	/**
	 * 适配
	 * <br>
	 * 首先检查target对象是否支持{@link Adaptable}，如果支持则先调用target的
	 * {@link Adaptable#adapt(Class)}获取适配结果。当不支持{@link Adaptable}或者
	 * 调用适配的结果为null时，才对注册adaptType的所有{@link AdaptFactory}实例，循环调用其
	 * {@link AdaptFactory#getAdaptor(Object, Class)}方法，当调用返回不为null时，则作为
	 * 方法的返回值。
	 * 
	 * @param target 
	 * 		适配的目标对象实例，非空
	 * @param adaptType 
	 * 		适配类型，非空，
	 * @return 返回适配对象，不支持适配可返回null.
	 */
	<A> A adapt(Object target, Class<A> adaptType);
	
	/**
	 * 适配
	 * <br>
	 * 方法首先检查target对象是否支持{@link Adaptable}，如果支持则先调用target的
	 * {@link Adaptable#adapt(Class)}获取适配结果。当不支持{@link Adaptable}或者
	 * 调用适配的结果为null时，才对满足Specification规则的{@link AdaptFactory}实例，循环调用其
	 * {@link AdaptFactory#getAdaptor(Object, Class)}方法，当调用返回不为null时，则作为
	 * 方法的返回值。
	 * @param target
	 * 		适配的目标对象实例，非空
	 * @param adaptType
	 * 		适配类型，非空，
	 * @param spec
	 * 		{@link Specification}规则检查器，非空
	 * @return
	 *      返回适配类型，不支持适配可返回null.
	 */
	<A> A adapt(Object target, Class<A> adaptType, Specification<Descriptor> spec);

	/**
	 * 获取所有注册的适配类型
	 * @return
	 * 		{@link Class}数组，无注册适配类型则返回empty数组
	 */
	Class<?>[] getAdaptTypes();
	
	/**
	 * 按适配类型获取所有{@link AdaptFactory}的描述说明
	 * @param adaptType 
	 * 		适配类型
	 * @return
	 * 		{@link Descriptor}数组，无对应的{@link AdaptFactory}返回empty数组
	 */
	Descriptor[] getAdaptFactoryDescriptors(Class<?> adaptType);
	
	/**
	 * 获取对象所支持的适配类型 
	 * @param type 类型
	 * @return
	 * 		{@link Class<?>}数组，适配类型
	 */
	Class<?>[] getAdaptTypesFromObject(Object object);
		
	/**
	 * 注册适配类型和适配工厂，允许多个适配工厂注册同样的适配类型。
	 * @param adaptType 
	 * 			{@link Class},非空
	 * @param factory 
	 * 			{@link AdaptFactory},非空
	 * @param props
	 * 			{@link Map},AdaptFactory的附加属性
	 * @return
	 * 		{@link Registration},非空
	 * @throws RegisterAdaptFactoryException
	 * 		注册适配工厂失败
	 */
	<A> AdaptFactoryRegistration<A> register(Class<A> adaptType, AdaptFactory<A> factory, Map<String, Object> props)
		throws RegisterAdaptFactoryException;
}
