/**
 * 
 */
package org.smarabbit.massy.axon.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.smarabbit.massy.model.persistent.jdbc.AbstractJdbcSchema;

/**
 * @author huangkaihui
 *
 */
public class EventSourcingMySqlSchema extends AbstractJdbcSchema {

	/**
	 * 
	 */
	public EventSourcingMySqlSchema() {
		
	}
	
	/* (non-Javadoc)
	 * @see org.smarabbit.massy.model.persistent.AbstractPersistenceSchema#getDefaultMappedName()
	 */
	@Override
	protected Map<String, String> getDefaultMappedName() {
		Map<String, String> result = super.getDefaultMappedName();
		
		result.put(EventSourcingMySqlDDL.DomainEventEntry.TABLE_NAME, "DomainEventEntry");
		result.put(EventSourcingMySqlDDL.SnapshotEventEntry.TABLE_NAME, "SnapshotEventEntry");
		
		result.put(EventSourcingMySqlDDL.SagaEntry.TABLE_NAME, "SagaEntry");
		result.put(EventSourcingMySqlDDL.AssociationValueEntry.TABLE_NAME, "AssociationValueEntry");
		
		result.put(EventSourcingMySqlDDL.AuditLogEntry.TABLE_NAME, "AuditLogEntry");
		
		return result;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.model.persistent.AbstractPersistenceSchema#initDataDefinitionLanguages(java.util.Map)
	 */
	@Override
	protected List<String> initDataDefinitionLanguages(
			Map<String, String> mappedName) {
		List<String> result = new ArrayList<String>();
		
		result.add(this.replaceMappedName(
				EventSourcingMySqlDDL.DomainEventEntry.DROP_TABLE, mappedName));
		result.add(this.replaceMappedName(
				EventSourcingMySqlDDL.DomainEventEntry.CREATE_TABLE, mappedName));
		
		result.add(this.replaceMappedName(
				EventSourcingMySqlDDL.SnapshotEventEntry.DROP_TABLE, mappedName));
		result.add(this.replaceMappedName(
				EventSourcingMySqlDDL.SnapshotEventEntry.CREATE_TABLE, mappedName));
		
		result.add(this.replaceMappedName(
				EventSourcingMySqlDDL.SagaEntry.DROP_TABLE, mappedName));
		result.add(this.replaceMappedName(
				EventSourcingMySqlDDL.SagaEntry.CREATE_TABLE, mappedName));
		
		result.add(this.replaceMappedName(
				EventSourcingMySqlDDL.AssociationValueEntry.DROP_TABLE, mappedName));
		result.add(this.replaceMappedName(
				EventSourcingMySqlDDL.AssociationValueEntry.CREATE_TABLE, mappedName));
		
		result.add(this.replaceMappedName(
				EventSourcingMySqlDDL.AuditLogEntry.DROP_TABLE, mappedName));
		result.add(this.replaceMappedName(
				EventSourcingMySqlDDL.AuditLogEntry.CREATE_TABLE, mappedName));
		
		result.add(EventSourcingMySqlDDL.EventErrorEntry.DROP_TABLE);
		result.add(EventSourcingMySqlDDL.EventErrorEntry.CREATE_TABLE);
		return result;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.model.persistent.AbstractPersistenceSchema#initPreprocessCommands(java.util.Map)
	 */
	@Override
	protected List<String> initPreprocessCommands(Map<String, String> mappedName) {
		List<String> result = new ArrayList<String>();
		return result;
	}

}
