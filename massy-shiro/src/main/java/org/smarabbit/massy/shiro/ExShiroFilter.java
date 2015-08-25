/**
 * 
 */
package org.smarabbit.massy.shiro;

import java.util.Map;

import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.web.env.WebEnvironment;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.apache.shiro.web.util.WebUtils;
import org.smarabbit.massy.util.CollectionUtils;

/**
 * @author huangkaihui
 *
 */
public class ExShiroFilter extends ShiroFilter {

	/**
	 * 
	 */
	public ExShiroFilter() {

	}
	
   @Override
    public void init() throws Exception {
        WebEnvironment env = WebUtils.getRequiredWebEnvironment(getServletContext());

        //设置Realms
        WebSecurityManager securityManager = env.getWebSecurityManager();
        setSecurityManager(securityManager);
        if (securityManager instanceof DefaultSecurityManager){
        	ShiroUtils.bindRealms((DefaultSecurityManager)securityManager);
        }

        //设置URL过滤
        FilterChainResolver resolver = env.getFilterChainResolver();
        if (resolver instanceof PathMatchingFilterChainResolver){
        	PathMatchingFilterChainResolver chainResolver = (PathMatchingFilterChainResolver)resolver;
        	FilterChainManager chainManager = chainResolver.getFilterChainManager();
        	if (chainManager == null){
        		chainManager = new DefaultFilterChainManager();
        		chainResolver.setFilterChainManager(chainManager);
        	}
        	
        	Map<String,String>  filterChainDefinitionMap = ShiroUtils.getFilterChainDefinitionMap();
        	 if (!CollectionUtils.isEmpty(filterChainDefinitionMap)) {
                 for (Map.Entry<String, String> entry : filterChainDefinitionMap.entrySet()) {
                     String url = entry.getKey();
                     String chainDefinition = entry.getValue();
                     chainManager.createChain(url, chainDefinition);
                 }
             }
        }
        
        if (resolver != null) {
            setFilterChainResolver(resolver);
        }
    }

}
