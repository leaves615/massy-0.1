/**
 * 
 */
package org.smarabbit.massy.axon.spring;

import org.axonframework.eventhandling.Cluster;
import org.axonframework.eventhandling.ClusterSelector;
import org.axonframework.eventhandling.EventListener;
import org.smarabbit.massy.MassyUtils;
import org.springframework.beans.factory.InitializingBean;

/**
 * 提供基于Massy Cluster服务的选择器
 * @author huangkaihui
 *
 */
public class ClusterServiceSelector implements ClusterSelector,  InitializingBean {

	private ClusterSelector selector;
	
	/**
	 * 
	 */
	public ClusterServiceSelector() {
	}

	/* (non-Javadoc)
	 * @see org.axonframework.eventhandling.ClusterSelector#selectCluster(org.axonframework.eventhandling.EventListener)
	 */
	@Override
	public Cluster selectCluster(EventListener eventListener) {
		return this.selector.selectCluster(eventListener);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		this.selector = MassyUtils.getDefaultContext().getService(ClusterSelector.class);
	}
		
}
