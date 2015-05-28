/**
 * 
 */
package org.axonframework.eventsourcing;

import org.smarabbit.massy.util.Asserts;

/**
 * @author huangkaihui
 *
 */
public abstract class EventSourcedAggregateRootUtils {

	/**
	 * 获取{@link AbstractEventSourcedAggregateRoot}.
	 * @param entity {@link AbstractEventSourcedEntity},非空
	 * @return {@link AbstractEventSourcedAggregateRoot}.
	 */
	public static AbstractEventSourcedAggregateRoot<?> getAggregateRoot(AbstractEventSourcedEntity entity){
		Asserts.argumentNotNull(entity, "entity");
		return entity.getAggregateRoot();
	}
	
	/**
	 * 获取{@link AbstractEventSourcedAggregateRoot}.
	 * @param entity {@link Object},非空
	 * @return {@link AbstractEventSourcedAggregateRoot}.
	 */
	public static AbstractEventSourcedAggregateRoot<?> getAggregateRoot(Object entity){
		if (entity == null) return null;
		
		if (entity instanceof AbstractEventSourcedAggregateRoot){
			return (AbstractEventSourcedAggregateRoot<?>)entity;
		}
		
		if (entity instanceof AbstractEventSourcedEntity){
			return getAggregateRoot((AbstractEventSourcedEntity)entity);
		}
		
		return null;
	}
}
