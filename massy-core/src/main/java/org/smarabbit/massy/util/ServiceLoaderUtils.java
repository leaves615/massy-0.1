/**
 * 
 */
package org.smarabbit.massy.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;


/**
 * 服务加载工具类，对ServiceLoader提供的方法进行封装。
 * 
 * @author huangkh
 *
 */
public abstract class ServiceLoaderUtils {
	
	/**
	 * 加载并返回首个服务，使用当前线程的ContextClassLoader加载
	 * @param service 服务类型，非空
	 * @return 返回服务实例，服务未配置返回null.
	 */
	public static <S> S loadFirstService(Class<S> service){
		return loadFirstService(service, Thread.currentThread().getContextClassLoader());
	}

	/**
	 * 加载并返回首个服务
	 * @param service 服务类型，非空
	 * @param loader {@link ClassLoader},非空
	 * @return 返回服务实例，服务未配置则返回null.
	 */
	public static <S> S loadFirstService(Class<S> service, ClassLoader loader){
		Asserts.argumentNotNull(service, "service.");
		Asserts.argumentNotNull(loader, "loader.");
		Iterator<S> it = iteratorService(service,loader);
		return it.hasNext() ? it.next() : null;
	}
	
	/**
	 * 加载并返回所有服务
	 * @param service 服务类型，非空
	 * @return 返回{@link List},不能返回null.
	 */
	public static <S> List<S> loadServices(Class<S> service){
		return loadServices(service, Thread.currentThread().getContextClassLoader());
	}
	
	/**
	 * 加载并返回所有服务
	 * @param service 服务类型，非空
	 * @param loader {@link ClassLoader},非空
	 * @return 返回{@link List},不能返回null.
	 */
	public static <S> List<S> loadServices(Class<S> service, ClassLoader loader){
		Asserts.argumentNotNull(service, "service.");
		Asserts.argumentNotNull(loader, "loader.");
		Iterator<S> it = iteratorService(service,loader);
		List<S> result = new ArrayList<S>();
		
		while (it.hasNext()){
			result.add(it.next());
		}
		
		return result;
	}
	
	/**
	 * 加载服务
	 * @param service 服务类型，非空
	 * @param loader {@link ClassLoader}
	 * @return 返回{@link Iterator}.不能返回null.
	 */
	protected static <S> Iterator<S> iteratorService(Class<S> service, ClassLoader loader){
		ServiceLoader<S> sl = ServiceLoader.load(service, loader);
		return sl.iterator();
	}
	
}
