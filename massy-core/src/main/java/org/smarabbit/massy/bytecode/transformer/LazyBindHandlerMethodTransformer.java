/**
 * 
 */
package org.smarabbit.massy.bytecode.transformer;

import java.lang.instrument.IllegalClassFormatException;

import javassist.CtMethod;

import org.smarabbit.massy.annotation.LazyBindHandler;
import org.smarabbit.massy.annotation.support.LazyBinderRepositoryFactory;
import org.smarabbit.massy.bytecode.MethodTransformer;

/**
 * @author huangkaihui
 *
 */
public class LazyBindHandlerMethodTransformer extends MethodTransformer {
	
	/**
	 * 
	 */
	public LazyBindHandlerMethodTransformer() {

	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.bytecode.MethodTransformer#doTransform(javassist.CtMethod)
	 */
	@Override
	protected void doTransform(CtMethod target)
			throws IllegalClassFormatException {
		try{
			LazyBindHandler anno = this.getAnnotation(LazyBindHandler.class, target);
			if (anno != null){
				LazyBinderRepositoryFactory.getDefault().register(target, anno);
			}
		}catch(Exception e){
			throw new IllegalClassFormatException(e.getMessage());
		}

	}

}
