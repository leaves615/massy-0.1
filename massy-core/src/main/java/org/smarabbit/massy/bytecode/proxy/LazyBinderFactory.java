/**
 * 
 */
package org.smarabbit.massy.bytecode.proxy;

import java.lang.reflect.Constructor;

import org.smarabbit.massy.annotation.LazyBindHandler;
import org.smarabbit.massy.annotation.support.LazyBindHandlerDefinition;
import org.smarabbit.massy.lazyload.LazyBinder;
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
	 * @param definition {@link LazyBindHandlerDefinition}
	 * @return
	 * 			{@link LazyBinder}，不能返回null.
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static LazyBinder<Object> create(Object handler, LazyBindHandlerDefinition definition) throws Exception{
		Asserts.argumentNotNull(handler, "handler");
		Asserts.argumentNotNull(definition, "definition");
		Class<?> clazz = GENERATOR.generate(definition);
		
		Constructor<?> cstor = clazz.getConstructor(new Class<?>[]{Object.class, LazyBindHandlerDefinition.class});
		return LazyBinder.class.cast(cstor.newInstance(handler, definition));
	}
	
}
