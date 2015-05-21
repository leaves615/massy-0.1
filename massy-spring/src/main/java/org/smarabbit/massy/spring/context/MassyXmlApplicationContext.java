/**
 * 
 */
package org.smarabbit.massy.spring.context;

import org.smarabbit.massy.spring.MassyApplicationContext;
import org.smarabbit.massy.spring.MassyResource;
import org.smarabbit.massy.util.Asserts;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.core.io.Resource;

/**
 * 扩展{@link AbstractXmlApplication},实现{@link MassyApplicationContext}
 * @author huangkh
 *
 */
public class MassyXmlApplicationContext extends AbstractXmlApplicationContext 
	implements MassyApplicationContext{

	private MassyResource massyResource;
	
	/**
	 * 
	 */
	public MassyXmlApplicationContext(MassyResource massyResource) {
		this(null, massyResource);
	}

	/**
	 * @param parent
	 */
	public MassyXmlApplicationContext(ApplicationContext parent, MassyResource massyResource) {
		super(parent);
		Asserts.argumentNotNull(massyResource, "massyResource");
		this.massyResource = massyResource;
	}

	@Override
	public MassyResource getMassyResource() {
		return this.massyResource;
	}

	/* (non-Javadoc)
	 * @see org.springframework.context.support.AbstractXmlApplicationContext#getConfigResources()
	 */
	@Override
	protected Resource[] getConfigResources() {
		return this.massyResource.getConfigResources();
	}	
	
	
}
