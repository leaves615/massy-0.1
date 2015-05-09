/**
 * 
 */
package org.smarabbit.massy.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author huangkh
 *
 */
public abstract class LogUtils {

	private static final Logger logger =
			LoggerFactory.getLogger("massy.framework");
	
	public static boolean isInfoEnabled(){
		return logger.isInfoEnabled();
	}
	
	public static void info(String message){
		logger.info(message);
	}
	
	public static boolean isDebugEnabled(){
		return logger.isDebugEnabled();
	}
	
	public static void debug(String message){
		logger.debug(message);
	}
	
	public static boolean isTraceEnabled(){
		return logger.isTraceEnabled();
	}
	
	public static void trace(String message){
		logger.trace(message);
	}
	
	public static boolean isWarnEnabled(){
		return logger.isWarnEnabled();
	}
	
	public static void warn(String message){
		logger.warn(message);
	}
	
}
