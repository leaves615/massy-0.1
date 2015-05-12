/**
 * 
 */
package org.smarabbit.massy.support;

import java.util.Comparator;

import org.smarabbit.massy.annotation.Order;

/**
 * 排序比较器,提供对{@link Ordered}实例，或者注解{@link Order}实例进行排序比较。
 * <br>
 * 非以上两种实例，则按{@link Ordered#DEFAULT_PRECEDENCE}进行比较
 * @author huangkh
 *
 */
public class OrderComparator implements Comparator<Object> {

	/**
	 * 
	 */
	public OrderComparator() {
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Object o1, Object o2) {
				
		int order1 = this.getOrder(o1);
		int order2 = this.getOrder(o2);
		
		return order1 == order2? 0:(order1 > order2 ? 1 : -1);
	}

	protected int getOrder(Object target){
		if (Ordered.class.isInstance(target)){
			return ((Ordered)target).getOrder();
		}
		
		Order order = target.getClass().getAnnotation(Order.class);
		if (order != null){
			return order.value();
		}
		
		return Ordered.DEFAULT_PRECEDENCE;
	}
	
}
