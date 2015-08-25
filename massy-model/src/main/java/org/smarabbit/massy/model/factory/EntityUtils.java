/**
 * 
 */
package org.smarabbit.massy.model.factory;

import org.smarabbit.massy.model.provider.DataProvider;


/**
 * @author huangkaihui
 *
 */
public abstract class EntityUtils {
	
	private static final GenericEntityFactory genericFactory =
			new GenericEntityFactory();
	private static final TypeNamedEntityFactory  namedFactory =
			new TypeNamedEntityFactory();

	/**
	 * 按typeName创建对应的实体实例
	 * @param DataProvider {@link DataProvider},非空
	 * @param resultType 返回类型
	 * @return
	 * 		{@link T}
	 * @throws EntityInstantiationException
	 */
	public static <T> T createByDynamic(DataProvider provider, Class<T> resultType) throws EntityInstantiationException{
		Object result = namedFactory.create(provider);
		return resultType.cast(result);
	}
	
	/**
	 * 按返回类型创建实体实例
	 * @param mapValue
	 * @param resultType
	 * @return
	 * @throws EntityInstantiationException
	 */
	public static <T> T create(DataProvider provider, Class<T> resultType) throws EntityInstantiationException{
		Object result = genericFactory.create(resultType, provider);
		return resultType.cast(result);
	}
}
