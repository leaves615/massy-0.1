/**
 * 
 */
package org.smarabbit.massy.instrumentation;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import org.smarabbit.massy.Environment;
import org.smarabbit.massy.launch.MassyLaunchException;
import org.smarabbit.massy.util.LogUtils;

/**
 * Agent jar 加载器，提供动态附加massy-instrumentation.jar的实现
 * @author huangkaihui
 *
 */
abstract class AgentJarLoader {

	private static final String VM_CLASSNAME = "com.sun.tools.attach.VirtualMachine";
	private static final String FILENAME = "massy-instrumentation-";
	
	private static URLClassLoader LOADER = null;
	private static Class<?> VMCLASS ;
	
	
	
	/**
	 * 附加 agent jar.
	 * @param agentJarPath
	 */
	public static void attachAgentJar(){
		load();
		
		if (VMCLASS == null){
			if (LogUtils.isWarnEnabled()){
				LogUtils.warn("cannot found " + VM_CLASSNAME + ".class.");
			}
			return;
		}
					
		String agentJarPath = getAgentJarPath();
		
		
		if (LogUtils.isInfoEnabled()){
			LogUtils.info("load agent: " + agentJarPath + ".");
		}
		
		try{
			RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
			String pid = runtime.getName();
			if (pid.indexOf("@") != -1)
				pid = pid.substring(0, pid.indexOf("@"));
			
			Method method = VMCLASS.getMethod("attach", new Class[] { String.class });
			Object vm = method.invoke(null, new Object[]{pid});
			
			Method loadAgentMethod = vm.getClass().getMethod("loadAgent", new Class[] { String.class });
			if (loadAgentMethod != null){
				loadAgentMethod.invoke(vm, new Object[] { agentJarPath });
			}
		}catch(Exception e){
			throw new MassyLaunchException(e.getMessage(), e);
		}
	}
	
	/**
	 * 获取agent jar路径
	 * @return
	 */
	protected static String getAgentJarPath(){
		String result = Environment.getDefault().getLibDir();
		if (result != null){
			if (!result.endsWith(Environment.getDefault().getFileSparator())){
				result = result + Environment.getDefault().getFileSparator();
			}
			result = result + FILENAME + "-" + Environment.getDefault().getMassyVersion() + ".jar";
		}else{
			Class<?> clazz = InstrumentationAgent.class;
			String path = clazz.getProtectionDomain().getCodeSource().getLocation().getFile();
			try {
				result = java.net.URLDecoder.decode(path, "UTF-8");
			} catch (java.io.UnsupportedEncodingException ex) {
				
			}
		}
		
		return result;
	}
	
	/**
	 * 动态加载Tools.jar, 为下一步附加agent jar做准备
	 */
	protected synchronized static void load(){
		if (LOADER != null) return;
		
		String javaHome = Environment.getDefault().getJavaHome();
		if (javaHome == null){
			throw new MassyLaunchException("JAVA_HOME not setting.");
		}
		
		String separator = Environment.getDefault().getFileSparator();
		String jre = separator + "jre";
		if (javaHome.endsWith(jre)){
			javaHome = javaHome.substring(0, javaHome.length() - 3);
		}
		
		String path = javaHome +   "lib" + separator + "tools.jar";
		
		File file = new File(path);
		if (!file.exists()){
			throw new MassyLaunchException("cannot found tools.jar: " + path + ".");
		}
		
		URL url = null;
		try {
			url = file.toURI().toURL();
			LOADER = new URLClassLoader(new URL[]{url}, Thread.currentThread().getContextClassLoader());
			VMCLASS =  LOADER.loadClass(VM_CLASSNAME);
			
		} catch (Exception e) {
			throw new MassyLaunchException(e.getMessage(), e);
		}		
	}
}
