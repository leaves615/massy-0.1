/**
 * 
 */
package org.smarabbit.massy.annotation.support;

import javassist.CtMethod;

import org.smarabbit.massy.bytecode.proxy.LazyBinderFactory;
import org.smarabbit.massy.util.Asserts;

/**
 * LazyBindHandler记录
 * @author huangkaihui
 *
 */
public class LazyBinderEntry {

	private String declaringTypeName;
	private String fieldName;
		
	private CtMethod method;
	
	private LazyBinder<Object> handler;
	
	/**
	 * 
	 */
	public LazyBinderEntry(CtMethod method,  String declaringTypeName, String fieldName) {
		Asserts.argumentNotNull(method, "method");
		Asserts.argumentNotNull(declaringTypeName, "declaringTypeName");
		Asserts.argumentNotNull(fieldName, "fieldName");
		
		this.method = method;
		this.declaringTypeName = declaringTypeName;
		this.fieldName = fieldName;
	}

	/**
	 * 类型
	 * @return the declaringTypeName
	 */
	public String getDeclaringTypeName() {
		return declaringTypeName;
	}

	/**
	 * 字段名
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}
	
	/**
	 * 获取延迟加载处理句柄的类名
	 * @return
	 * 		字符串,不能返回null.
	 */
	public String getHandlerClassName(){
		return this.method.getDeclaringClass().getName();
	}
	
	/**
	 * 获取方法名称
	 * @return
	 * 		字符串,不能返回null.
	 */
	public String getHandlerMethodName(){
		return this.method.getName();
	}
	
	public CtMethod getMethod(){
		return this.method;
	}
	 
	/**
	 * 生成{@link LazyBinder}实例
	 * @param obj 绑定的对象
	 * @throws Exception 发生异常则抛出
	 */
	public boolean  genericLazyBinder(Object annotatedObj) throws Exception{
		if (this.handler == null){
			this.handler = LazyBinderFactory.create(annotatedObj, this.method);
			return true;
		}
		
		return false;
	}
	
	public Object doGetValue(Object obj){
		if (this.handler != null){
			return this.handler.getValue(obj);
		}
		
		throw new LazyLoadException("cannot generic LazyBinder, please do it.");
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LazyBindHandlerEntry [declaringTypeName=" + declaringTypeName
				+ ", fieldName=" + fieldName + ", method=" + method.getDeclaringClass().getName() +"." + method.getName() + "(...) ]";
	}

}
