/**
 * 
 */
package org.smarabbit.massy.spring.support;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.smarabbit.massy.spring.MassyResource;
import org.smarabbit.massy.util.Asserts;
import org.smarabbit.massy.util.CollectionUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * 实现{@link MassyResourceFinder}的抽象基础类
 * @author huangkaihui
 *
 */
public abstract class AbstractMassyResourceFinder implements
		MassyResourceFinder {

	/**
	 * 
	 */
	public AbstractMassyResourceFinder() {
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.spring.support.MassyResourceFinder#find(java.lang.String[])
	 */
	@Override
	public Map<String, Collection<MassyResource>> find(String[] patterns) throws IOException {
		Asserts.argumentNotEmpty(patterns, "patterns");
		
		//查找资源
		Map<String, Map<String, Collection<Resource>>> map = this.doFind(patterns);
		
		//转换为MassyResource
		Map<String, Collection<MassyResource>> result = 
				new LinkedHashMap<String, Collection<MassyResource>>();
		
		if (!CollectionUtils.isEmpty(map)){
			Iterator<Entry<String, Map<String,Collection<Resource>>>> it =
					map.entrySet().iterator();
			
			while (it.hasNext()){
				Entry<String, Map<String, Collection<Resource>>> entry = it.next();
				if (entry.getValue() == null){
					continue;
				}
				
				//按路径模式添加集合
				List<MassyResource> list = new ArrayList<MassyResource>();
				result.put(entry.getKey(), list);
				
				for (Entry<String, Collection<Resource>> elem: entry.getValue().entrySet()){
					list.add(new MassyResource(
							elem.getKey(),
							elem.getValue().toArray(new Resource[elem.getValue().size()])
							));
				}
			}
		}
		
		//返回
		return result;
	}

	/**
	 * 执行查找
	 * @param patterns
	 * @return
	 * @throws IOException
	 */
	protected Map<String, Map<String, Collection<Resource>>> doFind(String[] patterns) throws IOException{
		ResourcePatternResolver resourceLoader = 
				new PathMatchingResourcePatternResolver();
		
		Map<String, Map<String, Collection<Resource>>> result = this.createMap();
		for (String pattern: patterns){
			Map<String, Collection<Resource>> map =
					new LinkedHashMap<String, Collection<Resource>>();
			this.doLookup(pattern, resourceLoader, map);
			if (!map.isEmpty()){
				result.put(pattern, map);
			}
		}
		return result;
	}
	
	/**
	 * 创建{@link Map},默认返回{@link LinkedHashMap}实例
	 * @return
	 * 		{@link LinkedHashMap}
	 */
	protected Map<String, Map<String, Collection<Resource>>> createMap(){
		return  new LinkedHashMap<String, Map<String, Collection<Resource>>>();
	}
	
	/**
	 * 执行查找
	 * @param pattern
	 * 					路径模式
	 * @param resourceLoader
	 * 					{@link ResourcePatternResolver)
	 * @param resources
	 * 					{@link Map}
	 * @throws IOException
	 * 					IO存取异常
	 */
	protected abstract void doLookup(String pattern, ResourcePatternResolver resourceLoader, Map<String, Collection<Resource>> resources) 
			throws IOException;
}
