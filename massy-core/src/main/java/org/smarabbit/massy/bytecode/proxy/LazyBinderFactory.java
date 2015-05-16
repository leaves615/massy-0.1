/**
 * 
 */
package org.smarabbit.massy.bytecode.proxy;

import java.lang.reflect.Constructor;

import javassist.CtMethod;

import org.smarabbit.massy.annotation.support.LazyBinder;
import org.smarabbit.massy.util.Asserts;

/**
 * {@link LazyBinder}工厂，自动构建{@link LazyBinder}实例
 * @author huangkaihui
 *
 */
public abstract class LazyBinderFactory {

	private static final LazyBinderTypeGenerator GENERATOR =
			new LazyBinderTypeGenerator();
	
	/**
	 * 创建{@link LazyBinder}实例
	 * @param handler 标记有{@link LazyBindHandler}的实例
	 * @param method {@link CtMethod}方法
	 * @return
	 * 			{@link LazyBinder}，不能返回null.
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static LazyBinder<Object> create(Object handler, CtMethod method) throws Exception{
		Asserts.argumentNotNull(method, "method");
		Class<?> clazz = GENERATOR.generate(method);
		
		Constructor<?> cstor = clazz.getConstructor(new Class<?>[]{Object.class, String.class});
		return LazyBinder.class.cast(cstor.newInstance(handler, method.getName()));
	}
}
