/**
 * 
 */
package org.smarabbit.massy.bytecode.transformer;

import java.lang.instrument.IllegalClassFormatException;

import javassist.CtClass;

import org.smarabbit.massy.annotation.Adapt;
import org.smarabbit.massy.annotation.support.AdaptDefinition;
import org.smarabbit.massy.annotation.support.AnnotatedDefinitionManagerFactory;
import org.smarabbit.massy.bytecode.ClassTransformer;

/**
 * @author huangkaihui
 *
 */
public class AdaptTransformer extends ClassTransformer {

	/**
	 * 
	 */
	public AdaptTransformer() {
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.bytecode.ClassTransformer#doTransform(javassist.CtClass)
	 */
	@Override
	protected void doTransform(CtClass target)
			throws IllegalClassFormatException {
		try{
		Adapt anno = this.getAnnotation(Adapt.class, target);
		if (anno != null){
			String declaringTypeName = target.getName();
			AdaptDefinition definition = new AdaptDefinition(declaringTypeName, anno);
			AnnotatedDefinitionManagerFactory.getDefault().addDefinition(declaringTypeName, definition);
		}
		}catch(Exception e){
			throw new IllegalClassFormatException(e.getMessage());
		}

	}

	
}
