/**
 * 
 */
package org.smarabbit.massy.test.model;

import org.smarabbit.massy.annotation.ExportService;
import org.smarabbit.massy.annotation.LazyBind;

/**
 * @author huangkaihui
 *
 */
@ExportService
public class AnnotatedObject {

	private String name;
	@LazyBind
	private String text;
	
	/**
	 * 
	 */
	public AnnotatedObject() {
	}

	public String getText(){
		return this.text;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
}
