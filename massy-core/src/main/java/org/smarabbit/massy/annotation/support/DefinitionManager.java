/**
 * 
 */
package org.smarabbit.massy.annotation.support;

/**
 * 定义管理器,提供类/字段/方法注解对应的所有{@link Definition}
 * @author huangkaihui
 *
 */
public interface DefinitionManager {

	/**
	 * 获取类的所有{@link Definition}
	 * @param clazz 类
	 */
	Definition[] getDefinitions(Class<?> clazz);

	/**
	 * 获取类指定类型的{@link Definition}
	 * @param clazz 类
	 * @param definitionType 定义类型
	 */
	<D extends Definition> D getDefinition(Class<?> clazz, Class<D> definitionType);
}
