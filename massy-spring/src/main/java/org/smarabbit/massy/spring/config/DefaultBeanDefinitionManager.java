/**
 * 
 */
package org.smarabbit.massy.spring.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.smarabbit.massy.annotation.support.AnnotatedDefinitionManagerFactory;
import org.smarabbit.massy.annotation.support.Definition;
import org.smarabbit.massy.annotation.support.DefinitionUtils;
import org.smarabbit.massy.util.Asserts;

/**
 * 实现{@link BeanDefintionManager}
 * @author huangkaihui
 *
 */
public class DefaultBeanDefinitionManager implements BeanDefinitionManager {

	private Map<String, Set<Definition>> definitinMap =
			new HashMap<String, Set<Definition>>();
	
	/**
	 * 
	 */
	public DefaultBeanDefinitionManager() {

	}
	
	public void addDefinition(String beanName, Class<?> beanType,  Definition definition){
		Asserts.argumentNotNull(beanName,"beanName");
		Asserts.argumentNotNull(beanType,"beanType");
		Asserts.argumentNotNull(definition,"definition");
		
		Set<Definition> set = this.definitinMap.get(beanName);
		if (set == null){
			set = new HashSet<Definition>();
			this.definitinMap.put(beanName, set);
		}
		
		set.add(definition);
		
		//取得注解方式的定义
		Definition[] annoDefinitions = AnnotatedDefinitionManagerFactory.getDefault().getDefinitions(beanType);
		DefinitionUtils.merge(annoDefinitions, set);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.spring.config.BeanDefinitionManager#getDefinitions(java.lang.String, java.lang.Class)
	 */
	@Override
	public Definition[] getDefinitions(String beanName, Class<?> beanType) {
		Asserts.argumentNotNull(beanName,"beanName");
		Asserts.argumentNotNull(beanType,"beanType");
		
		if (this.definitinMap.containsKey(beanName)){
			Set<Definition> set = this.definitinMap.get(beanName);
			return set.toArray(new Definition[set.size()]);
		}else{
			return AnnotatedDefinitionManagerFactory.getDefault().getDefinitions(beanType);
		}
	}
	
	
}
