/**
 * 
 */
package org.smarabbit.massy.model.provider;

import org.smarabbit.massy.util.Asserts;

/**
 * @author huangkaihui
 *
 */
public abstract class AbstractDataProvider<T> implements DataProvider {

	private T source;
	private NamedMapper namedProvider;
	
	/**
	 * 
	 */
	public AbstractDataProvider(T source) {
		this(source, null);
	}
	
	public AbstractDataProvider(T source, NamedMapper namedProvider){
		Asserts.argumentNotNull(source, "source");
		this.source = source;
		this.namedProvider = namedProvider;
	}
	
	/* (non-Javadoc)
	 * @see org.smarabbit.massy.model.store.provider.DataProvider#contains(java.lang.String)
	 */
	@Override
	public boolean contains(String name) {
		Asserts.argumentNotNull(name, "name");
		NamedMapper provider = this.getNamedProvider();
		return provider != null ? provider.contains(name) : this.doContains(name);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.model.store.provider.DataProvider#getNames()
	 */
	@Override
	public String[] getNames() {
		NamedMapper provider = this.getNamedProvider();
		return provider != null ? provider.getNames() : this.doGetNames();
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.model.store.provider.DataProvider#getValue(java.lang.String)
	 */
	@Override
	public Object getValue(String name) {
		Asserts.argumentNotNull(name, "name");
		NamedMapper provider = this.getNamedProvider();
		String mappingName =  provider != null? provider.getMappingName(name): name;
		return this.doGetValue(mappingName);
	}

	/**
	 * 名称映射器
	 * @return
	 * 			{@link NamedMapper},可能返回null.
	 */
	protected NamedMapper getNamedProvider(){
		return this.namedProvider;
	}
	
	/**
	 * 获取数据源
	 * @return
	 */
	public T getSource() {
		return this.source;
	}

	protected abstract boolean doContains(String name);
	protected abstract String[] doGetNames();
	protected abstract Object doGetValue(String name);
}
