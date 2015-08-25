/**
 * 
 */
package org.smarabbit.massy.model.persistent.jdbc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;

/**
 * @author huangkaihui
 *
 */
public abstract class AbstractJdbcSchema implements JdbcSchema {

	private String[] ddls = JdbcSchema.EMPTY;
	private String[] cmds = JdbcSchema.EMPTY;
	
	private Map<String, String> namedMap;
	
	/**
	 * 
	 */
	public AbstractJdbcSchema() {

	}
	
	/* (non-Javadoc)
	 * @see org.smarabbit.massy.model.persistent.PersistenceSchema#getDataDefinitionLanguages()
	 */
	@Override
	public String[] getDataDefinitionLanguages() {
		return this.ddls;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.model.persistent.PersistenceSchema#getPreprocessCommands()
	 */
	@Override
	public String[] getPreprocessCommands() {
		return this.cmds;
	}
	
	@PostConstruct
	public void init(){
		Map<String, String> defaultMappedName = this.getDefaultMappedName();
		if (this.namedMap != null){
			defaultMappedName.putAll(namedMap);
		}
		
		List<String> list = this.initDataDefinitionLanguages(defaultMappedName);
		this.ddls = list.toArray(new String[list.size()]);
		
		list = this.initPreprocessCommands(defaultMappedName);
		this.cmds = list.toArray(new String[list.size()]);
	}
	

	/**
	 * 获取缺省的字符串映射名
	 * @return
	 * 		{@link Map},不能返回null.
	 */
	protected Map<String, String> getDefaultMappedName(){
		return new HashMap<String, String>();
	}
	
	/**
	 * 替换MappedName的键值
	 * @param text 字符串
	 * @param mappedName {@link Map},非空
	 * @return
	 * 		替换后的字符串
	 */
	protected String replaceMappedName(String text, Map<String, String> mappedName){
		String result = new String(text);
		for (Entry<String, String> entry: mappedName.entrySet()){
			result = StringUtils.replace(result, entry.getKey(), entry.getValue());
		}
		return result;
	}
	
	/**
	 * @return the namedMap
	 */
	public Map<String, String> getNamedMap() {
		return namedMap;
	}

	/**
	 * @param namedMap the namedMap to set
	 */
	public void setNamedMap(Map<String, String> namedMap) {
		this.namedMap = namedMap;
	}

	protected abstract List<String> initDataDefinitionLanguages(Map<String, String> mappedName);
	
	protected abstract List<String> initPreprocessCommands(Map<String, String> mappedName);
}
