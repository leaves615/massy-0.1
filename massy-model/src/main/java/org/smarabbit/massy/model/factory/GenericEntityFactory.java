/**
 * 
 */
package org.smarabbit.massy.model.factory;

import org.smarabbit.massy.model.provider.DataProvider;
import org.smarabbit.massy.util.Asserts;

/**
 * @author huangkaihui
 *
 */
public class GenericEntityFactory extends AbstractEntityFactory{

	/**
	 * 
	 */
	public GenericEntityFactory() {
		
	}

	public <T> T create(Class<T> entityType, DataProvider provider) throws EntityInstantiationException{
		Asserts.argumentNotNull(entityType, "entityType");
		Asserts.argumentNotNull(provider, "provider");
		
		try{
			return  this.doCreate(entityType, provider);
		}catch(EntityInstantiationException e){
			throw e;
		}catch(Exception e){
			throw new EntityInstantiationException(e.getMessage(), e);
		}
	}
}
