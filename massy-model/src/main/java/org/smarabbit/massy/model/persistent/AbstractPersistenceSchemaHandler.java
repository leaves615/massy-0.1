/**
 * 
 */
package org.smarabbit.massy.model.persistent;

import org.smarabbit.massy.MassyContext;
import org.smarabbit.massy.MassyUtils;
import org.smarabbit.massy.PropertyNotFoundException;
import org.smarabbit.massy.util.Asserts;

/**
 * @author huangkaihui
 *
 */
public abstract class AbstractPersistenceSchemaHandler implements PersistenceSchemaHandler {

	private final String storeType;
		
	/**
	 * 
	 */
	public AbstractPersistenceSchemaHandler(String storeType) {
		Asserts.argumentNotNull(storeType, "storeType");
		this.storeType = storeType;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.model.store.StoreSchemaHandler#support()
	 */
	@Override
	public boolean supports() {
		MassyContext context = MassyUtils.getDefaultContext();
		String text = context.getDescriptor().getProperty(PersistenceConstants.STORE_TYPE, String.class);
		if (text == null){
			throw new PropertyNotFoundException(PersistenceConstants.STORE_TYPE);
		}
		return this.storeType.equals(text);
	}
		
	/**
	 * @return the storeType
	 */
	public String getStoreType() {
		return storeType;
	}

}
