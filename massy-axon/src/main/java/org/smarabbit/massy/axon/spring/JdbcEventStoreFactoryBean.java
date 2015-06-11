/**
 * 
 */
package org.smarabbit.massy.axon.spring;

import org.axonframework.common.jdbc.ConnectionProvider;
import org.axonframework.common.jdbc.DataSourceConnectionProvider;
import org.axonframework.common.jdbc.UnitOfWorkAwareConnectionProviderWrapper;
import org.axonframework.eventstore.jdbc.DefaultEventEntryStore;
import org.axonframework.eventstore.jdbc.EventEntryStore;
import org.axonframework.eventstore.jdbc.EventSqlSchema;
import org.axonframework.eventstore.jdbc.GenericEventSqlSchema;
import org.axonframework.eventstore.jdbc.JdbcEventStore;
import org.springframework.beans.factory.config.AbstractFactoryBean;

/**
 * @author huangkaihui
 *
 */
public class JdbcEventStoreFactoryBean extends AbstractFactoryBean<JdbcEventStore> {

	private DataSourceConnectionProvider dataSourceConnectionProvider;
	private EventSqlSchema<?> schema;
	
	public JdbcEventStoreFactoryBean() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.AbstractFactoryBean#getObjectType()
	 */
	@Override
	public Class<?> getObjectType() {
		return JdbcEventStore.class;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.AbstractFactoryBean#createInstance()
	 */
	@Override
	protected JdbcEventStore createInstance() throws Exception {
		ConnectionProvider provider =
				new UnitOfWorkAwareConnectionProviderWrapper(this.dataSourceConnectionProvider);
		EventSqlSchema<?> schema = 
				this.schema == null ?new GenericEventSqlSchema<Object>(): this.schema;
		EventEntryStore<?> entryStore =
				new DefaultEventEntryStore<>(provider, schema);
		JdbcEventStore result = new JdbcEventStore(entryStore);
		
		return result;
	}

	/**
	 * @return the dataSourceConnectionProvider
	 */
	public DataSourceConnectionProvider getDataSourceConnectionProvider() {
		return dataSourceConnectionProvider;
	}

	/**
	 * @param dataSourceConnectionProvider the dataSourceConnectionProvider to set
	 */
	public void setDataSourceConnectionProvider(
			DataSourceConnectionProvider dataSourceConnectionProvider) {
		this.dataSourceConnectionProvider = dataSourceConnectionProvider;
	}

	/**
	 * @return the schema
	 */
	public EventSqlSchema<?> getEventSqlSchema() {
		return schema;
	}

	/**
	 * @param schema the schema to set
	 */
	public void setEventSqlSchema(EventSqlSchema<?> schema) {
		this.schema = schema;
	}

	
}
