/**
 * 
 */
package org.smarabbit.massy.annotation.support;

/**
 * {@link ExportServiceAnnotatedRepository}工厂
 * 
 * @author huangkaihui
 *
 */
public abstract class ExportServiceAnnotatedRepositoryFactory {

	private static ExportServiceAnnotatedRepository INSTANCE =
			new ExportServiceAnnotatedRepository();
	
	public static ExportServiceAnnotatedRepository getDefault(){
		return INSTANCE;
	}
}
