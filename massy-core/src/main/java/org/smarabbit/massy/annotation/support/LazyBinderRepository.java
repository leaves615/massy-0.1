/**
 * 
 */
package org.smarabbit.massy.annotation.support;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smarabbit.massy.annotation.LazyBindHandler;
import org.smarabbit.massy.util.Asserts;

import javassist.CtMethod;

/**
 * @author huangkaihui
 *
 */
public class LazyBinderRepository {

	private static final Logger logger =
			LoggerFactory.getLogger(LazyBinderRepository.class);
	
	private ConcurrentHashMap<String, LazyBinderEntry> entries =
			new ConcurrentHashMap<String, LazyBinderEntry>();
	private ConcurrentHashMap<String, CopyOnWriteArrayList<LazyBinderEntry>> handlers =
			new ConcurrentHashMap<String, CopyOnWriteArrayList<LazyBinderEntry>>();
	
	/**
	 * 
	 */
	public LazyBinderRepository() {
		
	}

	/**
	 * 注册注解{@link LazyBindHandler}的方法
	 * @param method {@link LazyBindHandler},不能为null.
	 * @param anno {@link LazyBindHandler}，注解
	 * @return
	 * 		true表示注册成功，false表示失败(已注册)
	 */
	public void register(CtMethod method,  LazyBindHandler anno){
		Asserts.argumentNotNull(method, "method");
		Asserts.argumentNotNull(anno, "anno");
		
		String declaringTypeName = this.checkAndReturnDeclaringTypeName(method, anno);
		String fieldName = anno.fieldName();
		String key = this.getKey(declaringTypeName, fieldName);
		
		LazyBinderEntry entry = this.entries.get(key);
		if (entry == null){
			LazyBinderEntry tmp = new LazyBinderEntry(method, declaringTypeName, fieldName);
			entry = this.entries.putIfAbsent(key, tmp);
			if (entry == null){
				entry = tmp;
			}
		}
		
		CopyOnWriteArrayList<LazyBinderEntry> list = this.handlers.get(method.getDeclaringClass().getName());
		if (list == null){
			CopyOnWriteArrayList<LazyBinderEntry> tmp = new CopyOnWriteArrayList<LazyBinderEntry>();
			list =this.handlers.putIfAbsent(method.getDeclaringClass().getName(), tmp);
			if (list == null){
				list = tmp;
			}
			
			list.add(entry);
		}
		
		if (logger.isTraceEnabled()){
			logger.trace("register LazyBindHandler method: " + method);
		}
	}
	
	/**
	 * 判断对象实例是否有注解{@link LazyBindHandler}的方法，对有注解方法的进行绑定
	 * @param obj
	 */
	public void maybeToBind(Object obj){
		Asserts.argumentNotNull(obj, "obj");
		Class<?> clazz = obj.getClass();
		
		//接口对象，直接返回
		if (clazz.isInterface()) {
			return;
		}
		
		CopyOnWriteArrayList<LazyBinderEntry> list = this.getLazyBinderEntryList(clazz);
		if (list == null){
			return;
		}
		
		//生成
		for (LazyBinderEntry entry: list){
			try{
				if (!entry.genericLazyBinder(obj)){
					if (logger.isWarnEnabled()){
						logger.warn("generic LazyBinder failed, because it exist: " + entry + "." );
					}
				}else{
					if (logger.isTraceEnabled()){
						logger.trace("generic LazyBinder to wrapper: " + entry.getDeclaringTypeName() +"." + entry.getMethod().getName() + "(...)." );
					}
				}
			}catch(Exception e){
				if (logger.isErrorEnabled()){
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
	
	/**
	 * 延迟加载
	 * @param declaringType 声明类型
	 * @param fieldName 字段名
	 * @param lazyBindObj 延迟加载的对象实例
	 * @return
	 * 		{@link Object},延迟加载的字段值。
	 */
	public Object lazyLoad(Class<?> declaringType, String fieldName, Object lazyBindObj){
		Asserts.argumentNotNull(declaringType, "declaringType");
		Asserts.argumentNotNull(fieldName, "fieldName");
		Asserts.argumentNotNull(lazyBindObj, "lazyBindObj");
		
		LazyBinderEntry entry = this.getLazyBinderEntry(lazyBindObj.getClass(), fieldName);
		if (entry == null){
			throw new LazyLoadException(
					"cannot found match LazyBinder:  declaringType =" + declaringType.getName() +", fieldName=" + fieldName + "." );
		}
		
		return entry.doGetValue(lazyBindObj);
	}
	
	private LazyBinderEntry getLazyBinderEntry(Class<?> clazz, String fieldName){
		if (clazz ==Object.class){
			return null;
		}
		
		String key = this.getKey(clazz.getName(), fieldName);
		LazyBinderEntry result = this.entries.get(key);
		if (result == null){
			Class<?> parent = clazz.getSuperclass();
			result = this.getLazyBinderEntry(parent, fieldName);
		}
		return result;
	}
	
	private CopyOnWriteArrayList<LazyBinderEntry> getLazyBinderEntryList(Class<?> clazz){
		if (clazz ==Object.class){
			return null;
		}
		
		CopyOnWriteArrayList<LazyBinderEntry> result = this.handlers.get(clazz.getName());
		if (result == null){
			Class<?> parent = clazz.getSuperclass();
			
			result = this.getLazyBinderEntryList(parent);
		}
		
		return result;
	}
	
	private String checkAndReturnDeclaringTypeName(CtMethod method, LazyBindHandler anno){
		try{
			if (method.getParameterTypes().length != 1){
				throw new LazyLoadException("method parameter count not equal to one: " + method.getSignature() + ".");
			}
			
			Class<?> declaringType = anno.declaringType();
			return declaringType != Void.class ? 
					declaringType.getName() : 
						method.getParameterTypes()[0].getName();
		}catch(LazyLoadException e){
			throw e;
		}catch(Exception e){
			throw new LazyLoadException(e.getMessage(), e);
		}
	}
	
	private String getKey(String declaringTypeName, String fieldName){
		return declaringTypeName + "#" + fieldName;
	}
}
