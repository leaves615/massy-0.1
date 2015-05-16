/**
 * 
 */
package org.smarabbit.massy.bytecode.transformer;

import java.lang.instrument.IllegalClassFormatException;

import javassist.CannotCompileException;
import javassist.CtField;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smarabbit.massy.annotation.LazyBind;
import org.smarabbit.massy.annotation.support.LazyBinderRepositoryFactory;
import org.smarabbit.massy.bytecode.FieldTransformer;

/**
 * {@link LazyBind}注解处理器，创建并注册{@link LazyBindMatchEntry}
 * @author huangkaihui
 *
 */
public class LazyBindFieldTransformer extends FieldTransformer {

	private static final Logger logger =
			LoggerFactory.getLogger(LazyBindFieldTransformer.class);
	
	private FieldEditor editor = new FieldEditor();
	/**
	 * 
	 */
	public LazyBindFieldTransformer() {
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.bytecode.FieldTransformer#doTransform(javassist.CtField)
	 */
	@Override
	protected void doTransform(CtField target)
			throws IllegalClassFormatException {
		LazyBind anno;
		try {
			anno = this.getAnnotation(LazyBind.class, target);
			if (anno != null){
								
				this.editor.setField(target);
				target.getDeclaringClass().instrument(this.editor);
			}
		} catch (Exception e) {
			throw new IllegalClassFormatException(e.getMessage());
		}
	}

	private class FieldEditor extends ExprEditor{
		
		private CtField field;
	
		public FieldEditor(){
			
		}
		
		/* (non-Javadoc)
		 * @see javassist.expr.ExprEditor#edit(javassist.expr.FieldAccess)
		 */
		@Override
		public void edit(FieldAccess f) throws CannotCompileException {
			//写方法退出
			if (f.isWriter()) return;
						
			String className = this.field.getDeclaringClass().getName() + ".class";
			String fName = f.getFieldName();
			String fieldName = "this." + fName;
			try{
				StringBuffer buff = new StringBuffer();
				buff.append("{\n")
					.append("\t").append("if (").append(fieldName).append(" == null){").append("\n")
					.append("\t\t").append(fieldName)
						.append("= ($r)").append(LazyBinderRepositoryFactory.class.getName()).append(".getDefault()")
						.append(".lazyLoad(").append(className).append(",").append("\"").append(fName).append("\"").append(",").append("this").append(");\n");
					
				buff.append("\t}\n");		
				
				buff.append("\t").append("$_= $proceed($$);\n");
				buff.append("}\n");
				
				if (logger.isTraceEnabled()){
					logger.trace("read field transform: " + f.getClassName() + "#" + fName + "\n" +
							buff.toString());
				}
				
				f.replace(buff.toString());
			}catch(Exception e){
				throw new CannotCompileException(e.getMessage(),e);
			}
		}

		/**
		 * @return the field
		 */
		@SuppressWarnings("unused")
		public CtField getField() {
			return field;
		}

		/**
		 * @param field the field to set
		 */
		public void setField(CtField field) {
			this.field = field;
		}
		
	}
}
