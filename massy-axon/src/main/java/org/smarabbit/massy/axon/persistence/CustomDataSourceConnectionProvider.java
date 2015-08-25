/**
 * 
 */
package org.smarabbit.massy.axon.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.axonframework.common.jdbc.DataSourceConnectionProvider;

/**
 * @author huangkaihui
 *
 */
public abstract class CustomDataSourceConnectionProvider extends
		DataSourceConnectionProvider {
	
	/**
	 * @param dataSource
	 */
	public CustomDataSourceConnectionProvider(DataSource dataSource) {
		super(dataSource);
	}
	
	/* (non-Javadoc)
	 * @see org.axonframework.common.jdbc.DataSourceConnectionProvider#getConnection()
	 */
	@Override
	public Connection getConnection() throws SQLException {
		return this.checkAndAuditConnectionCatalog(super.getConnection());
	}
	
	/**
	 * 检查并调整Connection的Catalog属性
	 * @param connection
	 * @return
	 */
	protected abstract Connection checkAndAuditConnectionCatalog(Connection connection)
		throws SQLException;
	
}
