/**
 * 
 */
package org.smarabbit.massy.bytecode;

import java.lang.instrument.IllegalClassFormatException;

import org.smarabbit.massy.util.Asserts;

import javassist.CtField;

/**
 * @author huangkaihui
 *
 */
public abstract class FieldTransformer implements TransformerHandler<CtField> {

	/**
	 * 
	 */
	public FieldTransformer() {
	}

	@Override
	public void transform(CtField target) throws IllegalClassFormatException {
		Asserts.argumentNotNull(target, "target");
		this.doTransform(target);
	}
	
	protected <A> A getAnnotation(Class<A> annoType, CtField field) throws ClassNotFoundException{
		Object result = field.getAnnotation(annoType);
		return annoType.cast(result);
	}

	protected abstract void doTransform(CtField target) throws IllegalClassFormatException;
}
