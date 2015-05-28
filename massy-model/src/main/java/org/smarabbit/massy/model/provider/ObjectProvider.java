/**
 * 
 */
package org.smarabbit.massy.model.provider;

import org.smarabbit.massy.util.Asserts;

/**
 * 对象数据提供器
 * @author huangkaihui
 *
 */
public class ObjectProvider implements DataProvider {
	
	private Object source;
	private ObjectFieldMapper mapper;

	public ObjectProvider(Object source, ObjectFieldMapper mapper) {
		Asserts.argumentNotNull(source, "source");
		Asserts.argumentNotNull(mapper, "mapper");
		this.source = source;
		this.mapper = mapper;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.model.store.provider.DataProvider#contains(java.lang.String)
	 */
	@Override
	public boolean contains(String name) {
		return this.mapper.contains(name);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.model.store.provider.DataProvider#getNames()
	 */
	@Override
	public String[] getNames() {
		return this.mapper.getNames();
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.model.store.provider.DataProvider#getValue(java.lang.String)
	 */
	@Override
	public Object getValue(String name) {
		return this.mapper.getValue(name, this.source);
	}


}
