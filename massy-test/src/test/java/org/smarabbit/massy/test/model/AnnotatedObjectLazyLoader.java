/**
 * 
 */
package org.smarabbit.massy.test.model;

import org.smarabbit.massy.annotation.LazyBindHandler;

/**
 * @author huangkaihui
 *
 */
public class AnnotatedObjectLazyLoader {

	/**
	 * 
	 */
	public AnnotatedObjectLazyLoader() {
		
	}
	
	@LazyBindHandler(fieldName="text")
	public String loadText(AnnotatedObject obj){
		return "hello " + obj.getName();
	}

}
