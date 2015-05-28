/**
 * 
 */
package org.smarabbit.massy.lazyload;

import org.smarabbit.massy.MassyUtils;
import org.smarabbit.massy.annotation.support.LazyBindDefinition;
import org.smarabbit.massy.service.DefinitionEqualSpecification;
import org.smarabbit.massy.service.ServiceRepository;

/**
 * @author huangkaihui
 *
 */
public abstract class LazyLoadUtils {

	/**
	 * 获取{@link LazyBinder}实例
	 * @param declaringType 延迟加载类型
	 * @param fieldName 延迟加载字段名
	 * @return	
	 * 		字段值
	 */
	@SuppressWarnings("unchecked")
	public static LazyBinder<Object> getLazyBinder(Class<?> declaringType, String fieldName){
		/*if (!declaringType.isAssignableFrom(target.getClass())){
			throw new LazyLoadException(target + " is not assign to " + declaringType.getName() + ".");
		}*/
		
		LazyBindDefinition definition = new LazyBindDefinition(declaringType.getName(), fieldName);
		ServiceRepository repo = MassyUtils.getDefaultContext().getService(ServiceRepository.class);
		DefinitionEqualSpecification spec = new DefinitionEqualSpecification(definition);
		
		LazyBinder<Object> result = repo.findService(LazyBinder.class, spec);
		if (result == null){
			throw new LazyLoadException("cannot found match LazyBinder: " + definition);
		}
		
		return result;
	}
}
