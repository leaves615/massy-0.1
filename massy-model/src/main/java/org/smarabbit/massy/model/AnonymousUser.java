/**
 * 
 */
package org.smarabbit.massy.model;


/**
 * @author huangkaihui
 *
 */
public class AnonymousUser implements CurrentUser {

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

}
