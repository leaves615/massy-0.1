/**
 * 
 */
package org.smarabbit.massy.model.metadata;

/**
 * {@link ClassMetadataRegistry}工厂
 * @author huangkaihui
 *
 */
public abstract class ClassMetadataRegistryFactory {

	private static final ClassMetadataRegistry INSTANCE =
			new ClassMetadataRegistry();
	
	public static ClassMetadataRegistry getDefault(){
		return INSTANCE;
	}
}
