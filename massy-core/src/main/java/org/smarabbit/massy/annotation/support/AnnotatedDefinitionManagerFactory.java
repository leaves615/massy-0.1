/**
 * 
 */
package org.smarabbit.massy.annotation.support;

/**
 * {@link AnnotatedDefinitionManager}工厂
 * @author huangkaihui
 *
 */
public abstract class AnnotatedDefinitionManagerFactory {

	private static final AnnotatedDefinitionManager INSTANCE =
			new AnnotatedDefinitionManager();
	
	/**
	 * 获取缺省的{@link AnnotatedDefinitionManager}
	 * @return
	 */
	public static AnnotatedDefinitionManager getDefault(){
		return INSTANCE;
	}
}
