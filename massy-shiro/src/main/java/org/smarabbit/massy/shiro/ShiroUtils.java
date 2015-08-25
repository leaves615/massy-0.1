/**
 * 
 */
package org.smarabbit.massy.shiro;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smarabbit.massy.MassyUtils;
import org.smarabbit.massy.service.OrderComparator;
import org.smarabbit.massy.util.CollectionUtils;

/**
 * @author huangkaihui
 *
 */
public abstract class ShiroUtils {

	private static final Logger logger = LoggerFactory.getLogger(ShiroUtils.class);
	public static final String FILTER_FILE = "/META-INF/shiro/filterchain.properties";
	
	/**
	 * 绑定Realm
	 * @param securityManager
	 */
	public static void bindRealms(DefaultSecurityManager securityManager){
		if (securityManager == null){
			if (logger.isWarnEnabled()){
				logger.warn("securityManger was null, canot bind realms.");
			}
			return;
		}
		
		//查找所有Realm服务
		Realm[] realms = MassyUtils.getDefaultContext().getAllServices(Realm.class);
		List<Realm> list = Arrays.asList(realms);
			
		//附加SecurityManager的Realms
		Collection<Realm> c = securityManager.getRealms();
		if (!CollectionUtils.isEmpty(c)){
			
			//排重
			for (Realm realm :c){
				if (!list.contains(realm)){
					list.add(realm);
				}
			}
		}
		
		if (!list.isEmpty()){
			//排序
			Collections.sort(list, new OrderComparator());
			securityManager.setRealms(list);
		}
	}
	
	/**
	 * 加载URL过滤配置文件
	 * @return
	 */
	public static Map<String,String> getFilterChainDefinitionMap(){
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Map<String,String> result = new HashMap<String,String>();
		
		try{
			Enumeration<URL> em = loader.getResources(FILTER_FILE);
			while (em.hasMoreElements()){
				URL url = em.nextElement();
				
				Properties props = new Properties();
				props.load(url.openStream());
				
				
				Enumeration<Object> keys = props.keys();
				while (keys.hasMoreElements()){
					String key = (String)keys.nextElement();
					result.put(key, (String)props.getProperty(key));
				}
		}
		}catch(IOException e){
			if (logger.isWarnEnabled()){
				logger.warn(e.getMessage(), e);
			}
		}
		
		return result;
	}
	
	
}
