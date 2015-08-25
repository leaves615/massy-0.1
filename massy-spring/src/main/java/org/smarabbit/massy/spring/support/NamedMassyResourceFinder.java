/**
 * 
 */
package org.smarabbit.massy.spring.support;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.smarabbit.massy.spring.MassyResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * 按文件名查找{@link MassyResource}
 * @author huangkaihui
 *
 */
public class NamedMassyResourceFinder extends AbstractMassyResourceFinder {
	
	private static final String SPLITER = System.getProperty("file.separator") ;
	
	public NamedMassyResourceFinder(){
		
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.spring.support.AbstractMassyResourceFinder#doLookup(java.lang.String, org.springframework.core.io.support.ResourcePatternResolver, java.util.Map)
	 */
	@Override
	protected void doLookup(String pattern,
			ResourcePatternResolver resourceLoader,
			Map<String, Collection<Resource>> resources) throws IOException {
		Resource[] result = resourceLoader.getResources(pattern);
		for (Resource resource: result){
			String name = this.getName(resource.getURL());
						
			Collection<Resource> c = resources.get(name);
			if (c == null){
				c = new ArrayList<Resource>();
				resources.put(name, c);
			}
			c.add(resource);
		}
	}
	
	/**
	 * 获取应用程序名称
	 * @param url
	 * 		{@link URL}
	 * @return
	 * 		字符串
	 */
	protected String getName(URL url){
		String path = url.getPath();
		int index = path.lastIndexOf(SPLITER);
		if (index !=-1){
			path = path.substring(index+1);
		}
		return path;
	}
}