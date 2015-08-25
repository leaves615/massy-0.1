/**
 * 
 */
package org.smarabbit.massy.launch;

import org.smarabbit.massy.util.ServiceLoaderUtils;

/**
 * {@link MassyLauncher}工厂
 * 
 * @author huangkaihui
 *
 */
public abstract class MassyLauncherFactory {

	/**
	 * 创建{@link MassyLauncher}实例
	 * <br>
	 * 实例必须满足ServiceLoader加载规范 
	 * @return
	 * @throws MassyLaunchException
	 */
	public static MassyLauncher create() throws MassyLaunchException{
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		return create(loader);
	}
	
	/**
	 * 创建{@link MassyLauncher}实例
	 * <br>
	 * 实例必须满足ServiceLoader加载规范
	 * @param loader {@link ClassLoader},不能为null.
	 * @return
	 * @throws MassyLaunchException
	 */
	public static MassyLauncher create(ClassLoader loader) throws MassyLaunchException{
		MassyLauncher result = 
					ServiceLoaderUtils.loadFirstService(MassyLauncher.class, loader);
		if (result == null){
			throw new MassyLaunchException("cannot found MassyLauncher from ServiceLoader.");
		}
		return result;
	}
}
