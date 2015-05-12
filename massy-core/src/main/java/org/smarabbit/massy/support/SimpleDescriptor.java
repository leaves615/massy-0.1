/**
 * 
 */
package org.smarabbit.massy.support;

import java.util.Map;

import org.smarabbit.massy.Descriptor;
import org.smarabbit.massy.util.Asserts;
import org.smarabbit.massy.util.CollectionUtils;

/**
 * 简单的{@link Descriptor},提供对{@link Map}的查询.
 * 
 * @author huangkaihui
 *
 */
public class SimpleDescriptor implements Descriptor {

	private Map<String, Object> props;
	
	/**
	 * 
	 */
	public SimpleDescriptor(Map<String, Object> props) {
		Asserts.argumentNotNull(props, "props");
		this.props = props;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.Descriptor#contains(java.lang.String)
	 */
	@Override
	public boolean contains(String name) {
		Asserts.argumentNotNull(name, "name");
		return this.props.containsKey(name);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.Descriptor#getProperty(java.lang.String)
	 */
	@Override
	public Object getProperty(String name) {
		Asserts.argumentNotNull(name, "name");
		return this.props.get(name);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.Descriptor#getProperty(java.lang.String, java.lang.Class)
	 */
	@Override
	public <P> P getProperty(String name, Class<P> propType) {
		Asserts.argumentNotNull(propType, "propType");
		Object result = this.getProperty(name);
		return propType.cast(result);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.Descriptor#getProperty(java.lang.String, java.lang.Object, java.lang.Class)
	 */
	@Override
	public <P> P getProperty(String name, P defaultValue, Class<P> propType) {
		P result = this.getProperty(name,  propType);
		return result == null ? defaultValue : result;
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.Descriptor#getPropertyNames()
	 */
	@Override
	public String[] getPropertyNames() {
		return this.props.keySet().toArray(new String[this.props.size()]);
	}

	/**
	 *是否为Empty.
	 * @return
	 * 		true表示是，false表示否
	 */
	protected boolean isEmpty(){
		return CollectionUtils.isEmpty(this.props);
	}
}
