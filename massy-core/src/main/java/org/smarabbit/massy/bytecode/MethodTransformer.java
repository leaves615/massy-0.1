/**
 * 
 */
package org.smarabbit.massy.bytecode;

import java.lang.instrument.IllegalClassFormatException;

import org.smarabbit.massy.util.Asserts;
import javassist.CtMethod;

/**
 * @author huangkaihui
 *
 */
public abstract class MethodTransformer  implements TransformerHandler<CtMethod>{

	/**
	 * 
	 */
	public MethodTransformer() {
		
	}

	@Override
	public void transform(CtMethod target) throws IllegalClassFormatException {
		Asserts.argumentNotNull(target, "target");
		this.doTransform(target);
	}
	
	protected <A> A getAnnotation(Class<A> annoType, CtMethod method) throws ClassNotFoundException{
		Object result = method.getAnnotation(annoType);
		return annoType.cast(result);
	}

	protected abstract void doTransform(CtMethod target) throws IllegalClassFormatException;
}
