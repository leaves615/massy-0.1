/**
 * 
 */
package org.smarabbit.massy.model;

import org.smarabbit.massy.MassyUtils;


/**
 * 匿名用户
 * @author huangkaihui
 *
 */
public class AnonymousUser implements My {

	/**
	 * 
	 */
	public AnonymousUser() {
		
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.useradmin.CurrentUser#getId()
	 */
	@Override
	public String getId() {
		return "anonymous";
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.useradmin.CurrentUser#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return "游客";
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.useradmin.CurrentUser#isAuthenticated()
	 */
	@Override
	public boolean isAuthenticated() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.model.My#runAs(java.lang.Class)
	 */
	@Override
	public <A extends Actable> A runAs(Class<A> actorType) {
		return MassyUtils.adapt(this, actorType);
	}

}
