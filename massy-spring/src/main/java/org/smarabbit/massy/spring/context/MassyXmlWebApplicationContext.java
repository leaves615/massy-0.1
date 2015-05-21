/**
 * 
 */
package org.smarabbit.massy.spring.context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.smarabbit.massy.spring.MassyApplicationContext;
import org.smarabbit.massy.spring.MassyResource;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * @author huangkh
 *
 */
public class MassyXmlWebApplicationContext extends XmlWebApplicationContext 
	implements MassyApplicationContext{

	/** Default config location for the root context */
	public static final String DEFAULT_CONFIG_LOCATION = 
			"classpath*:/META-INF/massy/context/webcontext.xml";
	
	/** Default prefix for building a config location for a namespace */
	public static final String DEFAULT_CONFIG_LOCATION_PREFIX = "classpath:/META-INF/massy/context/";
	
	/** Default suffix for building a config location for a namespace */
	public static final String DEFAULT_CONFIG_LOCATION_SUFFIX = ".xml";
	
	private MassyResource massyResource;
	
	/**
	 * 
	 */
	public MassyXmlWebApplicationContext() {
		this.massyResource = new MassyResource("webcontext.xml");
	}

	@Override
	public MassyResource getMassyResource() {
		return this.massyResource;
	}
	
	
	/* (non-Javadoc)
	 * @see org.springframework.web.context.support.XmlWebApplicationContext#loadBeanDefinitions(org.springframework.beans.factory.xml.XmlBeanDefinitionReader)
	 */
	@Override
	protected void loadBeanDefinitions(XmlBeanDefinitionReader reader)
			throws IOException {
		String[] configLocations = getConfigLocations();
		List<Resource> resources = new ArrayList<Resource>();
		if (configLocations != null) {
			for (String configLocation : configLocations) {
				reader.loadBeanDefinitions(configLocation);
				resources.add(this.getResource(configLocation));
			}
		}
		this.massyResource.setConfigResources(resources.toArray(new Resource[resources.size()]));
	}

	@Override
	protected String[] getDefaultConfigLocations() {
		if (getNamespace() != null) {
			return new String[] {DEFAULT_CONFIG_LOCATION_PREFIX + getNamespace() + DEFAULT_CONFIG_LOCATION_SUFFIX};
		}
		else {
			return new String[] {DEFAULT_CONFIG_LOCATION};
		}
	}

}
