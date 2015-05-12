/**
 * 
 */
package org.smarabbit.massy.instrumentation;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
	private static final String FILENAME = "massy-instrumentation-"  + Environment.getDefault().getMassyVersion() + ".jar";;
	
	private static URLClassLoader LOADER = null;
	private static Class<?> VMCLASS ;
	
	
	
	/**
	 * 附加 agent jar.
	 * @param agentJarPath
	 * @throws IOException 
	 */
	public static void attachAgentJar() throws IOException{
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
	 * @throws IOException 
	 */
	protected static String getAgentJarPath() throws IOException{
		String tmpDir = Environment.getDefault().getTmpDir();
		if (!tmpDir.endsWith(Environment.getDefault().getFileSparator())){
			tmpDir = tmpDir + Environment.getDefault().getFileSparator();
		}
		File target = new File(tmpDir + FILENAME);
		if (target.exists()){
			return target.getAbsolutePath();
		}
		
		String result = Environment.getDefault().getLibDir();
		if (result != null){
			if (!result.endsWith(Environment.getDefault().getFileSparator())){
				result = result + Environment.getDefault().getFileSparator();
			}
			result = result + FILENAME;
		}else{
			Class<?> clazz = InstrumentationAgent.class;
			String path = clazz.getProtectionDomain().getCodeSource().getLocation().getFile();
			try {
				
				path = java.net.URLDecoder.decode(path, "UTF-8");
				if (path.endsWith(".jar")){
					File source = new File(path);
					//复制文件
					copyFile(source, target);
					target.deleteOnExit();
					return target.getAbsolutePath();
				}
			} catch (java.io.UnsupportedEncodingException ex) {
				
			}
		}
		
		return result;
	}
	
	private static void copyFile(File sourceFile, File targetFile) throws IOException {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			// 新建文件输入流并对它进行缓冲
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

			// 新建文件输出流并对它进行缓冲
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
		} finally {
			// 关闭流
			if (inBuff != null)
				inBuff.close();
			if (outBuff != null)
				outBuff.close();
		}
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
