/**
 * 
 */
package org.smarabbit.massy.bytecode.transformer;

import java.lang.instrument.IllegalClassFormatException;

import javassist.CtMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smarabbit.massy.annotation.LazyBindHandler;
import org.smarabbit.massy.annotation.support.AnnotatedDefinitionManagerFactory;
import org.smarabbit.massy.annotation.support.LazyBindHandlerDefinition;
import org.smarabbit.massy.bytecode.MethodTransformer;

/**
 * {@link LazyBindHandler}增强处理器
 * @author huangkaihui
 *
 */
public class LazyBindHandlerTransformer extends MethodTransformer {
	
	private static final Logger logger =
			LoggerFactory.getLogger(LazyBindHandlerTransformer.class);
	
	/**
	 * 
	 */
	public LazyBindHandlerTransformer() {

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
				String declaringTypeName = target.getDeclaringClass().getName();
				String methodName = target.getName();
				
				if (target.getParameterTypes().length != 1){
					if (logger.isWarnEnabled()){
						logger.warn("method parameter count not equal one: class=" 
								+ declaringTypeName + ", method=" + methodName + ".");
					}
					return;
				}
				
				String paramTypeName = target.getParameterTypes()[0].getName();
				String fieldName = anno.fieldName();
				
				LazyBindHandlerDefinition definition =
						new LazyBindHandlerDefinition(declaringTypeName, methodName, paramTypeName, fieldName);
				AnnotatedDefinitionManagerFactory.getDefault().addDefinition(declaringTypeName, definition);
			}
		}catch(Exception e){
			throw new IllegalClassFormatException(e.getMessage());
		}

	}

}
