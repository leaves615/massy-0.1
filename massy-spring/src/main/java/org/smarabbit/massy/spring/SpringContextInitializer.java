/**
 * 
 */
package org.smarabbit.massy.spring;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smarabbit.massy.Constants;
import org.smarabbit.massy.MassyContext;
import org.smarabbit.massy.annotation.Order;
import org.smarabbit.massy.launch.MassyContextInitializer;
import org.smarabbit.massy.launch.MassyContextInitializerChain;
import org.smarabbit.massy.launch.MassyLaunchException;
import org.springframework.context.ApplicationContext;
import org.smarabbit.massy.spring.support.MassyResourceFinder;
import org.smarabbit.massy.spring.support.MassyResourceFinderFactroy;
import org.smarabbit.massy.support.Ordered;

/**
 * Spring配置文件初始化器加载除Web应用外的其他Spring配置文件。
 * <br>
 * 可以在初始化参数中设置{@link Constants#CONFIGFILE_PATTERNS}来增加和调整
 * 所需加载的Spring配置文件和加载顺序。
 * 
 * @author huangkaihui
 *
 */
@Order(Ordered.LOWEST_PRECEDENCE)
public class SpringContextInitializer implements MassyContextInitializer {
	
	private static final Logger logger =
			LoggerFactory.getLogger(SpringContextInitializer.class);
	
	//默认的配置文件路径
	private static final String DEFAULT_PATH = "classpath*:/META-INF/massy/context";
	
	//classpath前缀
	private static final String PERFIX_CLASSPATH = "classpath";
		
	//默认的配置文件
	private static final String DEFAULT_LOCATIONPATTERNS = 
							"*-dbcontext.xml," +
							"*-framework.xml," +
							"*-component.xml," +
							"*-context.xml";
	
	private static final String DEFAULT_NAME = "${default}";

	/**
	 * 
	 */
	public SpringContextInitializer() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.launch.MassyContextInitializer#onInit(org.smarabbit.massy.MassyContext, java.util.Map, org.smarabbit.massy.launch.MassyContextInitializerChain)
	 */
	@Override
	public void onInit(MassyContext context, Map<String, Object> initParams,
			MassyContextInitializerChain chain) throws MassyLaunchException {
		try{
			Map<String, Collection<MassyResource>> resources = this.findResources(initParams);
			List<ApplicationContext> list = new ArrayList<ApplicationContext>();
			
			MultithreadingBatchLoader loader = new MultithreadingBatchLoader(list);
			
			Iterator<Entry<String, Collection<MassyResource>>> it =
					resources.entrySet().iterator();
			while (it.hasNext()){
				Entry<String, Collection<MassyResource>> entry = it.next();
				loader.setResources(entry.getValue());
				loader.load();	
				
				if (loader.getException() != null){
					throw loader.getException();
				}
			}
					
		}catch(FileNotFoundException e){
			if (logger.isWarnEnabled()){
				logger.warn(e.getMessage());
			}
		}catch(Exception e){
			throw new MassyLaunchException(e.getMessage(), e);
		}
		
		chain.proceed(context, initParams);
	}

	/**
	 * 查找Massy的Spring配置资源
	 * @param initParams 
	 * 			{@link Map},初始化参数
	 * @return
	 * 			{@link MassyResource}数组
	 * @throws IOException
	 * 			IO存取异常
	 */
	protected Map<String, Collection<MassyResource>> findResources(Map<String, Object> initParams) throws IOException{
		String locationPatterns = this.getLocationPatterns(initParams);
		String[] patterns = locationPatterns.split(",");
		
		//对简写的路径，按默认路径补充
		int size = patterns.length;
		for (int i=0; i<size; i++){
			String pattern = patterns[i];
			if (!pattern.startsWith(PERFIX_CLASSPATH)){
				patterns[i]= pattern.startsWith("/")? 
						DEFAULT_PATH + pattern :
							DEFAULT_PATH + "/" + pattern;
			}
		}
		
		MassyResourceFinder finder = MassyResourceFinderFactroy.create();
		return finder.find(patterns);
	}
	
	/**
	 * 获取配置文件路径模式
	 * @param initParams 
	 * 		初始化参数
	 * @return 
	 * 		字符串
	 */
	protected String getLocationPatterns(Map<String, Object> initParams){
		String result =(String)initParams.get(Constants.CONFIGFILE_PATTERNS);
		if (result == null){
			result = DEFAULT_LOCATIONPATTERNS;
		}else{
			result.replaceAll(DEFAULT_NAME, DEFAULT_LOCATIONPATTERNS);
		}
		return result;
	}
}
