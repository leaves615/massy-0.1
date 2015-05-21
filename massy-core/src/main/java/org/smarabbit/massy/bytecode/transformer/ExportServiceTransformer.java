/**
 * 
 */
package org.smarabbit.massy.bytecode.transformer;

import java.lang.instrument.IllegalClassFormatException;

import javassist.CtClass;

import org.smarabbit.massy.annotation.ExportService;
import org.smarabbit.massy.annotation.support.AnnotatedDefinitionManagerFactory;
import org.smarabbit.massy.annotation.support.ExportServiceDefinition;
import org.smarabbit.massy.bytecode.ClassTransformer;

/**
 * @author huangkaihui
 *
 */
public class ExportServiceTransformer extends ClassTransformer {

	/**
	 * 
	 */
	public ExportServiceTransformer() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.bytecode.ClassTransformer#doTransform(javassist.CtClass)
	 */
	@Override
	protected void doTransform(CtClass target)
			throws IllegalClassFormatException {
		try {
			ExportService anno = this.getAnnotation(ExportService.class, target);
			if (anno != null){
				String className = target.getName();
				ExportServiceDefinition definition = new ExportServiceDefinition(className, anno.serviceTypes());
				definition.setAlias(anno.alias());
				
				AnnotatedDefinitionManagerFactory.getDefault().addDefinition(className, definition);
			}
		} catch (Exception e) {
			throw new IllegalClassFormatException(e.getMessage());
		}
		
	}

}
