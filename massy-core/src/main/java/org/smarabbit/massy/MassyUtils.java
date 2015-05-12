/**
 * 
 */
package org.smarabbit.massy;

import org.smarabbit.massy.launch.DefaultMassyLauncher;
import org.smarabbit.massy.launch.MassyLaunchException;
import org.smarabbit.massy.launch.MassyLauncher;
import org.smarabbit.massy.util.ServiceLoaderUtils;

/**
 * @author huangkaihui
 *
 */
public abstract class MassyUtils {

	private static MassyContext INSTANCE;
	
	/**
	 * 获取{@link MassyContext}
	 * @return
	 * 		{@link MassyContext}
	 */
	public static MassyContext getDefaultContext(){
		if (INSTANCE == null){
			throw new MassyLaunchException("massy framework cannot launch, please do it.");
		}
		return INSTANCE;
	}
	
	/**
	 * 设置{@link MassyContext}
	 * @param context {@link MassyContext}
	 */
	public static void setDefaultContext(MassyContext context){
		if (INSTANCE == null){
			INSTANCE = context;
		}
	}
	
	/**
	 * 创建{@link MassyLauncher}
	 * @return
	 * 		{@link MassyLauncher},不能返回null.
	 */
	public static MassyLauncher create(){
		MassyLauncher result = ServiceLoaderUtils.loadFirstService(MassyLauncher.class);
		if (result == null){
			result = new DefaultMassyLauncher();
		}
		return result;
	}
}
