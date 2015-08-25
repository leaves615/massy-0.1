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

	private static final ClassLoader LOADER = Thread.currentThread().getContextClassLoader();
	
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
		Asserts.argumentNotNull(declaringObject, "declaringObject");
		this.checkCaller(declaringObject);
		return this.doGetValue(declaringObject);
	}
	
	/**
	 * 检查调用是否由declaringObject发出
	 * @param declaringObject
	 */
	protected void checkCaller(T declaringObject){
		Class<?> type = declaringObject.getClass();
		StackTraceElement[] stacks = Thread.currentThread().getStackTrace();   
		if (stacks.length <4){
			throw new IllegalArgumentException("unauthorized access.");
		}
		
		String className = stacks[3].getClassName();
		if (type.getName().equals(className)){
			return;
		}
		
		//不等于，则判断继承关系
		Class<?> callerType;
		try {
			callerType = LOADER.loadClass(className);
			if (!callerType.isAssignableFrom(type)){
				throw new IllegalArgumentException("unauthorized access.");
			}
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("unauthorized access.");
		}
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.lazyload.LazyBinder#getDefinition()
	 */
	@Override
	public LazyBindDefinition getDefinition() {
		return this.definiton;
	}
	
	protected abstract Object doGetValue(T declaringObject);
}
