/**
 * 
 */
package org.smarabbit.massy.model;

/**
 * 查询仓储
 * @author huangkaihui
 *
 */
public interface QueryRepository<P, T> {

	/**
	 * 按主键查找{@link T}
	 * @param id
	 * 			主键,非空
	 * @return
	 * 			{@link T},如果不存在则返回null.
	 */
	T findById(P id);
}
