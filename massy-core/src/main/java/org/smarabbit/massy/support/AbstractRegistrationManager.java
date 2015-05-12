/**
 * 
 */
package org.smarabbit.massy.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.smarabbit.massy.Descriptor;
import org.smarabbit.massy.Registration;
import org.smarabbit.massy.RegistrationManager;
import org.smarabbit.massy.util.Asserts;

/**
 * 实现{@link RegistrationManager}的抽象类
 * @author huangkh
 *
 */
public abstract class AbstractRegistrationManager<R extends Registration> 
	implements RegistrationManager<R> {
			
	/**
	 * 
	 */
	public AbstractRegistrationManager() {}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.adaptation.AdaptFactoryRegistrationManager#add(org.smarabbit.massy.adaptation.AdaptFactoryRegistration)
	 */
	@Override
	public void add(R registration) {
		Asserts.argumentNotNull(registration, "registration");
		this.doAdd(registration);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.adaptation.AdaptFactoryRegistrationManager#remove(org.smarabbit.massy.adaptation.AdaptFactoryRegistration)
	 */
	@Override
	public void remove(R registration) {
		Asserts.argumentNotNull(registration, "registration");
		this.doRemove(registration);
	}

	/* (non-Javadoc)
	 * @see org.smarabbit.massy.adaptation.AdaptFactoryRegistrationManager#getDescriptors()
	 */
	@Override
	public Descriptor[] getDescriptors() {
		//获取所有AdaptFactoryRegistration
		Collection<R> c =
				this.getRegistrations();
		
		//从AdaptFactoryRegistration中取得Descriptor并转换为数组
		List<Descriptor> list = new ArrayList<Descriptor>(c.size());
		Iterator<R> it = c.iterator();
		while (it.hasNext()){
			list.add(it.next().getDescriptor());
		}
		
		return list.toArray(new Descriptor[list.size()]);
	}
	
	/**
	 * 执行添加{@link R}
	 * @param registarion  
	 * 		{@link R}
	 * @return
	 * 		添加成功返回true,否则返回false.
	 */
	protected abstract void doAdd(R registration);
	
	/**
	 * 执行移除{@link R}
	 * @param registarion  
	 * 		{@link R}
	 * @return
	 * 		移除成功返回true,否则返回false.
	 */
	protected abstract void doRemove(R registration);
	
	/**
	 * 获取所有注册的{@link R}
	 * @return 
	 * 		{@link Collection},不能返回null.
	 */
	protected abstract Collection<R> getRegistrations();
	
}
