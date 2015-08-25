/**
 * 
 */
package org.smarabbit.massy.model.provider;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.smarabbit.massy.util.Asserts;

/**
 * 简单名称映射器，实现{@link NamedMapper}接口
 * @author huangkaihui
 *
 */
public class SimpleNamedMapper implements NamedMapper {

	private final Map<String, String> namedMap;
	
	/**
	 * 
	 */
	public SimpleNamedMapper() {
		this(new HashMap<String, String>());
	}
	
	public SimpleNamedMapper(Map<String, String> map){
		Asserts.argumentNotNull(map, "map");
		this.namedMap = map;
	}
	
	/**
	 * 添加键和映射名
	 * @param key
	 * @param value
	 */
	public void add(String key, String mappingName){
		Asserts.argumentNotNull(key,"key");
		Asserts.argumentNotNull(mappingName, "mappingName");
		this.namedMap.put(key, mappingName);
	}
	
	/**
	 * 键替换，用新键替换旧键，替换完成后，新键绑定旧键的值。
	 * 旧键被删除。
	 *
	 * @param newKey 新键
	 * @param oldKey 旧键
	 * @return 
	 * 			true替换成功，false失败
	 */
	public boolean replaceKey(String newKey, String oldKey){
		if (this.namedMap.containsKey(oldKey)){
			String value = this.namedMap.remove(oldKey);
			this.namedMap.put(newKey, value);
			return true;
		}
		return false;
	}
	
	/**
	 * 键复制，复制后增减新键，新键对应的值为复制键的值
	 * 
	 * @param newKey 新键
	 * @param copyKey 旧键
	 * @return
	 * 			true复制成功，false失败。
	 */
	public boolean copyKey(String newKey, String copyKey){
		if (this.namedMap.containsKey(copyKey)){
			String value = this.namedMap.get(copyKey);
			this.namedMap.put(newKey, value);
			return true;
		}
		return false;
	}
	
	/**
	 * 移除键
	 * @param key 键值
	 * @return 
	 * 			true操作成功，false失败
	 */
	public boolean remove(String key){
		return this.namedMap.remove(key) != null;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.model.store.provider.NamedMapper#contains(java.lang.String)
	 */
	@Override
	public boolean contains(String name) {
		return this.namedMap.containsKey(name);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.model.store.provider.NamedMapper#getNames()
	 */
	@Override
	public String[] getNames() {
		Set<String> s = this.namedMap.keySet();
		return s.toArray(new String[s.size()]);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.model.store.provider.NamedMapper#getMappingName(java.lang.String)
	 */
	@Override
	public String getMappingName(String name) {
		return this.namedMap.containsKey(name) ? 
				this.namedMap.get(name) : name;
	}
	
	/**
	 * 反转
	 * @return
	 */
	public Map<String, String> reverse(){
		Map<String, String> result = new HashMap<String, String>();
		for (Entry<String, String> entry : this.namedMap.entrySet()){
			result.put(entry.getValue(), entry.getKey());
		}
		return result;
	}
	
	/**
	 * 获取反转的{@link NamedMapper}
	 * @return
	 */
	public NamedMapper getReverseNamedMapper(){
		return new SimpleNamedMapper(this.reverse());
	}

}
