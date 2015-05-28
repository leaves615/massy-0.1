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
public class TypeNamedEntityFactory extends AbstractEntityFactory implements
		EntityFactory {

	/**
	 * 
	 */
	public TypeNamedEntityFactory() {
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.model.factory.EntityFactory#create(org.smarabbit.massy.Descriptor)
	 */
	@Override
	public Object create(DataProvider mapValue) throws EntityInstantiationException {
		Asserts.argumentNotNull(mapValue, "mapValue");
		
		try{
			Class<?> entityType = this.getEntityType(mapValue);
			return  this.doCreate(entityType, mapValue);
		}catch(EntityInstantiationException e){
			throw e;
		}catch(Exception e){
			throw new EntityInstantiationException(e.getMessage(), e);
		}
	}

	protected Class<?> getEntityType(DataProvider mapValue) throws ClassNotFoundException{
		String typeName = (String)mapValue.getValue(DEFAULT_TYPENAME);
		return Class.forName(typeName);
	}
}
