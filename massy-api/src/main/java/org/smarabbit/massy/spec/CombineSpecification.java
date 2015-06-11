/**
 * 
 */
package org.smarabbit.massy.spec;

import java.util.Arrays;
import java.util.List;

import org.smarabbit.massy.util.Asserts;

/**
 * 组合规则检查器
 * @author huangkaihui
 *
 */
public final class CombineSpecification<T> implements Specification<T> {

	private final List<Specification<T>> specList;
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public CombineSpecification(Specification<T> ... specs) {
		this(Arrays.asList(specs));
	}
	
	public CombineSpecification(List<Specification<T>> specs){
		Asserts.argumentNotEmpty(specs, "specs");
		this.specList = specs;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.spec.Specification#isStaisfyBy(java.lang.Object)
	 */
	@Override
	public boolean isStaisfyBy(T target) {
		for (Specification<T> spec : this.specList){
			if (!spec.isStaisfyBy(target)){
				return false;
			}
		}
		return true;
	}

	public Specification<?>[] getSpecifications(){
		return this.specList.toArray(
				new Specification<?>[this.specList.size()]);
	}
	
	/**
	 * 是否有指定类型的规则检查器
	 * @param specType
	 * 			{@link Class}
	 * @return
	 * 			true表示有，false表示没有
	 */
	public boolean hasSpecification(Class<? extends Specification<?>> specType){
		for (Specification<?> spec : this.specList){
			if (CombineSpecification.class.isInstance(spec)){
				if (((CombineSpecification<?>)spec).hasSpecification(specType)){
					return true;
				}
			}else{
				if (specType.isInstance(specType)){
					return true;
				}
			}
		}
		
		return false;
	}
}
