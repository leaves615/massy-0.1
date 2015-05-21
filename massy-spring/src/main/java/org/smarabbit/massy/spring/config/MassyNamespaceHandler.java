/**
 * 
 */
package org.smarabbit.massy.spring.config;

import javax.sql.DataSource;

import org.smarabbit.massy.util.Asserts;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.cache.CacheManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.w3c.dom.Element;

/**
 * Massy Namespace 扩展处理器
 * @author huangkaihui
 *
 */
public class MassyNamespaceHandler extends NamespaceHandlerSupport {
	
	/**
	 * 
	 */
	public MassyNamespaceHandler() {
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.xml.NamespaceHandler#init()
	 */
	@Override
	public void init() {
		this.registerBeanDefinitionParser("annotation-driven", new AnnotationDrivenDefinitionParser());
		
		//export系列
		this.registerBeanDefinitionParser("export-service", new ExportServiceBeanDefinitionParser());
		this.registerBeanDefinitionParser("export-datasource", 
				new ExportSpecificBeanDefinitionParser(DataSource.class.getName()));
		this.registerBeanDefinitionParser("export-transactionmanager", 
				new ExportSpecificBeanDefinitionParser(PlatformTransactionManager.class.getName()));
		this.registerBeanDefinitionParser("export-cachemanager", 
				new ExportSpecificBeanDefinitionParser(CacheManager.class.getName()));
		
		this.registerBeanDefinitionParser("export-sqlsession", 
				new ExportSpecificBeanDefinitionParser("org.apache.ibatis.session.SqlSession"));
		this.registerBeanDefinitionParser("export-sqlsessionfactory", 
				new ExportSpecificBeanDefinitionParser("org.apache.ibatis.session.SqlSessionFactory"));
		this.registerBeanDefinitionParser("export-entitymanager", 
				new ExportSpecificBeanDefinitionParser("javax.persistence.EntityManager"));
		this.registerBeanDefinitionParser("export-entitymanagerfactory", 
				new ExportSpecificBeanDefinitionParser("javax.persistence.EntityManagerFactory"));
		this.registerBeanDefinitionParser("export-eventbus", 
				new ExportSpecificBeanDefinitionParser("org.axonframework.eventhandling.EventBus"));
		this.registerBeanDefinitionParser("export-eventstore", 
				new ExportSpecificBeanDefinitionParser("org.axonframework.eventstore.EventStore"));
		this.registerBeanDefinitionParser("export-commandbus", 
				new ExportSpecificBeanDefinitionParser("org.axonframework.commandhandling.CommandBus"));
		this.registerBeanDefinitionParser("export-disruptorcommandbus", 
				new ExportSpecificBeanDefinitionParser("org.axonframework.commandhandling.disruptor.DisruptorCommandBus"));
		this.registerBeanDefinitionParser("export-commandgateway", 
				new ExportSpecificBeanDefinitionParser("org.axonframework.commandhandling.gateway.CommandGateway"));
		this.registerBeanDefinitionParser("export-sagarepository", 
				new ExportSpecificBeanDefinitionParser("org.axonframework.saga.SagaRepository"));
		this.registerBeanDefinitionParser("export-cachedrepository", 
				new ExportSpecificBeanDefinitionParser("org.smarabbit.massy.model.repository.CachedRepository"));

		
		//Import系列
		this.registerBeanDefinitionParser("import-service", new ImportServiceBeanDefinitionParser());
		this.registerBeanDefinitionParser("import-datasource", 
				new ImportSpecificBeanDefinitionParser(DataSource.class.getName()));
		this.registerBeanDefinitionParser("import-transactionmanager", 
				new ImportSpecificBeanDefinitionParser(PlatformTransactionManager.class.getName()));
		this.registerBeanDefinitionParser("import-cachemanager", 
				new ImportSpecificBeanDefinitionParser(CacheManager.class.getName()));
		
		this.registerBeanDefinitionParser("import-sqlsession", 
				new ImportSpecificBeanDefinitionParser("org.apache.ibatis.session.SqlSession"));
		this.registerBeanDefinitionParser("import-sqlsessionfactory", 
				new ImportSpecificBeanDefinitionParser("org.apache.ibatis.session.SqlSessionFactory"));
		this.registerBeanDefinitionParser("import-entitymanager", 
				new ImportSpecificBeanDefinitionParser("javax.persistence.EntityManager"));
		this.registerBeanDefinitionParser("import-entitymanagerfactory", 
				new ImportSpecificBeanDefinitionParser("javax.persistence.EntityManagerFactory"));
		this.registerBeanDefinitionParser("import-eventbus", 
				new ImportSpecificBeanDefinitionParser("org.axonframework.eventhandling.EventBus"));
		this.registerBeanDefinitionParser("import-eventstore", 
				new ImportSpecificBeanDefinitionParser("org.axonframework.eventstore.EventStore"));
		this.registerBeanDefinitionParser("import-commandbus", 
				new ImportSpecificBeanDefinitionParser("org.axonframework.commandhandling.CommandBus"));
		this.registerBeanDefinitionParser("import-disruptorcommandbus", 
				new ImportSpecificBeanDefinitionParser("org.axonframework.commandhandling.disruptor.DisruptorCommandBus"));
		this.registerBeanDefinitionParser("import-commandgateway", 
				new ImportSpecificBeanDefinitionParser("org.axonframework.commandhandling.gateway.CommandGateway"));
		this.registerBeanDefinitionParser("import-sagarepository", 
				new ImportSpecificBeanDefinitionParser("org.axonframework.saga.SagaRepository"));
		this.registerBeanDefinitionParser("import-cachedrepository", 
				new ImportSpecificBeanDefinitionParser("org.smarabbit.massy.model.repository.CachedRepository"));
	}
	
	private class ExportSpecificBeanDefinitionParser extends ExportServiceBeanDefinitionParser {

		private final String serviceTypeName;
		
		public ExportSpecificBeanDefinitionParser(String serviceTypeName) {
			Asserts.argumentNotNull(serviceTypeName, "serviceTypeName");
			this.serviceTypeName = serviceTypeName;
		}

		/* (non-Javadoc)
		 * @see org.smarabbit.massy.spring.config.ExportServiceBeanDefinitionParser#getServiceTypes(org.w3c.dom.Element)
		 */
		@Override
		protected Class<?>[] getServiceTypes(Element element) throws Exception {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			return new Class<?>[]{loader.loadClass(this.serviceTypeName)};
		}	
		
	}
	
	private class ImportSpecificBeanDefinitionParser extends ImportServiceBeanDefinitionParser{

		private final String serviceTypeName;
		
		public ImportSpecificBeanDefinitionParser(String serviceTypeName) {
			Asserts.argumentNotNull(serviceTypeName, "serviceTypeName");
			this.serviceTypeName = serviceTypeName;
		}

		/* (non-Javadoc)
		 * @see org.smarabbit.massy.spring.config.ImportServiceBeanDefinitionParser#getServiceType(org.w3c.dom.Element)
		 */
		@Override
		protected String getServiceType(Element element) {
			return this.serviceTypeName;
		}
		
	}
	
}
