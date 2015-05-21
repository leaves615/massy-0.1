/**
 * 
 */
package org.smarabbit.massy.spring;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.smarabbit.massy.Constants;
import org.smarabbit.massy.MassyUtils;
import org.smarabbit.massy.service.ServiceRepository;
import org.smarabbit.massy.spring.context.MassyXmlApplicationContext;
import org.smarabbit.massy.util.Asserts;
import org.springframework.context.ApplicationContext;

/**
 * 多线程加载Spring配置文件
 * @author huangkaihui
 *
 */
public class MultithreadingBatchLoader {

	private static final String CONTAINER = "spring";
	private final Collection<ApplicationContext> applicationContexts;
	private Collection<MassyResource> resources;
	
	private ServiceRepository serviceRepo;
	
	private Exception exception;
	/**
	 * 
	 */
	public MultithreadingBatchLoader(Collection<ApplicationContext> applicationContexts) {
		Asserts.argumentNotNull(applicationContexts, "applicationContexts");
		
		this.serviceRepo = MassyUtils.getDefaultContext().getService(ServiceRepository.class);
		this.applicationContexts = applicationContexts;
		
	}

	/**
	 * 批量加载
	 * @throws Exception
	 */
	public void load() throws Exception{
		Asserts.ensureFieldInited(this.resources, "resources");
		CountDownLatch latch = new CountDownLatch(resources.size());
		
		Iterator<MassyResource> it = resources.iterator();
		while(it.hasNext()){		
			new Thread(new Loader(it.next(), latch)).start();
		}
		
		latch.await();
	}
	
	/**
	 * @return the resources
	 */
	public Collection<MassyResource> getResources() {
		return resources;
	}

	/**
	 * @param resources the resources to set
	 */
	public void setResources(Collection<MassyResource> resources) {
		Asserts.argumentNotEmpty(resources, "resources");
		this.resources = resources;
	}

	/**
	 * @return the exception
	 */
	public Exception getException() {
		return exception;
	}

	private class Loader implements Runnable{

		private MassyResource resource;
		private CountDownLatch latch;
		
		public Loader(MassyResource resource, CountDownLatch latch){
			this.resource = resource;
			this.latch = latch;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			try{
				MassyXmlApplicationContext applicationContext =
						new MassyXmlApplicationContext(resource);
				applicationContext.refresh();
				
				Map<String, Object> props = new HashMap<String, Object>();
				props.put(Constants.SERVICE_CONTAINER, CONTAINER);
				props.put(Constants.SERVICE_CONFIGFILE, MassyResource.getConfigurationFile(resource));
	
				MultithreadingBatchLoader.this.serviceRepo.register(
						ApplicationContext.class, applicationContext, props);
				applicationContexts.add(applicationContext);
			} catch (Exception e) {
				if (exception == null)
					exception = e;
			}finally{
				latch.countDown();
			}
		}
	}
}
