/**
 * 
 */
package org.smarabbit.massy.model.persistent;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.smarabbit.massy.support.OrderComparator;
import org.smarabbit.massy.util.ServiceLoaderUtils;

/**
 * @author huangkaihui
 *
 */
public final class PersistenceSchemaHelper {

	private List<PersistenceSchemaHandler> handlers;

	public PersistenceSchemaHelper() {
		this.handlers = ServiceLoaderUtils.loadServices(PersistenceSchemaHandler.class);
		Collections.sort(handlers, new OrderComparator());
	}
	
	/**
	 * 构建数据模式
	 * @throws Exception
	 */
	public void build() throws Exception{
		Iterator<PersistenceSchemaHandler> it = this.handlers.iterator();
		while (it.hasNext()){
			PersistenceSchemaHandler handler = it.next();
			if (handler.supports()){
				handler.buildSchema();
				handler.preprocess();
			}
		}
	}
}
