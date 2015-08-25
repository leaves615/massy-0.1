/**
 * 
 */
package org.smarabbit.massy.axon.auditing;

import java.io.Serializable;
import java.sql.Timestamp;

import org.smarabbit.massy.model.My;
import org.smarabbit.massy.model.MyFactory;
import org.smarabbit.massy.util.Asserts;

/**
 * 命令处理完成事件
 * @author huangkaihui
 *
 */
public class CommandProcessCompletedEvent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3503216702084255281L;

	private final AuditEntry entry;
	
	private final String userId;
	private final String displayName;
	
	private final Timestamp time;
	
	/**
	 * 
	 */
	public CommandProcessCompletedEvent(AuditEntry entry, Timestamp time) {
		Asserts.argumentNotNull(entry, "entry");
		Asserts.argumentNotNull(time, "time");
		this.entry = entry;
		
		My my = MyFactory.getCurrent();
		this.userId = my.getId();
		this.displayName = my.getDisplayName();
		
		this.time = time;
	}
	
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}



	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}



	public AuditEntry getAuditEntry(){
		return this.entry;
	}

	/**
	 * @return the time
	 */
	public Timestamp getTime() {
		return time;
	}

}
