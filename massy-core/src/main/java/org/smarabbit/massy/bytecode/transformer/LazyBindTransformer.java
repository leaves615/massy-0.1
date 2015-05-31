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
import org.smarabbit.massy.bytecode.FieldTransformer;
import org.smarabbit.massy.lazyload.LazyBinder;
import org.smarabbit.massy.lazyload.LazyLoadUtils;

/**
 * {@link LazyBind}注解处理器，创建并注册{@link LazyBindMatchEntry}
 * @author huangkaihui
 *
 */
public class LazyBindTransformer extends FieldTransformer {

	private static final Logger logger =
			LoggerFactory.getLogger(LazyBindTransformer.class);
	
	private FieldEditor editor = new FieldEditor();
	/**
	 * 
	 */
	public LazyBindTransformer() {
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
			try{
				//字段不同退出
				if (f.getField() != field) return;			
				//写方法退出
				if (f.isWriter()) return;
		
				String className = this.field.getDeclaringClass().getName() + ".class";
				String fName = f.getFieldName();
				String fieldName = "this." + fName;
			
				StringBuffer buff = new StringBuffer();
				buff.append("{\n")
					.append("\t").append("if (").append(fieldName).append(" == null){").append("\n")
					
					.append("\t\t").append(LazyBinder.class.getName()).append(" binder=").append(LazyLoadUtils.class.getName())
						.append(".getLazyBinder(").append(className).append(",").append("\"").append(fName).append("\"").append(");\n")
						
					.append("\t\t").append(fieldName)
						.append("= ($r)").append(" binder.getValue(").append("this").append(");").append("\n");
											
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
		 * @param field the field to set
		 */
		public void setField(CtField field) {
			this.field = field;
		}
		
	}
}
