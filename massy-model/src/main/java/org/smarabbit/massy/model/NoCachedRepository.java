/**
 * 
 */
package org.smarabbit.massy.model;

/**
 * @author huangkaihui
 *
 */
public class NoCachedRepository implements CachedRepository {

	/**
	 * 
	 */
	public NoCachedRepository() {
		
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.model.CachedRepository#addEntry(java.lang.Class, java.lang.Object, java.lang.Object)
	 */
	@Override
	public <K, V> void addEntry(Class<?> type, K identifier, V object) {
		
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.model.CachedRepository#findById(java.lang.Class, java.lang.Object)
	 */
	@Override
	public <K, V> V findById(Class<?> type, K identifier) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.model.CachedRepository#remove(java.lang.Class, java.lang.Object)
	 */
	@Override
	public <K> void remove(Class<?> type, K identifier) {
		
	}

}
