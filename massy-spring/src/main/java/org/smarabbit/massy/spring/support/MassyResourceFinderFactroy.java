/**
 * 
 */
package org.smarabbit.massy.spring.support;

/**
 * {@link MassyResourceFinder}工厂
 * 
 * @author huangkaihui
 */
public abstract class MassyResourceFinderFactroy {

	/**
	 * 创建{@link MassyResourceFinder}实例
	 * @return
	 * 		{@link MassyResourceFinder}实例，不能返回nulll.
	 */
	public static MassyResourceFinder create(){
		return new NamedMassyResourceFinder();
	}
	
}
