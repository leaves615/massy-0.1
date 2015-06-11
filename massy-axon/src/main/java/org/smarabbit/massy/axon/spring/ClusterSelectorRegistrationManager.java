/**
 * 
 */
package org.smarabbit.massy.axon.spring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.axonframework.eventhandling.Cluster;
import org.axonframework.eventhandling.ClusterSelector;
import org.axonframework.eventhandling.EventListener;
import org.smarabbit.massy.Descriptor;
import org.smarabbit.massy.service.ServiceRegistration;
import org.smarabbit.massy.service.support.AbstractServiceRegistrationManager;
import org.smarabbit.massy.spec.Specification;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author huangkaihui
 *
 */
public class ClusterSelectorRegistrationManager extends
		AbstractServiceRegistrationManager<ClusterSelector> {
	
	private SingletonClusterSelector selector =
			new SingletonClusterSelector();
	private List<ServiceRegistration> registrationList =
			new CopyOnWriteArrayList<ServiceRegistration>();
	
	public ClusterSelectorRegistrationManager(){
		
	}
	
	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.support.AbstractServiceRegistrationManager#getDefaultService()
	 */
	@Override
	public ClusterSelector getDefaultService() {
		return this.selector;
	}



	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.support.AbstractServiceRegistrationManager#findService(org.smarabbit.massy.Descriptor)
	 */
	@Override
	public ClusterSelector findService(Descriptor descriptor) {
		return this.selector;
	}



	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.support.AbstractServiceRegistrationManager#findService(java.lang.String)
	 */
	@Override
	public ClusterSelector findService(String alias) {
		return this.selector;
	}



	/* (non-Javadoc)
	 * @see org.smarabbit.massy.service.support.AbstractServiceRegistrationManager#findService(org.smarabbit.massy.spec.Specification)
	 */
	@Override
	public ClusterSelector findService(Specification<Descriptor> spec) {
		return super.findService(spec);
	}



	/* (non-Javadoc)
	 * @see org.smarabbit.massy.support.AbstractRegistrationManager#doAdd(org.smarabbit.massy.Registration)
	 */
	@Override
	protected void doAdd(ServiceRegistration registration) {
		this.registrationList.add(registration);
		this.selector.add(registration);		
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.support.AbstractRegistrationManager#doRemove(org.smarabbit.massy.Registration)
	 */
	@Override
	protected void doRemove(ServiceRegistration registration) {
		this.registrationList.remove(registration);
		this.selector.remove(registration);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.support.AbstractRegistrationManager#getRegistrations()
	 */
	@Override
	protected Collection<ServiceRegistration> getRegistrations() {
		return this.registrationList;
	}
	
	/**
	 * 单例{@link ClusterSelector}
	 * @author huangkaihui
	 *
	 */
	private class SingletonClusterSelector implements ClusterSelector {

		private CopyOnWriteArrayList<ServiceRegistration> registrations = new CopyOnWriteArrayList<ServiceRegistration>();
		private ClusterSelectComparator comparator =
				new ClusterSelectComparator();
				
		public SingletonClusterSelector() {
			super();
		}

		/* (non-Javadoc)
		 * @see org.axonframework.eventhandling.ClusterSelector#selectCluster(org.axonframework.eventhandling.EventListener)
		 */
		@Override
		public Cluster selectCluster(EventListener eventListener) {
			Cluster cluster = null;
	        Iterator<ServiceRegistration> it = this.registrations.iterator();
	        while (cluster == null && it.hasNext()) {
	        	ClusterSelector selector = (ClusterSelector)it.next().getService();
	            cluster = selector.selectCluster(eventListener);
	        }
	        return cluster;
		}
		
		public synchronized  void add(ServiceRegistration registration){
			List<ServiceRegistration> list = new ArrayList<ServiceRegistration>(this.registrations);
			list.add(registration);
			Collections.sort(list, comparator);
			this.registrations = new CopyOnWriteArrayList<ServiceRegistration>(list);
		}
		
		public  void remove(ServiceRegistration registration){
			this.registrations.remove(registration);
		}
		
	}
	
	private class ClusterSelectComparator implements Comparator<ServiceRegistration>{

		public ClusterSelectComparator() {
			super();
		}

		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(ServiceRegistration sr1, ServiceRegistration sr2) {
			String name1 = sr1.getDescriptor().getProperty("beanName", String.class);
			String name2 = sr2.getDescriptor().getProperty("beanName", String.class);
			
			ClusterSelector selector1 = (ClusterSelector)sr1.getService();
			ClusterSelector selector2 = (ClusterSelector)sr2.getService();
			
			int order1 = 0;
			if (selector1 instanceof Ordered) {
                order1 = ((Ordered) selector1).getOrder();
            } else if (selector1.getClass().isAnnotationPresent(Order.class)) {
                order1 = selector1.getClass().getAnnotation(Order.class).value();
            }
			
			int order2 = 0;
			if (selector2 instanceof Ordered) {
                order2 = ((Ordered) selector2).getOrder();
            } else if (selector2.getClass().isAnnotationPresent(Order.class)) {
                order2 = selector2.getClass().getAnnotation(Order.class).value();
            }
			
			 if (order1 == order2) {
				 return name1.compareTo(name2);
	         } else {
	              return (order1 < order2) ? -1 : (1);
	         }
		}
		
	}
	
}
