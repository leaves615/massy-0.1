/**
 * 
 */
package org.smarabbit.massy.bytecode.proxy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javassist.ClassClassPath;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.Modifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smarabbit.massy.annotation.support.AbstractLazyBinder;
import org.smarabbit.massy.annotation.support.LazyBinder;
import org.smarabbit.massy.util.Asserts;

/**
 * {@link LazyBinder}类型生成器
 * @author huangkaihui
 *
 */
public class LazyBinderTypeGenerator extends AbstractTypeGenerator<CtMethod> {

	private static final Logger logger = 
			LoggerFactory.getLogger(LazyBinderTypeGenerator.class);
	private Map<CtMethod, Class<? extends LazyBinder<?>>> classMap =
			new ConcurrentHashMap<CtMethod, Class<? extends LazyBinder<?>>>();
	
	/**
	 * 
	 */
	public LazyBinderTypeGenerator() {
		ClassClassPath classPath = new ClassClassPath(AbstractLazyBinder.class);
		pool.insertClassPath(classPath);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.bytecode.proxy.TypeGenerator#generate(java.lang.Class)
	 */
	@Override
	public Class<?> generate(CtMethod method)  throws Exception{
		Asserts.argumentNotNull(method, "method");
		Class<? extends LazyBinder<?>> result = classMap.get(method);
		if (result == null){
			String methodName = method.getName();
			String paramTypeName = method.getParameterTypes()[0].getName();
			String handlerClassName = method.getDeclaringClass().getName();
			
			String packageName = method.getDeclaringClass().getPackageName();
			String className = method.getDeclaringClass().getSimpleName() + "$" +  method.getName() + "$" +
					method.getParameterTypes()[0].getSimpleName() ;
			result = this.genericNewClass(packageName, className, methodName, handlerClassName, paramTypeName);
			this.classMap.put(method, result);
		
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	protected Class<? extends LazyBinder<?>> genericNewClass(String packageName, String className, 
			String methodName, String handlerTypeName, String paramTypeName){
		try{
			CtClass cc = pool.makeClass(packageName + "." + className);
		
			CtClass superCc = pool.get(AbstractLazyBinder.class.getName());
			cc.setSuperclass(superCc);
			
			CtClass stringType = pool.get(String.class.getName());
			CtField f = new CtField(stringType, "methodName", cc);
			f.setModifiers(Modifier.PROTECTED);
			cc.addField(f);
			
			//构造方法
			StringBuffer buffer = new StringBuffer();
			buffer.append("public ").append(className).append("(Object handler, String methodName){\r\n")
				.append("\tsuper(handler);\r\n")
				.append("\tthis.methodName = methodName; \r\n")
				.append("}");
			if (logger.isTraceEnabled()){
				logger.trace("add LazyBinder class constractor:\r\n" + buffer.toString());
			}
			CtConstructor m = CtNewConstructor.make(buffer.toString(),cc);
			cc.addConstructor(m);
			
			buffer.setLength(0);
			//getValue方法
			buffer.append("public Object getValue(Object declaringObject) {\r\n")
				.append("\treturn ")
					.append("((").append(handlerTypeName).append(")")
					.append("this.getHandler()).")
					.append(methodName)
				.append("((").append(paramTypeName).append(")declaringObject);\r\n")
				.append("}");
			if (logger.isTraceEnabled()){
				logger.trace("add LazyBinder class method:\r\n" + buffer.toString());
			}
			CtMethod method = CtNewMethod.make(buffer.toString(), cc);
			cc.addMethod(method);
			
			return cc.toClass();
		}catch(Exception e){
			if (logger.isErrorEnabled()){
				logger.error(e.getMessage(), e);
			}
			return null;
		}
		
	}

}
