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
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smarabbit.massy.MassyUtils;
import org.smarabbit.massy.support.OrderComparator;
import org.smarabbit.massy.util.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;

/**
 * 增强ShiroFilterFactoryBean,Bean初始化后，从MassyContext中自动查找
 * 所有的Realm实例，并设置到SecurityManager中。
 * 
 * @author huangkh
 *
 */
public class ExShiroFilterFactoryBean extends ShiroFilterFactoryBean
	implements InitializingBean{

	public static final String FILTER_FILE = "/META-INF/shiro/filterchain.properties";
	private static final Logger logger =
			LoggerFactory.getLogger(ExShiroFilterFactoryBean.class);
	/**
	 * 
	 */
	public ExShiroFilterFactoryBean() {

	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		DefaultSecurityManager securityManager = (DefaultSecurityManager) 
				this.getSecurityManager();
		if (securityManager != null){
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
			
			Map<String,String> filterChainDefinitionMap = this.getFilterChainDefinitionMap();
			if (!filterChainDefinitionMap.isEmpty()){
				this.setFilterChainDefinitionMap(filterChainDefinitionMap);
			}
		}
	}
	
	public Map<String,String> getFilterChainDefinitionMap(){
		
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
