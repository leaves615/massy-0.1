/**
 * 
 */
package org.smarabbit.massy.model.provider;

import java.util.Map;

import org.smarabbit.massy.util.Asserts;

/**
 * Map数据提供器
 * @author huangkaihui
 *
 */
public class MapProvider extends AbstractDataProvider<Map<String, Object>> {
	
	
	/**
	 * 
	 */
	public MapProvider(Map<String, Object> source) {
		super(source);
	}
	
	
		
	public MapProvider(Map<String, Object> source, NamedMapper provider) {
		super(source, provider);
	}

	@Override
	public boolean doContains(String name) {
		Asserts.argumentNotNull(name, "name");
		return 
				this.getSource().containsKey(name);
	}

	
	@Override
	public String[] doGetNames() {
		return this.getSource().keySet().toArray(new String[this.getSource().size()]);
	}

	
	@Override
	public Object doGetValue(String name) {
		return this.getSource().get(name);
	}


}
