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
import org.smarabbit.massy.MassyUtils;
import org.smarabbit.massy.annotation.ImportService;
import org.smarabbit.massy.annotation.support.AnnotatedDefinitionManagerFactory;
import org.smarabbit.massy.annotation.support.ImportServiceDefinition;
import org.smarabbit.massy.bytecode.FieldTransformer;

/**
 * 提供{@link ImportService}注解的增强转换方法
 * @author huangkaihui
 *
 */
public class ImportServiceTransformer extends FieldTransformer {

	private static final Logger logger =
			LoggerFactory.getLogger(ImportServiceTransformer.class);
	
	/**
	 * 
	 */
	public ImportServiceTransformer() {
		
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.bytecode.FieldTransformer#doTransform(javassist.CtField)
	 */
	@Override
	protected void doTransform(CtField target)
			throws IllegalClassFormatException {
		
		try {
			ImportService anno = this.getAnnotation(ImportService.class, target);
			if (anno != null){
				String className = target.getDeclaringClass().getName();
				ImportServiceDefinition definition =
						new ImportServiceDefinition(
								className,
								target.getName(), 
								anno.serviceType(), 
								anno.alias(), 
								anno.allowNull());
				AnnotatedDefinitionManagerFactory.getDefault().addDefinition(className, definition);
				
				FieldEditor editor = new FieldEditor(target, anno);
				target.getDeclaringClass().instrument(editor);
			}
		} catch (Exception e) {
			throw new IllegalClassFormatException(e.getMessage());
		}
	}
	

	/**
	 * 字段编辑器
	 * @author huangkaihui
	 *
	 */
	public class FieldEditor extends ExprEditor {

		private CtField field;
		private ImportService anno;
		
		public FieldEditor(CtField field, ImportService anno) {
			this.field = field;
			this.anno = anno;
		}
		
		/* (non-Javadoc)
		 * @see javassist.expr.ExprEditor#edit(javassist.expr.FieldAccess)
		 */
		@Override
		public void edit(FieldAccess f) throws CannotCompileException {
			try{
				if (f.getField() != this.field) return;
				
				//写方法退出
				if (f.isWriter()) return;
				String fName = f.getFieldName();
				String fieldName = "this." + fName;
			
			
				String serviceType = anno.serviceType() == Void.class ?
						"$type" : 
							anno.serviceType().getName() + ".class";
				String alias = anno.alias().equals("") ?
						"null" : "\"" + anno.alias() + "\"";
				String allowNull = anno.allowNull() ? "true" : "false";
				
				StringBuffer buff = new StringBuffer();
				buff.append("{\n")
					.append("\t").append("if (").append(fieldName).append(" == null){").append("\n")
					.append("\t\t").append(fieldName).append("= ($r)").append("\n")
					.append("\t\t\t").append(MassyUtils.class.getName())
						.append(".getService(")
						.append(serviceType).append(",")
						.append(alias).append(",")
						.append(allowNull)
						.append(");")
					.append("\n");
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
	}
}
