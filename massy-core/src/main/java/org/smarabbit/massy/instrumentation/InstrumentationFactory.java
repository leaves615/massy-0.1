/**
 * 
 */
package org.smarabbit.massy.instrumentation;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;

/**
 * {@link Instrumentation}工厂，提供{@link Instrumentation}实例
 * @author huangkaihui
 *
 */
public abstract class InstrumentationFactory {

	private static volatile Instrumentation INSTANCE = null;
	private static final String AGENT_CLASSNAME = "org.smarabbit.massy.instrumentation.InstrumentationAgent";
	private static final String METHOD_NAME = "getInstrumentation";
	
	/**
	 * 获取{@link Instrumentation}实例
	 * @return
	 * @throws IOException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws ClassNotFoundException
	 * @throws URISyntaxException
	 */
	public static synchronized Instrumentation getInstrumentation()
		throws  IOException, NoSuchMethodException, IllegalAccessException,
		InvocationTargetException, ClassNotFoundException, URISyntaxException {
		
		if (INSTANCE != null) return INSTANCE;
		
		try{
			//尝试看看是否已经加载agent
			INSTANCE = tryGetInstrumentation();
		}catch(Exception e){
		
		}
		
		if (INSTANCE == null){		
			//附加agent jar.
			AgentJarLoader.attachAgentJar();
			//再次尝试
			INSTANCE = tryGetInstrumentation();
		}
		
		return INSTANCE;
	}
	
	/**
	 * 尝试获取{@link Instrumentation}接口
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	protected static Instrumentation tryGetInstrumentation() throws ClassNotFoundException, 
		SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		ClassLoader loader = ClassLoader.getSystemClassLoader();			
		Class<?> clazz = loader.loadClass(AGENT_CLASSNAME);
	
		Method method = clazz.getMethod(METHOD_NAME, new Class<?>[0]);
		return (Instrumentation)method.invoke(clazz, new Object[0]);
	}
}
