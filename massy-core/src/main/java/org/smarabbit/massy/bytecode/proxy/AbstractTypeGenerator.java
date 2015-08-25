/**
 * 
 */
package org.smarabbit.massy.bytecode.proxy;

import javassist.ClassPool;

/**
 * @author huangkaihui
 *
 */
public abstract class AbstractTypeGenerator<T> implements TypeGenerator<T> {

	protected final static ClassPool pool = ClassPool.getDefault();
	
	/**
	 * 
	 */
	public AbstractTypeGenerator() {

	}

}
