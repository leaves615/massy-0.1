/**
 * 
 */
package org.smarabbit.massy.model.persistent;

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
	
	/**
	 * @param storeType
	 */
	public JdbcSchemaHandler(String storeType) {
		super(storeType);

	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.model.store.StoreSchemaHandler#buildSchema()
	 */
	@Override
	public void buildSchema() throws Exception {
		String alias = this.getAlias();
		DataSource dataSource = this.getDataSource(alias);
		if (dataSource != null){
			this.doBuildSchema(dataSource);
		}else{
			if (logger.isWarnEnabled()){
				logger.warn("cannot found datasource service :" + alias);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.model.store.StoreSchemaHandler#preprocess()
	 */
	@Override
	public void preprocess() throws Exception {
		String alias = this.getAlias();
		DataSource dataSource = this.getDataSource(alias);
		if (dataSource != null){
			this.doPreprocess(dataSource);
		}else{
			if (logger.isWarnEnabled()){
				logger.warn("cannot found datasource service :" + alias);
			}
		}
	}
	
	protected DataSource getDataSource(String alias){
		return MassyUtils.getService(DataSource.class, alias, true);
	}
	
	protected abstract String getAlias();
	protected abstract void doBuildSchema(DataSource dataSource) throws Exception;
	protected abstract void doPreprocess(DataSource dataSource) throws Exception;

}