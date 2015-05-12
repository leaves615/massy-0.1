/**
 * 
 */
package org.smarabbit.massy.spec;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.smarabbit.massy.util.Asserts;
import org.smarabbit.massy.util.CollectionUtils;


/**
 * 规则检查器工具类，提供对object、Collection、数组的规则检查和排序
 * @author Huangkaihui
 *
 */
public final class SpecificationUtils {

	/**
	 * 判断目标是否满足规则
	 * @param target 
	 * 		目标对象，不能为null.
	 * @param spec 
	 * 		{@link Specification}，不能为null.
	 * @return 
	 * 		true表示满足规则，否则返回false.
	 */
	public static <T> boolean isStaisfyBy(T target, Specification<T> spec){
		Asserts.argumentNotNull(target, "target");
		Asserts.argumentNotNull(spec, "spec");
		
		return spec.isStaisfyBy(target);
	}
	
	/**
	 * 查找首个满足规则的元素
	 * @param target 
	 * 		{@link Collection},不能为null.
	 * @param spec 
	 * 		{@link Specification}，不能为null.
	 * @return 
	 * 		{@link T}实例，无满足规则情况返回null.
	 */
	public static <T> T findFirstBySpec(Collection<? extends T> target, Specification<T> spec){
		Asserts.argumentNotNull(target, "target");
		Asserts.argumentNotNull(spec, "spec");
		
		for (T obj : target){
			if (spec.isStaisfyBy(obj)){
				return obj;
			}
		}
		
		return null;
	}
	
	/**
	 * 查找首个满足规则的元素
	 * @param target 
	 * 		目标对象数组,不能为null.
	 * @param spec 
	 * 		{@link Specification}，不能为null.
	 * @return 
	 * 		{@link T}实例，无满足规则情况返回null.
	 */
	public static <T> T findFirstBySpec(T[] target, Specification<T> spec){
		Asserts.argumentNotNull(target, "target");
		Asserts.argumentNotNull(spec, "spec");
		
		int size = target.length;
		for (int i=0; i<size; i++){
			T temp = target[i];
			if (spec.isStaisfyBy(temp)){
				return temp;
			}
		}
		
		return null;
	}
	
	/**
	 * 获取所有满足规则要求的集合元素
	 * @param target 目标对象{@link Collection},不能为null.
	 * @param spec {@link Specification}规则检查器，不能为null.
	 * @return 返回{@link List}, 无满足规则要求的集合元素返回empty.
	 */
	public static <T> List<T> isStaisfyToList(Collection<? extends T> target, Specification<T> spec){
		Asserts.argumentNotNull(target, "target");
		Asserts.argumentNotNull(spec, "spec");
		
		List<T> result = new ArrayList<T>();
		for (T obj : target){
			if (spec.isStaisfyBy(obj)){
				result.add(obj);
			}
		}
		return result;
	}
	
	/**
	 * 获取所有满足规则要求的集合元素,并排序
	 * @param target 
	 * 		{@link Collection},不能为null.
	 * @param spec 
	 * 		{@link Specification}，不能为null.
	 * @param comparator 
	 * 		{@link Comparator}，不能为null.
	 * @return 
	 * 		{@link List}, 无满足规则要求的集合元素返回empty.
	 */
	public static <T> List<T> isStaisfyToList(Collection<? extends T> target, Specification<T> spec, Comparator<T> comparator){
		Asserts.argumentNotNull(comparator, "comparator");
		
		List<T> result = isStaisfyToList(target, spec);
		if (!CollectionUtils.isEmpty(result)){
			Collections.sort(result, comparator);
		}
		return result;
	}
	
	/**
	 * 获取所有满足规则要求的集合元素
	 * @param target 目标对象{@link Collection},不能为null.
	 * @param spec {@link Specification}规则检查器，不能为null.
	 * @return 返回{@link Set}, 无满足规则要求的集合元素返回empty.
	 */
	public static <T> Set<T> isStaisfyToSet(Collection<? extends T> target, Specification<T> spec){
		Asserts.argumentNotNull(target, "target");
		Asserts.argumentNotNull(spec, "spec");
		
		Set<T> result = new HashSet<T>();
		for (T obj : target){
			if (spec.isStaisfyBy(obj)){
				result.add(obj);
			}
		}
		return result;
	}
	
	/**
	 * 是否有指定类型的规则检查器
	 * @param specType
	 * @param spec
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> boolean hasSpecification(Class<? extends Specification<T>> specType, Specification<T> spec){
		Asserts.argumentNotNull(spec, "spec");
		Asserts.argumentNotNull(specType, "specType");
		if (specType.isInstance(spec)) return true;
		
		if (spec instanceof CombineSpecification){
			return ((CombineSpecification)spec).hasSpecification(specType);
		}
		
		return false;
	}
}
