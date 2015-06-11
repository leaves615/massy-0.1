/**
 * 
 */
package org.smarabbit.massy.launch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.smarabbit.massy.Constants;
import org.smarabbit.massy.MassyContext;
import org.smarabbit.massy.instrumentation.InstrumentationAgentInitializer;
import org.smarabbit.massy.support.ObjectOrderUtils;
import org.smarabbit.massy.util.Asserts;
import org.smarabbit.massy.util.LogUtils;
import org.smarabbit.massy.util.ServiceLoaderUtils;

/**
 * @author huangkaihui
 *
 */
public class MassyContextInitializerProcessor {

	private final ClassLoader loader;
	/**
	 * 
	 */
	public MassyContextInitializerProcessor() {
		this(Thread.currentThread().getContextClassLoader());
	}
	
	public MassyContextInitializerProcessor(ClassLoader classLoader){
		Asserts.argumentNotNull(classLoader, "classLoader");
		this.loader = classLoader;
	}
	
	/**
	 * 初始化
	 * @param context
	 * @param initParams
	 * @throws MassyLaunchException
	 */
	protected void doInit(MassyContext context, Map<String, Object> initParams)
			throws MassyLaunchException {
		//前置初始化器
		ArrayList<MassyContextInitializer> initializers = 
				this.getPrependInitializer(initParams);
		//添加增强初始化器
		initializers.add(0, new InstrumentationAgentInitializer());
		
		//添加系统级定义的初始化器
		initializers.addAll(this.loadAndSortInitializer());
		
		Chain chain = new Chain(initializers.iterator());
		//链调度，完成初始化过程
		chain.proceed(context, initParams);
		
		if (LogUtils.isInfoEnabled()){
			LogUtils.info("Massy Framework Started.");
		}
	}

	/**
	 * 获取前置初始化器
	 * @param initParams 
	 * 		参数
	 * @return
	 * 		{@link List}集合，不能返回null.
	 * @throws MassyLaunchException
	 */
	@SuppressWarnings("unchecked")
	protected ArrayList<MassyContextInitializer> getPrependInitializer(Map<String, Object> initParams)
		throws MassyLaunchException{
		ArrayList<MassyContextInitializer> result =  new ArrayList<MassyContextInitializer>();
		if (initParams.containsKey(Constants.PREPEND_INITIALIZERS)){
			List<MassyContextInitializer> tmp = (List<MassyContextInitializer>)initParams.get(Constants.PREPEND_INITIALIZERS) ;
			result.addAll(tmp);
		}
								
		//移除前置初始化器
		initParams.remove(Constants.PREPEND_INITIALIZERS);
		return result;
	}
	
	/**
	 * 加载MassyContextInitializer服务，并排序
	 * @return
	 */
	protected List<MassyContextInitializer> loadAndSortInitializer(){
		List<MassyContextInitializer> result =
				ServiceLoaderUtils.loadServices(MassyContextInitializer.class, this.loader);
		ObjectOrderUtils.sort(result);
		return result;
	}
	
	/**
	 * 调度链
	 * @author huangkh
	 *
	 */
	private class Chain implements MassyContextInitializerChain {

		private final MassyContextInitializer current;
		private final Chain next;
		
		/**
		 * 
		 */
		public Chain(Iterator<MassyContextInitializer> it) {
			if (it.hasNext()){
				this.current = it.next();
				this.next = new Chain(it);
			}else{
				this.current = null;
				this.next = null;
			}
		}

		/* (non-Javadoc)
		 * @see org.smarabbit.massy.launch.MassyContextInitializerChain#proceed(org.smarabbit.massy.MassyContext, java.util.Map)
		 */
		@Override
		public void proceed(MassyContext context, Map<String, Object> initParams)
				throws MassyLaunchException {
			if (this.current != null){
				this.current.onInit(context, initParams, next);
			}
		}

	}
}
