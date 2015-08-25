/**
 * 
 */
package org.smarabbit.massy.model.persistent.jdbc;

import java.sql.Connection;
import java.sql.Statement;

import javax.sql.DataSource;

import org.smarabbit.massy.model.persistent.PersistenceSchemaHandler;
import org.smarabbit.massy.util.Asserts;

/**
 * JdbcSchemaHandler,处理采用数据库存储的建表和初始化过程
 * @author huangkaihui
 *
 */
public class SimpleJdbcSchemaHandler implements PersistenceSchemaHandler {

	private DataSource dataSource;
	private JdbcSchema schema;
	
	private String catalog;
	
	public SimpleJdbcSchemaHandler() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.smarabbit.massy.model.store.StoreSchemaHandler#buildSchema()
	 */
	@Override
	public void buildSchema() throws Exception {
		String[] ddls = this.schema.getDataDefinitionLanguages();
		int size = ddls.length;
		if (size > 0) {

			Connection conn = dataSource.getConnection();
			if (this.catalog != null){
				if (!this.catalog.equals(conn.getCatalog())){
					conn.setCatalog(this.catalog);
				}
			}
			
			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();

			for (int i = 0; i < size; i++) {
				stmt.addBatch(ddls[i]);
			}

			stmt.executeBatch();
			conn.setAutoCommit(true);

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.smarabbit.massy.model.store.StoreSchemaHandler#preprocess()
	 */
	@Override
	public void preprocess() throws Exception {
		Asserts.ensureFieldInited(this.dataSource, "dataSource");
		Asserts.ensureFieldInited(this.schema, "schema");

		String cmds[] = this.schema.getPreprocessCommands();
		int size = cmds.length;

		if (size > 0) {
			DataSource dataSource = this.getDataSource();
			Connection conn = dataSource.getConnection();
			if (this.catalog != null){
				if (!this.catalog.equals(conn.getCatalog())){
					conn.setCatalog(this.catalog);
				}
			}
			
			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();

			for (int i = 0; i < size; i++) {
				stmt.addBatch(cmds[i]);
			}

			stmt.executeBatch();
			conn.setAutoCommit(true);
		}
	}

	/**
	 * @return the schema
	 */
	public JdbcSchema getSchema() {
		return schema;
	}

	/**
	 * @param schema
	 *            the schema to set
	 */
	public void setSchema(JdbcSchema schema) {
		this.schema = schema;
	}

	/**
	 * @return the dataSource
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * @param dataSource
	 *            the dataSource to set
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * @return the catalog
	 */
	public String getCatalog() {
		return catalog;
	}

	/**
	 * @param catalog the catalog to set
	 */
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}	
	
}
