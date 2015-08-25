/**
 * 
 */
package org.smarabbit.massy.bytecode;

import java.io.ByteArrayInputStream;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.LoaderClassPath;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smarabbit.massy.instrumentation.InstrumentationSupportObject;

/**
 * Javassist类文件增强器
 * @author huangkaihui
 *
 */
public class JavassistClassFileTransformer extends InstrumentationSupportObject {

	private static final Logger logger =
			LoggerFactory.getLogger(JavassistClassFileTransformer.class);
	private List<String> classesToSkip = new ArrayList<String>();
	private TransformerHandlerManager mngr = new TransformerHandlerManager();
	/**
	 * 
	 */
	public JavassistClassFileTransformer() {
	}

	/* (non-Javadoc)
	 * @see java.lang.instrument.ClassFileTransformer#transform(java.lang.ClassLoader, java.lang.String, java.lang.Class, java.security.ProtectionDomain, byte[])
	 */
	@Override
	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {
		String enhanceClassName = className.replaceAll("/",".");
		if (this.isSkip(enhanceClassName)){
			//跳过
			return classfileBuffer;
		}
				
		try{
			// Create a Javassist CtClass from the bype code
			ClassPool pool = new ClassPool(true);
			if (loader != null){
				pool.insertClassPath(new LoaderClassPath(loader));
			
				ClassLoader parent = loader.getParent();
				if (parent != null){
					pool.appendClassPath(new LoaderClassPath(loader.getParent()));
				}
			}
            CtClass cc = pool.makeClass(new ByteArrayInputStream(classfileBuffer));
			
            // Only instrument unfrozen classes
            if(!cc.isFrozen() ){
            	            	
            	this.mngr.transform(cc);            	
        		if (logger.isTraceEnabled()){
        			logger.trace("transformer class: " + className + ".");
        		}
        		return cc.toBytecode();

            }
			
            return classfileBuffer;
		}catch(Exception e){
			String msg = "Transforming class: " + enhanceClassName + " failed.";
			if (logger.isErrorEnabled()){
				logger.error(msg, e);
			}
			throw new IllegalClassFormatException(e.getMessage());
		}
	}

	/**
	 * 初始化跳过的包名
	 */
	protected void initSkipPackage(){
		classesToSkip.add( "java." );
		classesToSkip.add( "javax." );
		classesToSkip.add( "org.springframework.");
		classesToSkip.add( "org.axonframework.");
		classesToSkip.add( "org.apache.");
        classesToSkip.add( "sun." );
        classesToSkip.add( "com.sun." );
        classesToSkip.add( "org.jdom" );
        classesToSkip.add( "org.apache." );
        classesToSkip.add( "org.hamcrest");
        classesToSkip.add( "org.junit");
        classesToSkip.add( "junit.");
        classesToSkip.add( "com.thoughtworks.");
        classesToSkip.add( "org.eclipse.");
	}
	
	protected boolean isSkip(String enhanceClassName){
				
		//检查是否需要跳过的类
		Iterator<String> it = classesToSkip.iterator();
		while (it.hasNext()){
			if (enhanceClassName.startsWith(it.next())){
				return true;
			}
		}
		
		return false;
	}
}
