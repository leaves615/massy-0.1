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
import org.smarabbit.massy.util.Asserts;

/**
 * 动态代理类型生成器
 * @author huangkaihui
 *
 */
public class DynamicProxyTypeGenerator extends AbstractTypeGenerator<Class<?>> {

	private final static Logger logger = LoggerFactory.getLogger(DynamicProxyTypeGenerator.class);
	
	private Map<Class<?>, Class<?>> classMap =
			new ConcurrentHashMap<Class<?>, Class<?>>();
	
	public DynamicProxyTypeGenerator() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.bytecode.proxy.TypeGenerator#generate(java.lang.Object)
	 */
	@Override
	public Class<?> generate(Class<?> source) throws Exception {
		Asserts.argumentNotNull(source, "source");
		Class<?> result =  this.classMap.get(source);
		if (result == null){
			ClassClassPath classPath = new ClassClassPath(source);
			pool.insertClassPath(classPath);
			
			result = this.doGenerate(source);
			this.classMap.put(source, result);
		}
		return result;
	}

	protected Class<?> doGenerate(Class<?> source) throws Exception{
		//定义delegate字段
		CtClass delegateType = pool.get(source.getName());
		//创建CtClass
		CtClass cc = this.createNewCtClass(source, delegateType);
					
		//方法处理
		if (delegateType.isInterface()){
			//接口代理模式下
			this.generatorInterfaces(cc, delegateType);
		}else{
			//类代理模式下
			CtClass parent = delegateType;
			while (!parent.getName().equals(Object.class.getName())){
				this.generatorInterfaces(cc, parent);	
				parent = parent.getSuperclass();
			}	
		}
		
		return cc.toClass();
	}
	
	/**
	 * 创建CtClass
	 * @param source
	 * @return
	 */
	protected CtClass createNewCtClass(Class<?> source, CtClass delegateType)
		throws Exception{
		String packageName = source.getPackage().getName();
		String className = source.getSimpleName() + "$JavassistProxy";
		
		CtClass result = pool.makeClass(packageName + "." + className);
		if (!delegateType.isInterface()){
			result.setSuperclass(delegateType);
		}else{
			result.addInterface(delegateType);
		}
		
		CtField f = new CtField(delegateType, "delegate", result);
		f.setModifiers(Modifier.PROTECTED);
		result.addField(f);
		
		//构造方法
		StringBuffer buffer = new StringBuffer();
		buffer.append("public ").append(className)
			.append("(").append(source.getName()).append(" instance){\r\n")
			.append("\tthis.delegate= instance;\r\n")
			.append("}");
		if (logger.isTraceEnabled()){
			logger.trace("add  "+ result.getSimpleName() +" constractor:\r\n" + buffer.toString());
		}
		CtConstructor m = CtNewConstructor.make(buffer.toString(), result);
		result.addConstructor(m);
		
		return result;
	}
	
	protected void generatorInterfaces(CtClass cc, CtClass impType)
		throws Exception{
		this.generatorMethod(cc, impType);
		
		CtClass[] intfTypes = impType.getInterfaces();
		for (CtClass intfType: intfTypes){
			generatorInterfaces(cc, intfType);
		}
	}
	
	/**
	 * 生成方法
	 * @param cc
	 * @param sourceType
	 * @throws Exception
	 */
	protected void generatorMethod(CtClass cc, CtClass sourceType)
		throws Exception{
		CtClass voidType = pool.get(void.class.getName());
		CtMethod[] methods = sourceType.getDeclaredMethods();
		
		StringBuffer buffer = new StringBuffer();
		for (CtMethod method: methods){
					
			//非public方法，跳过
			if (!Modifier.isPublic(method.getModifiers())){
				continue;
			}
			
			//重名方法，跳过
			try{
				if (cc.getDeclaredMethod(
						method.getName(), method.getParameterTypes()) != null){
					continue;
				}
			}catch(Exception e){
				//无重名时，抛出异常，不处理
			}
			
			buffer.append("public ")
				.append(method.getReturnType().getName()).append(" ")
				.append(method.getName()).append("(");
			CtClass[] paramTypes = method.getParameterTypes();
			int size = paramTypes.length;
			for (int i=0; i<size; i++){
				//添加参数
				buffer.append(paramTypes[i].getName())
					.append(" arg").append(i);
				if (i!=size-1){
					buffer.append(",");
				}
			}
			buffer.append("){\r\n")
				.append("\t");
			if (method.getReturnType() != voidType){
				buffer.append(method.getReturnType().getName())
					.append(" result = null;\r\n\t");
			}
			buffer.append("if (this.delegate != null) {\r\n\t");
			if (method.getReturnType() != voidType){
				buffer.append("\tresult = ");	
			}
			buffer.append("\tthis.delegate.").append(method.getName()).append("(");			
			for (int i=0; i<size; i++){
				buffer.append("arg").append(i);
				if (i!=size-1){
					buffer.append(",");
				}
			}
			buffer.append(");\r\n").append("\t}\r\n");
			if (method.getReturnType() != voidType){
				buffer.append("\treturn result;\r\n");
			}
			buffer.append("}");
			
			if (logger.isTraceEnabled()){
				logger.trace("add " + cc.getSimpleName() + " method:\r\n" + buffer.toString());
			}
			
			CtMethod newMethod = CtNewMethod.make(buffer.toString(), cc);
			cc.addMethod(newMethod);
			
			buffer.setLength(0);
		}
	}
}
