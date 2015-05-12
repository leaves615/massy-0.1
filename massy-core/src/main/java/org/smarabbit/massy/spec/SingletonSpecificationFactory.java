/**
 * 
 */
package org.smarabbit.massy.spec;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单例规则检查器工厂
 * @author huangkh
 *
 */
public abstract class SingletonSpecificationFactory {

	private static Map<Class<?>, Specification<?>> specMap =
			new ConcurrentHashMap<Class<?>, Specification<?>>();
	
	/**
	 * 获取缺省的{@link S}实例
	 * @param specType
	 * @return 返回{@link S}.
	 */
	@SuppressWarnings("unchecked")
	public static <S extends Specification<?>> S getDefault(Class<S> specType){
		if (specMap.containsKey(specType)){
			return (S)specMap.get(specType);
		}
		
		return newInstance(specType);
	}
	
	protected static <S> S newInstance(Class<S> specType){
		try{
			S result = specType.newInstance();
			specMap.put(specType, (Specification<?>)result);
			return result;
		}catch(Exception e){
			return null;
		}
	}
}
