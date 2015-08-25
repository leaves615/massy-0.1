/**
 * 
 */
package org.smarabbit.massy.bytecode;

import java.lang.instrument.IllegalClassFormatException;

import org.smarabbit.massy.util.Asserts;

import javassist.CtClass;

/**
 * @author huangkaihui
 *
 */
public abstract class ClassTransformer implements TransformerHandler<CtClass> {

	/**
	 * 
	 */
	public ClassTransformer() {
		
	}

	@Override
	public void transform(CtClass target) throws IllegalClassFormatException {
		Asserts.argumentNotNull(target, "target");
		this.doTransform(target);
	}
	
	protected <A> A getAnnotation(Class<A> annoType, CtClass cc) throws ClassNotFoundException{
		Object result = cc.getAnnotation(annoType);
		return annoType.cast(result);
	}

	protected abstract void doTransform(CtClass target) throws IllegalClassFormatException;
}
