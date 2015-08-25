/**
 * 
 */
package org.smarabbit.massy.annotation.support;

import java.lang.annotation.ElementType;
import java.util.Collection;

import org.smarabbit.massy.spec.Specification;
import org.smarabbit.massy.spec.SpecificationUtils;
import org.smarabbit.massy.spec.TypeEqualSpecification;
import org.smarabbit.massy.util.Asserts;
import org.smarabbit.massy.util.CollectionUtils;

/**
 * {@link Definition}工具类，用于合并类的{@link Definition}
 * @author huangkaihui
 *
 */
public abstract class DefinitionUtils {

	/**
	 * 将{@link Definition}数组，合并到Set中
	 * <br>
	 * 如果Set中已包括有注解类的Definition,且该Definition支持继承，则跳过同类型的Definition合并
	 * @param definitions
	 * @param set
	 */
	public static void merge(Definition[] definitions, Collection<Definition> set){
		Asserts.argumentNotNull(set, "set");
		if (definitions.length == 0) return;
		
		for (Definition definition: definitions){
			
			//类注解，按是否继承判断是否执行添加
			if (definition.getElementType() == ElementType.TYPE){
				if (!definition.isInherited()){
					set.add(definition);
				}else{
					Specification<Definition> spec = new TypeEqualSpecification<Definition>(definition.getClass());
					if (SpecificationUtils.findFirstBySpec(set, spec) == null){
						set.add(definition);
					}
				}
			}else{
				//方法类或者字段类,直接添加
				set.add(definition);
			}

		}
	}
	
	/**
	 * 将source合并到target中
	 * <br>
	 * 如果Set中已包括有注解类的Definition,且该Definition支持继承，则跳过同类型的Definition合并
	 * @param source
	 * @param target
	 */
	public static void merge(Collection<Definition> source, Collection<Definition> target){
		Asserts.argumentNotNull(target, "target");
		if (CollectionUtils.isEmpty(source)) return;
		
		for (Definition definition: source){
			
			//类注解，按是否继承判断是否执行添加
			if (definition.getElementType() == ElementType.TYPE){
				if (!definition.isInherited()){
					target.add(definition);
				}else{
					Specification<Definition> spec = new TypeEqualSpecification<Definition>(definition.getClass());
					if (SpecificationUtils.findFirstBySpec(target, spec) == null){
						target.add(definition);
					}
				}
			}else{
				//方法类或者字段类,直接添加
				target.add(definition);
			}
		}

	}
		
	/**
	 * 将Definition合并到Set中
	 * <br>
	 * 如果Set中已包括有注解类的Definition,且该Definition支持继承，则跳过同类型的Definition合并
	 * @param definition
	 * @param set
	 */
	public static void merge(Definition definition, Collection<Definition> set){
		Asserts.argumentNotNull(set, "set");
		
		if (!definition.isInherited()){
			set.add(definition);
		}else{
			Specification<Definition> spec = new TypeEqualSpecification<Definition>(definition.getClass());
			if (SpecificationUtils.findFirstBySpec(set, spec) == null){
				set.add(definition);
			}
		}					
	}
}
