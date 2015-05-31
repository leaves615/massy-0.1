/**
 * 
 */
package org.smarabbit.massy.model.persistent;

import java.sql.Connection;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smarabbit.massy.MassyUtils;

/**
 * @author huangkaihui
 *
 */
public abstract class JdbcSchemaHandler extends AbstractPersistenceSchemaHandler {

	private static final Logger logger =
			LoggerFactory.getLogger(JdbcSchemaHandler.class);
	
	private PersistenceSchema schema;
	
	/**
	 * @param storeType
	 */
	public JdbcSchemaHandler(String storeType) {
		super(storeType);
		this.schema = this.createSchema();
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.model.store.StoreSchemaHandler#buildSchema()
	 */
	@Override
	public void buildSchema() throws Exception {
		String[] ddls = this.schema.getDataDefinitionLanguages();
		int size = ddls.length;
		if (size >0){
			String alias = this.getAlias();
			DataSource dataSource = this.getDataSource(alias);
			if (dataSource != null){
				Connection conn = dataSource.getConnection();
				conn.setAutoCommit(false);
				Statement stmt = conn.createStatement();
				
				for (int i=0; i<size; i++) {
					stmt.addBatch(ddls[i]);
				}
				
				stmt.executeBatch();
				conn.setAutoCommit(true);
			}else{
				if (logger.isWarnEnabled()){
					logger.warn("cannot found datasource service :" + alias);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.model.store.StoreSchemaHandler#preprocess()
	 */
	@Override
	public void preprocess() throws Exception {
		String cmds[] = this.schema.getPreprocessCommands();
		int size = cmds.length;
		
		if (size >0){
			String alias = this.getAlias();
			DataSource dataSource = this.getDataSource(alias);
			if (dataSource != null){
				Connection conn = dataSource.getConnection();
				conn.setAutoCommit(false);
				Statement stmt = conn.createStatement();
				
				for (int i=0; i<size; i++) {
					stmt.addBatch(cmds[i]);
				}
				
				stmt.executeBatch();
				conn.setAutoCommit(true);
			}else{
				if (logger.isWarnEnabled()){
					logger.warn("cannot found datasource service :" + alias);
				}
			}
		}
		
	}
	
	protected DataSource getDataSource(String alias){
		return MassyUtils.getService(DataSource.class, alias, true);
	}
	
	protected abstract String getAlias();
	protected abstract PersistenceSchema createSchema();

}
