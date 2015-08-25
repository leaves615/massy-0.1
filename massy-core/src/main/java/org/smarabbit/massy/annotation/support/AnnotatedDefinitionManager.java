/**
 * 
 */
package org.smarabbit.massy.annotation.support;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.smarabbit.massy.spec.Specification;
import org.smarabbit.massy.spec.SpecificationUtils;
import org.smarabbit.massy.spec.TypeEqualSpecification;
import org.smarabbit.massy.util.Asserts;

/**
 * 注解定义管理器，在类增强时添加对应注解定义
 * @author huangkaihui
 *
 */
public class AnnotatedDefinitionManager  implements DefinitionManager{

	private Map<String, Set<Definition>> definitionMap =
			new HashMap<String, Set<Definition>>();
	
	/**
	 * 
	 */
	public AnnotatedDefinitionManager() {

	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.annotation.support.DefinitionManager#getDefinition(java.lang.Class)
	 */
	@Override
	public Definition[] getDefinitions(Class<?> clazz) {
		Asserts.argumentNotNull(clazz, "clazz");
		
		Set<Definition> set = new HashSet<Definition>();
		this.scanDefinition(clazz, set);
		return set.toArray(new Definition[set.size()]);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.annotation.support.DefinitionManager#getDefinition(java.lang.Class, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <D extends Definition> D getDefinition(Class<?> clazz,
			Class<D> definitionType) {
		Asserts.argumentNotNull(clazz, "clazz");
		Asserts.argumentNotNull(definitionType, "definitionType");
		
		Set<Definition> set = this.getClassDefinitions(clazz.getName());
		if (set != null){
			Specification<Definition> spec = new TypeEqualSpecification<Definition>(definitionType);
			return (D) SpecificationUtils.findFirstBySpec(set, spec);
		}
		return null;
	}
	
	/**
	 * 未找到满足{@link Specification}规则的定义，则添加定义
	 * @param declaringTypeName 类型名称
	 * @param spec  {@link Specification}规则检查器
	 */
	public void addDefinition(String declaringTypeName, Definition definition){
		Asserts.argumentNotNull(declaringTypeName, "declaringTypeName");
		Asserts.argumentNotNull(definition, "definition");
		
		synchronized (this.definitionMap){
			Set<Definition> set = this.definitionMap.get(declaringTypeName);
			if (set == null){
				set = new HashSet<Definition>();
				this.definitionMap.put(declaringTypeName, set);
			}
			
			if (!set.contains(definition)){
				set.add(definition);
			}
		}
		
		
	}
	
	protected void scanDefinition(Class<?> clazz, Set<Definition> set){
		//非接口类型基类是Object
		if (clazz == Object.class){
			return;
		}
		
		//接口类型基类是null.
		if (clazz == null){
			return;
		}
		
		Set<Definition> defSet = this.getClassDefinitions(clazz.getName());
		if (defSet != null){
			DefinitionUtils.merge(defSet, set);
		}	
		
		Class<?> superior = clazz.getSuperclass();
		this.scanDefinition(superior, set);
	}
	
	protected Set<Definition> getClassDefinitions(String declaringTypeName){
		return this.definitionMap.get(declaringTypeName);
	}
	
}
