/**
 * 
 */
package org.smarabbit.massy.support;

import org.smarabbit.massy.Descriptor;
import org.smarabbit.massy.Registration;

/**
 * 提供获取和设置{@link Descriptor}的抽象注册凭据
 * 
 * @author huangkh
 *
 */
public abstract class AbstractRegistration implements Registration {

	private long Id;
	private Descriptor descriptor;
	
	/**
	 * 
	 */
	public AbstractRegistration(long Id) {
		this.Id = Id;
	}
	
	/* (non-Javadoc)
	 * @see org.smarabbit.massy.Registration#getId()
	 */
	@Override
	public long getId() {
		return this.Id;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.Descriptable#getDescriptor()
	 */
	@Override
	public Descriptor getDescriptor() {
		return this.descriptor;
	}

	protected void setDescriptor(Descriptor descriptor){
		this.descriptor = descriptor;
	}

}
