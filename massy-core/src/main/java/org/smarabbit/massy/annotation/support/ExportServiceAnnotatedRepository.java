/**
 * 
 */
package org.smarabbit.massy.annotation.support;

import java.util.concurrent.ConcurrentHashMap;

import javassist.CtClass;

import org.smarabbit.massy.annotation.ExportService;
import org.smarabbit.massy.util.Asserts;

/**
 * 注解{@link ExportService}的组件仓储
 * @author huangkaihui
 *
 */
public class ExportServiceAnnotatedRepository {

	private ConcurrentHashMap<String, ExportService> annoMap =
			new ConcurrentHashMap<String, ExportService>();
	
	/**
	 * 
	 */
	public ExportServiceAnnotatedRepository() {
		
	}

	/**
	 * 添加类的{@link ExportService}
	 * @param CtClass 类
	 * @param anno 注解
	 * @param anno {@link ExportService}注解
	 */
	public void add(CtClass cc, ExportService anno){
		Asserts.argumentNotNull(cc, "cc");
		Asserts.argumentNotNull(anno, "anno");
		this.annoMap.putIfAbsent(cc.getName(), anno);
	}
	
	/**
	 * 按类获取{@link ExportService}
	 * @param clazz 类
	 * @return {@link ExportService}
	 */
	public ExportService getExportService(Class<?> clazz){
		Asserts.argumentNotNull(clazz, "clazz");
		
		return this.annoMap.get(clazz.getName());
	}
	
	/**
	 * 按类层次结构获取{@link ExportService}注解
	 * <br>
	 * 按类的继承层次，倒序查找{@link ExportService}
	 * @param clazz 类型，不能为null.
	 * @return
	 * 			{@link ExportService},可能返回null.
	 */
	public ExportService getExportServiceFromHierarchy(Class<?> clazz){
		Asserts.argumentNotNull(clazz, "clazz");
		if (clazz.isInterface()){
			return null;
		}
		
		ExportService result = null;
		Class<?> tmp = clazz;
		while (result == null){
			if (tmp == Object.class){
				break;
			}
			
			result = this.annoMap.get(tmp.getName());
			tmp = tmp.getSuperclass();
		}
		
		return result;
	}
}
