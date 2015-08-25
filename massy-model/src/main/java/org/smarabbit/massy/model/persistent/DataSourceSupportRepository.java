/**
 * 
 */
package org.smarabbit.massy.model.persistent;

import javax.sql.DataSource;

/**
 * @author huangkaihui
 *
 */
public abstract class DataSourceSupportRepository {
	
	private DataSource dataSource;

	public DataSourceSupportRepository() {
	}

	/**
	 * @return the dataSource
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * @param dataSource the dataSource to set
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
