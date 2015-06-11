/**
 * 
 */
package org.smarabbit.massy.axon.persistence;

/**
 * 事件溯源MySql模式
 * @author huangkaihui
 *
 */
public interface EventSourcingMySqlDDL {

	static final String CATALOG = "eventdb";
	
	/**
	 * 领域事件记录
	 * @author huangkaihui
	 *
	 */
	public interface DomainEventEntry{
		
		/**
		 * 表名称
		 */
		static final String TABLE_NAME = "${DomainEventEntry}";
		
		/**
		 * 删除表语句
		 */
		static final String DROP_TABLE = 
				"DROP TABLE IF EXISTS `${DomainEventEntry}`;";
		
		/**
		 * 创建表
		 */
		static final String CREATE_TABLE =
				"CREATE TABLE `${DomainEventEntry}` ( " +
						"`aggregateIdentifier` varchar(40) NOT NULL, " +
						"`sequenceNumber` bigint(20) NOT NULL, " +
						"`type` varchar(255) NOT NULL, " +
						"`eventIdentifier` varchar(255) NOT NULL, " +
						"`metaData` blob, " +
						"`payload` blob NOT NULL, " +
						"`payloadRevision` varchar(255) DEFAULT NULL, " +
						"`payloadType` varchar(255) NOT NULL, " +
						"`timeStamp` varchar(255) NOT NULL, " +
						"PRIMARY KEY (`aggregateIdentifier`,`sequenceNumber`,`type`) " +
						") ENGINE=InnoDB DEFAULT CHARSET=latin1;";
		
	}
	
	/**
	 * 快照记录
	 * @author huangkaihui
	 *
	 */
	public interface SnapshotEventEntry {
		
		static final String TABLE_NAME = "${SnapshotEventEntry}";
		
		static final String DROP_TABLE =
				"DROP TABLE IF EXISTS `${SnapshotEventEntry}`;";
		
		static final String CREATE_TABLE = 
				" create table ${SnapshotEventEntry} ( " +
						"aggregateIdentifier varchar(255) not null, " +
						"sequenceNumber bigint not null, " +
						"type varchar(255) not null, " +
						"eventIdentifier varchar(255) not null, " +
						"metaData blob, " +
						"payload blob not null, " +
						"payloadRevision varchar(255), " +
						"payloadType varchar(255) not null, " +
						"timeStamp varchar(255) not null, " +
						"primary key (aggregateIdentifier, sequenceNumber, type) " +
						")ENGINE=InnoDB DEFAULT CHARSET=latin1;";
		
	}
	
	/**
	 * Saga记录
	 * @author huangkaihui
	 *
	 */
	public interface SagaEntry{
		
		static final String TABLE_NAME = "${SagaEntry}";
		
		static final String DROP_TABLE = 
				"DROP TABLE IF EXISTS `${SagaEntry}`;";
		
		static final String CREATE_TABLE = 
				"CREATE TABLE `${SagaEntry}` ( " +
						"`sagaId` varchar(255) CHARACTER SET latin1 NOT NULL, " +
						"`revision` varchar(255) DEFAULT NULL, " +
						"`sagaType` varchar(255) DEFAULT NULL, " +
						"`serializedSaga` blob, " +
						"PRIMARY KEY (`sagaId`) " +
						") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
	}
	
	/**
	 * Saga关联键值记录
	 * @author huangkaihui
	 *
	 */
	public interface AssociationValueEntry{
		
		static final String TABLE_NAME = "${AssociationValueEntry}";
		
		static final String DROP_TABLE = 
				"DROP TABLE IF EXISTS `${AssociationValueEntry}`;";
		
		static final String CREATE_TABLE = 
				"CREATE TABLE `${AssociationValueEntry}` ( " +
						"`id` int(11) NOT NULL AUTO_INCREMENT, " +
						"`associationKey` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL, " +
						"`associationValue` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL, " +
						"`sagaId` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL, " +
						"`sagaType` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL, " +
						"PRIMARY KEY (`id`) " +
						") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;";
	}
	
	/**
	 * 审计日志记录
	 * @author huangkaihui
	 *
	 */
	public interface AuditLogEntry {
		
		static final String TABLE_NAME = "${AuditLogEntry}";
		
		static final String DROP_TABLE =
				"DROP TABLE IF EXISTS `${AuditLogEntry}`;";;
		
		static final String CREATE_TABLE =
				"CREATE TABLE `${AuditLogEntry}` (   " +
					"`id` bigint(20) NOT NULL AUTO_INCREMENT,  " +
					"`userId` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL,  " +
					"`displayName` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL, " +
					"`commandType` varchar(255) CHARACTER SET utf8mb4 NOT NULL, " +
					"`command` blob NOT NULL,  " +
					"`description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL, " +
					"`events` blob, " +
					"`resourceType` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL, " +
					"`resourceIdentifier` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL, " +
					"`success` bit(1) NOT NULL, " +
					"`resultType` varchar(255)  CHARACTER SET utf8mb4 DEFAULT NULL, " +
					"`result` blob,  " +
					"`causeType` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL, " +
					"`cause` blob, " +
					"`time` datetime NOT NULL, " +
					"PRIMARY KEY (`id`), " +
					"KEY `userIdIndex` (`userId`), " +
					"KEY `cmdTypeIndex` (`commandType`), " +
					"KEy `timeIndex` (`time`) " +
					") ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci; "; 
		
		static final String CREATE_AUDITLOG =
				"INSERT INTO `${AuditLogEntry}` ( "
						+ "userId, displayName, commandType, command, description, events, resourceType, resourceIdentifier, success,"
						+ "resultType, result, causeType, cause, time) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	}
	
	/**
	 * 事件错误记录
	 * @author huangkaihui
	 *
	 */
	public static interface EventErrorEntry {
		
		static final String DROP_TABLE =
				"DROP TABLE IF EXISTS `EventErrorEntry`;";
		
		static final String CREATE_TABLE =
				"CREATE TABLE `EventErrorEntry` ( " +
					"`identifier` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL, " +
					"`eventType` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL, " +
					"`event` blob NOT NULL, " +
					"`causeType` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL, " +
					"`cause` blob NOT NULL, " +
					"`time` datetime NOT NULL, " +
					"`repaired` bit(1) NOT NULL DEFAULT b'0', " +
					"PRIMARY KEY (`identifier`) " +
					") ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;";
		
		static final String CREATE_EVENTERROR =
				"INSERT INTO EventErrorEntry (" +
						"identifier, eventType, event, causeType, cause, time) VALUES(" +
						"?,?,?,?,?,?)";
	}
}
