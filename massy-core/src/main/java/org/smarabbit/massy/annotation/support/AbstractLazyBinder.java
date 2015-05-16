/**
 * 
 */
package org.smarabbit.massy.annotation.support;

import org.smarabbit.massy.util.Asserts;

/**
 * @author huangkaihui
 *
 */
public abstract class AbstractLazyBinder<T> implements LazyBinder<T> {

	private final Object handler;
	
	/**
	 * 
	 */
	protected AbstractLazyBinder(Object handler) {
		Asserts.argumentNotNull(handler, "handler");
		this.handler = handler;
	}

	/**
	 * @return the handler
	 */
	protected Object getHandler() {
		return handler;
	}
	
	/* (non-Javadoc)
	 * @see org.smarabbit.massy.lazybind.LazyBinder#getValue(java.lang.Object)
	 */
	@Override
	public Object getValue(T declaringObject) {
		return null;
	}

}
