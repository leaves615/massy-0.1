/**
 * 
 */
package org.smarabbit.massy.spring;

import org.smarabbit.massy.util.Asserts;
import org.springframework.core.io.Resource;

/**
 * Massy资源
 * @author root
 *
 */
public final class MassyResource{
		
	private static final Resource[] EMPTY = new Resource[0];
	
	private final String name;
	private Resource[] configResources;
	
	public MassyResource(String name){
		this(name, null);
	}
	
	public MassyResource(String name, Resource[] resource){
		Asserts.argumentNotNull(name, "name");
		this.name = name;
		this.configResources = resource;
	}
	
	/**
	 * 获取名称
	 * @return
	 * 		字符串，不能返回null.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * 资源数组
	 * @return
	 * 		{@link Resource}数组，无资源可以返回empty数组
	 */
	public Resource[] getConfigResources() {
		return this.configResources == null ?
							EMPTY : this.configResources;
	}
	
	/**
	 * 获取配置文件名，多个配置文件使用";"隔离
	 * @param massyResource {@link MassyResource}
	 * @return
	 * 			字符串
	 */
	public static String getConfigurationFile(MassyResource massyResource){
		Resource[] resources = massyResource.getConfigResources();
		StringBuffer sb = new StringBuffer();
		int size = resources.length;
		for (int i=0; i<size; i++){
			Resource res = resources[i];
			try{
				sb.append(res.getURI().getPath());
				if (i<size-1){
					sb.append(";");
				}
			}catch(Exception e){
				
			}
		}
		return sb.toString();
	}
	
	public void setConfigResources(Resource[] configResources) {
		this.configResources = configResources;
	}
}
