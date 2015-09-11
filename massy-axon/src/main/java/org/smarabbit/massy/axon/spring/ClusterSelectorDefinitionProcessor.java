/**
 * 
 */
package org.smarabbit.massy.axon.spring;

import java.util.HashMap;
import java.util.Map;

import org.axonframework.eventhandling.ClusterSelector;
import org.smarabbit.massy.annotation.support.Definition;
import org.smarabbit.massy.annotation.support.ExportServiceDefinition;
import org.smarabbit.massy.spring.config.DefinitionProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * @author huangkaihui
 *
 */
public class ClusterSelectorDefinitionProcessor implements DefinitionProcessor {

	/**
	 * 
	 */
	public ClusterSelectorDefinitionProcessor() {
		
	}

	@Override
	public Definition discovry(String beanName, Class<?> beanType, ConfigurableListableBeanFactory beanFactory) {
		if (beanFactory.containsBeanDefinition(beanName)){
			
			if (ClusterSelector.class.isAssignableFrom(beanType)){
				if (beanType != ClusterServiceSelector.class){
					Map<String, Object> props = new HashMap<String, Object>();
					props.put("beanName", beanName);
					ExportServiceDefinition result = new ExportServiceDefinition(new Class<?>[]{ClusterSelector.class}, props);				
					return result;
				}
			}
		}
		return null;
	}

}
