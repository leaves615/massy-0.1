/**
 * 
 */
package org.smarabbit.massy.instrumentation;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.jar.JarFile;

/**
 * {@link Instrumentation}封装器，提供卸载{@link ClassFileTransformer}的简易处理，
 * 避免web application重复启动时发生{@link ClassFileTransformer}未即使卸载的场景。
 * 
 * @author huangkaihui
 *
 */
public class InstrumentationWrapper implements Instrumentation {

	private final Instrumentation inst;
	private Set<ClassFileTransformer> transformers =
			new CopyOnWriteArraySet<ClassFileTransformer>();
	
	/**
	 * 
	 */
	public InstrumentationWrapper(Instrumentation instrumentation) {
		if (instrumentation == null) throw new IllegalArgumentException("instrumentation cannot be null.");
		
		this.inst = instrumentation;
	}

	/* (non-Javadoc)
	 * @see java.lang.instrument.Instrumentation#addTransformer(java.lang.instrument.ClassFileTransformer, boolean)
	 */
	@Override
	public void addTransformer(ClassFileTransformer transformer,
			boolean canRetransform) {
		this.inst.addTransformer(transformer, canRetransform);
		transformers.add(transformer);
	}

	/* (non-Javadoc)
	 * @see java.lang.instrument.Instrumentation#addTransformer(java.lang.instrument.ClassFileTransformer)
	 */
	@Override
	public void addTransformer(ClassFileTransformer transformer) {
		this.inst.addTransformer(transformer);
		transformers.add(transformer);
	}

	/* (non-Javadoc)
	 * @see java.lang.instrument.Instrumentation#removeTransformer(java.lang.instrument.ClassFileTransformer)
	 */
	@Override
	public boolean removeTransformer(ClassFileTransformer transformer) {
		boolean result = this.transformers.remove(transformer);
		
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.instrument.Instrumentation#isRetransformClassesSupported()
	 */
	@Override
	public boolean isRetransformClassesSupported() {
		return this.inst.isRetransformClassesSupported();
	}

	/* (non-Javadoc)
	 * @see java.lang.instrument.Instrumentation#retransformClasses(java.lang.Class[])
	 */
	@Override
	public void retransformClasses(Class<?>... classes)
			throws UnmodifiableClassException {
		this.inst.retransformClasses(classes);
	}

	/* (non-Javadoc)
	 * @see java.lang.instrument.Instrumentation#isRedefineClassesSupported()
	 */
	@Override
	public boolean isRedefineClassesSupported() {
		return this.inst.isRedefineClassesSupported();
	}

	/* (non-Javadoc)
	 * @see java.lang.instrument.Instrumentation#redefineClasses(java.lang.instrument.ClassDefinition[])
	 */
	@Override
	public void redefineClasses(ClassDefinition... definitions)
			throws ClassNotFoundException, UnmodifiableClassException {
		this.inst.redefineClasses(definitions);
	}

	/* (non-Javadoc)
	 * @see java.lang.instrument.Instrumentation#isModifiableClass(java.lang.Class)
	 */
	@Override
	public boolean isModifiableClass(Class<?> theClass) {
		return this.inst.isModifiableClass(theClass);
	}

	/* (non-Javadoc)
	 * @see java.lang.instrument.Instrumentation#getAllLoadedClasses()
	 */
	@Override
	public Class<?>[] getAllLoadedClasses() {
		return this.inst.getAllLoadedClasses();
	}

	/* (non-Javadoc)
	 * @see java.lang.instrument.Instrumentation#getInitiatedClasses(java.lang.ClassLoader)
	 */
	@Override
	public Class<?>[] getInitiatedClasses(ClassLoader loader) {
		return this.inst.getInitiatedClasses(loader);
	}

	/* (non-Javadoc)
	 * @see java.lang.instrument.Instrumentation#getObjectSize(java.lang.Object)
	 */
	@Override
	public long getObjectSize(Object objectToSize) {
		return this.inst.getObjectSize(objectToSize);
	}

	/* (non-Javadoc)
	 * @see java.lang.instrument.Instrumentation#appendToBootstrapClassLoaderSearch(java.util.jar.JarFile)
	 */
	@Override
	public void appendToBootstrapClassLoaderSearch(JarFile jarfile) {
		this.inst.appendToSystemClassLoaderSearch(jarfile);
	}

	/* (non-Javadoc)
	 * @see java.lang.instrument.Instrumentation#appendToSystemClassLoaderSearch(java.util.jar.JarFile)
	 */
	@Override
	public void appendToSystemClassLoaderSearch(JarFile jarfile) {
		this.inst.appendToBootstrapClassLoaderSearch(jarfile);
	}

	/* (non-Javadoc)
	 * @see java.lang.instrument.Instrumentation#isNativeMethodPrefixSupported()
	 */
	@Override
	public boolean isNativeMethodPrefixSupported() {
		return this.inst.isNativeMethodPrefixSupported();
	}

	/* (non-Javadoc)
	 * @see java.lang.instrument.Instrumentation#setNativeMethodPrefix(java.lang.instrument.ClassFileTransformer, java.lang.String)
	 */
	@Override
	public void setNativeMethodPrefix(ClassFileTransformer transformer,
			String prefix) {
		this.inst.setNativeMethodPrefix(transformer, prefix);
	}

	/**
	 * 移除所有{@link ClassTransformer}
	 */
	public synchronized void removeAllClassFileTransformer(){
		if (this.transformers.isEmpty()) return;
		
		Iterator<ClassFileTransformer> it = this.transformers.iterator();
		while (it.hasNext()){
			ClassFileTransformer transformer = it.next();
			this.inst.removeTransformer(transformer);
		}
		this.transformers.clear();
	}
}
