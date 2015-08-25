/**
 * 
 */
package org.smarabbit.massy.bytecode;

import java.lang.instrument.IllegalClassFormatException;
import java.util.List;

import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

import org.smarabbit.massy.util.ServiceLoaderUtils;

/**
 * @author huangkaihui
 *
 */
public class TransformerHandlerManager  extends ClassTransformer{

	private List<ClassTransformer> classTrans;
	private List<FieldTransformer> fieldTrans;
	private List<MethodTransformer> methodTrans;
	
	/**
	 * 
	 */
	public TransformerHandlerManager() {
		 this.loadTransformers();
	}
	
	
	
	@Override
	protected void doTransform(CtClass target)
			throws IllegalClassFormatException {
		if (!this.classTrans.isEmpty()){
			for (ClassTransformer transformer : this.classTrans){
				transformer.transform(target);
			}
		}
		
		if (!this.fieldTrans.isEmpty()){
			CtField[] fields = target.getDeclaredFields();
			for (CtField field: fields){
				for (FieldTransformer transformer: this.fieldTrans){
					transformer.transform(field);
				}
			}
		}
		
		if (!this.methodTrans.isEmpty()){
			CtMethod[] methods = target.getDeclaredMethods();
			for (CtMethod method: methods){
				for (MethodTransformer transformer : this.methodTrans){
					transformer.transform(method);
				}
			}
		}
	}

	private void loadTransformers(){
		this.classTrans = ServiceLoaderUtils.loadServices(ClassTransformer.class);
		this.fieldTrans = ServiceLoaderUtils.loadServices(FieldTransformer.class);
		this.methodTrans = ServiceLoaderUtils.loadServices(MethodTransformer.class);
	}

}
