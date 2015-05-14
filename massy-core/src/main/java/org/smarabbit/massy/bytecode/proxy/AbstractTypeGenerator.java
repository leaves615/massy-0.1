/**
 * 
 */
package org.smarabbit.massy.bytecode.proxy;

import javassist.ClassPool;

/**
 * @author huangkaihui
 *
 */
public abstract class AbstractTypeGenerator implements TypeGenerator {

	protected final static ClassPool pool = ClassPool.getDefault();
	
	/**
	 * 
	 */
	public AbstractTypeGenerator() {

	}

}
