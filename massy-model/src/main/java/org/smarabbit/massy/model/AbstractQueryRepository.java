/**
 * 
 */
package org.smarabbit.massy.model;

import javax.annotation.Resource;

import org.smarabbit.massy.util.Asserts;

/**
 * @author huangkaihui
 *
 */
public abstract class AbstractQueryRepository<P, T> implements QueryRepository<P, T> {
	
	private CachedRepository cachedRepository ;
		
	/**
	 * 
	 */
	public AbstractQueryRepository() {
		
	}
	
	/* (non-Javadoc)
	 * @see org.smarabbit.massy.model.QueryRepository#findById(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T findById(P id) {
		Asserts.argumentNotNull(id, "od");
		
		
		T result = null;
		if (this.cachedRepository != null){
			result =(T)this.cachedRepository.findById(this.getCachedType(), id);
		
			if (result == null){
				result = this.doFindById(id);
				if (result != null){
					this.cachedRepository.addEntry(this.getCachedType(), id, result);
				}
			}
		}else{
			result = this.doFindById(id);
		}
		return result;
	}

	/**
	 * 缓存类型
	 * @return
	 */
	public abstract Class<?> getCachedType();
	
	/**
	 * 执行查找
	 * @param id 编号
	 * @return 
	 * 			{@link T}
	 */
	protected abstract T doFindById(P id);

	/**
	 * @return the cachedRepository
	 */
	public CachedRepository getCachedRepository() {
		return cachedRepository;
	}

	/**
	 * @param cachedRepository the cachedRepository to set
	 */
	@Resource
	public void setCachedRepository(CachedRepository cached) {
		this.cachedRepository = cached;
	}
	
	
}
