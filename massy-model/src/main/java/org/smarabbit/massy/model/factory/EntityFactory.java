/**
 * 
 */
package org.smarabbit.massy.model.factory;

import org.smarabbit.massy.model.provider.DataProvider;



/**
 * 实体对象工厂，提供从存储构建对象实例
 * @author huangkaihui
 *
 */
public interface EntityFactory{
		
	static final String DEFAULT_TYPENAME = "typeName";
	
	/**
	 * 创建实例
	 * 
	 * @param provider {@link DataProvider}，非空
	 * @return 
	 * 			{@link Object},不支持则返回null.
	 */
	Object create(DataProvider provider) throws EntityInstantiationException;
}
