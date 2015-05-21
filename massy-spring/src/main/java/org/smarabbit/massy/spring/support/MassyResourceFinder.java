/**
 * 
 */
package org.smarabbit.massy.spring.support;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import org.smarabbit.massy.spring.MassyResource;

/**
 * Massy资源查找器,查找应用程序的Spring资源文件
 * @author huangkaihui
 *
 */
public interface MassyResourceFinder {

	/**
	 * 查找{@link MassyResource}
	 * @param patterns 
	 * 					定位的模式
	 * @return
	 * 		{@link Map}，不能返回null.
	 * @throws IOException
	 * 		IO存取异常
	 */
	Map<String, Collection<MassyResource>> find(String[] patterns) throws IOException;
}
