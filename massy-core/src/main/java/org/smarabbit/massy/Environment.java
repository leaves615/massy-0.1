/**
 * 
 */
package org.smarabbit.massy;

/**
 * 运行环境
 * @author huangkaihui
 *
 */
public class Environment {

	private static final Environment INSTANCE = new Environment();
	
	private String javaHome;
	private String fileSeparator;
	
	private String version = "0.1";
	private String libDir;
		
	private Environment(){
		this.javaHome = System.getProperty("java.home");
		this.fileSeparator = System.getProperty("file.separator");
	}
	
	public String getJavaHome(){
		return this.javaHome;
	}
	
	public String getFileSparator(){
		return this.fileSeparator;
	}
	
	public String getMassyVersion(){
		return this.version;
	}
	
	public String getLibDir() {
		return libDir;
	}

	public void setLibDir(String libDir) {
		this.libDir = libDir;
	}

	public static Environment getDefault(){
		return INSTANCE;
	}
}
