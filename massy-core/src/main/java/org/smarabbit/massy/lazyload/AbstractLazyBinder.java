/**
 * 
 */
package org.smarabbit.massy.lazyload;

import org.smarabbit.massy.annotation.support.LazyBindDefinition;
import org.smarabbit.massy.annotation.support.LazyBindHandlerDefinition;
import org.smarabbit.massy.util.Asserts;

/**
 * @author huangkaihui
 *
 */
public abstract class AbstractLazyBinder<T> implements LazyBinder<T> {

	private final LazyBindDefinition definiton;
	private final Object handler;
	
	public AbstractLazyBinder(Object handler, LazyBindHandlerDefinition definition){
		this(handler, new LazyBindDefinition(definition.getParamTypeName(), definition.getFieldName()));
	}
	
	/**
	 * 
	 */
	protected AbstractLazyBinder(Object handler, LazyBindDefinition definition) {
		Asserts.argumentNotNull(handler, "handler");
		Asserts.argumentNotNull(definition, "definition");
		this.handler = handler;
		this.definiton = definition;
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

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.lazyload.LazyBinder#getDefinition()
	 */
	@Override
	public LazyBindDefinition getDefinition() {
		return this.definiton;
	}
}
