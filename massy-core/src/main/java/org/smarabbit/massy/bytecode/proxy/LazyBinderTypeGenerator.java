/**
 * 
 */
package org.smarabbit.massy.bytecode.proxy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javassist.ClassClassPath;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smarabbit.massy.annotation.support.LazyBindHandlerDefinition;
import org.smarabbit.massy.lazyload.AbstractLazyBinder;
import org.smarabbit.massy.lazyload.LazyBinder;
import org.smarabbit.massy.util.Asserts;

/**
 * {@link LazyBinder}类型生成器
 * @author huangkaihui
 *
 */
public class LazyBinderTypeGenerator extends AbstractTypeGenerator<LazyBindHandlerDefinition> {

	private static final Logger logger = 
			LoggerFactory.getLogger(LazyBinderTypeGenerator.class);
	private Map<LazyBindHandlerDefinition, Class<? extends LazyBinder<?>>> classMap =
			new ConcurrentHashMap<LazyBindHandlerDefinition, Class<? extends LazyBinder<?>>>();
	
	/**
	 * 
	 */
	public LazyBinderTypeGenerator() {
		ClassClassPath classPath = new ClassClassPath(AbstractLazyBinder.class);
		pool.insertClassPath(classPath);
	}


	@Override
	public Class<?> generate(LazyBindHandlerDefinition definition)  throws Exception{
		Asserts.argumentNotNull(definition, "definition");
		Class<? extends LazyBinder<?>> result = classMap.get(definition);
		if (result == null){
			String methodName = definition.getMethodName();
			String paramTypeName = definition.getParamTypeName();
			String handlerClassName = definition.getDeclaringTypeName();
			
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			Class<?> handlerClass = loader.loadClass(handlerClassName);
			Class<?> paramType = loader.loadClass(paramTypeName);
			
			String packageName = handlerClass.getPackage().getName();
			String className = handlerClass.getSimpleName() + "$" +  definition.getMethodName() + "$" +
					paramType.getSimpleName() ;
			result = this.genericNewClass(packageName, className, methodName, handlerClassName, paramTypeName);
			this.classMap.put(definition, result);
		
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
						
			cc.getClassPool().importPackage(LazyBindHandlerDefinition.class.getPackage().getName());
			//构造方法
			StringBuffer buffer = new StringBuffer();
			buffer.append("public ").append(className).append("(Object handler, LazyBindHandlerDefinition definition){\r\n")
				.append("\tsuper(handler, definition);\r\n")

				.append("}");
			if (logger.isTraceEnabled()){
				logger.trace("add LazyBinder class constractor:\r\n" + buffer.toString());
			}
			CtConstructor m = CtNewConstructor.make(buffer.toString(),cc);
			cc.addConstructor(m);
			
			buffer.setLength(0);
			//doGetValue方法
			buffer.append("protected Object doGetValue(Object declaringObject) {\r\n")
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
