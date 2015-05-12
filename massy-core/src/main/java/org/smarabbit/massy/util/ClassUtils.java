/**
 * 
 */
package org.smarabbit.massy.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 类工具，提供查找类，反射构建实例等方法
 * @author huangkaihui
 *
 */
public abstract class ClassUtils {

	/**
	 * 按类型名称查找类
	 * @param className 类型名称
	 * @return {@link Class}
	 * @throws ClassNotFoundException
	 */
	public static Class<?> forName(String className) throws ClassNotFoundException{
		return forName(className, null);
	}
	
	/**
	 * 按类型名称查找类
	 * @param className 类型名称
	 * @param cl {@link ClassLoader}
	 * @return {@link Class}
	 * @throws ClassNotFoundException
	 */
	public static Class<?> forName(String className, ClassLoader cl) throws ClassNotFoundException{
		if (cl == null){
			cl = Thread.currentThread().getContextClassLoader();
		}
		
		return cl.loadClass(className);
	}
	/**
	 * 调用无参构造函数实例化
	 * @param clazz
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<T> clazz)
			throws InstantiationException,
			IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, SecurityException{
		
		Constructor<?> cstr = clazz.getDeclaredConstructor(new Class<?>[0]);
		if (!cstr.isAccessible()){
			cstr.setAccessible(true);
		}
		return (T) cstr.newInstance(new Object[0]);
	}
	
	/**
	 * 调用有参构造函数实例化类
	 * @param clazz
	 * @param args
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	@SuppressWarnings("unchecked")
	public static <T> T instantiate(Class<T> clazz, Object... args)
			throws InstantiationException,
			IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
			if (args.length==0) {
				return newInstance(clazz);
			}
			Class<?>[] types = new Class<?>[args.length];
			for (int i=0; i<args.length; i++) {
				types[i] = args.getClass();
			}
			for (Constructor<?> c : clazz.getConstructors()) {
				Class<?>[] paramTypes = c.getParameterTypes();
				if (paramTypes.length!=args.length) {
					continue;
				}
				boolean foundCtr = true;
				for (int i=0; i<paramTypes.length; i++) {
					if (!paramTypes[i].isAssignableFrom(types[i])) {
						foundCtr = false;
						break;
					}
				}
				if (foundCtr) {
					if (!c.isAccessible()){
						c.setAccessible(true);
					}
					return (T)c.newInstance(args);
				}
			}
			throw new IllegalArgumentException(
				"Unable to find suitable constructor for "+clazz.getName());
		}
}
