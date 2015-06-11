/**
 * 
 */
package org.smarabbit.massy.model.persistent;

import org.smarabbit.massy.util.Asserts;
import org.springframework.context.ApplicationContext;

/**
 * @author huangkaihui
 *
 */
public final class PersistenceSchemaBuilder {

	private ApplicationContext context;

	public PersistenceSchemaBuilder(ApplicationContext context) {
		Asserts.argumentNotNull(context, "context");
		this.context = context;
	}
	
	/**
	 * 构建数据模式
	 * @throws Exception
	 */
	public void build() throws Exception{		
		String[] beanNames = context.getBeanDefinitionNames();
		
		int size = beanNames.length;
		for (int i=0; i<size; i++){
			String beanName = beanNames[i];
			Class<?> beanType = context.getType(beanName);
			if (PersistenceSchemaHandler.class.isAssignableFrom(beanType)){
				PersistenceSchemaHandler handler = 
						context.getBean(beanName, PersistenceSchemaHandler.class);
			
				handler.buildSchema();
				handler.preprocess();
			}
		}		
		
		context = null;
	}

}
