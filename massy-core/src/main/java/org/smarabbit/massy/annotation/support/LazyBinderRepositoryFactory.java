/**
 * 
 */
package org.smarabbit.massy.annotation.support;

/**
 * {@link LazyBinderRepository}工厂
 * @author huangkaihui
 *
 */
public abstract class LazyBinderRepositoryFactory {

	private static final LazyBinderRepository INSTANCE =
			new LazyBinderRepository();
	
	/*
	 * 获取缺省的{@link LazyBinderRepository}实例
	 */
	public static LazyBinderRepository getDefault(){
		return INSTANCE;
	}
}
