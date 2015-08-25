/**
 * 
 */
package org.smarabbit.massy.adapt.support;

import java.util.HashMap;
import java.util.Map;

import org.smarabbit.massy.Constants;
import org.smarabbit.massy.adapt.AdaptFactory;
import org.smarabbit.massy.adapt.AdaptFactoryRegistration;
import org.smarabbit.massy.support.AbstractRegistration;
import org.smarabbit.massy.support.DescriptionUtils;
import org.smarabbit.massy.support.SimpleDescriptor;

/**
 * @author huangkaihui
 *
 */
abstract class AbstractAdaptFactoryRegistration<A> extends
		AbstractRegistration implements AdaptFactoryRegistration<A> {

	private final AdaptFactory<A> factory;
	
	public AbstractAdaptFactoryRegistration(long id, Class<A> adaptType, AdaptFactory<A> factory,
			Map<String, Object> props) {
		super(id);
		
		// 创建新的Map,并复制附加属性
		Map<String, Object> map = new HashMap<String, Object>();
		if (props != null){
			map.putAll(props);
		}

		// 适配类型
		map.put(Constants.ADAPT_TYPE, adaptType);
		// 工厂的类型
		Class<?> clazz = this.getObjectType(factory);
		map.put(Constants.OBJECT_CLASS, clazz);

		// 说明
		String description = DescriptionUtils.getDescription(clazz);
		if (description != null) {
			map.put(Constants.DESCRIPTION, description);
		}

		this.factory = factory;
		this.setDescriptor(new SimpleDescriptor(map));
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.adapt.AdaptFactoryRegistration#getAdaptType()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Class<A> getAdaptType() {
		return this.getDescriptor().getProperty(Constants.ADAPT_TYPE,
				Class.class);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.adapt.AdaptFactoryRegistration#getAdaptFactory()
	 */
	@Override
	public AdaptFactory<A> getAdaptFactory() {
		return this.factory;
	}

	protected Class<?> getObjectType(Object object){		
		return object.getClass();
	}
}
