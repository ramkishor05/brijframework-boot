package org.brijframework.context.integration;

import org.brijframework.context.ApplicationContext;
import org.brijframework.factories.impl.AbstractFactory;

public class ApplicationContextFactory extends AbstractFactory<String, ApplicationContext> {
	
	private ApplicationArgs arguments= new ApplicationArgs();
	private static ApplicationContextFactory factory;

	private ApplicationContextFactory() {
	}

	public static ApplicationContextFactory getFactory(String[] varArgs) {
		if (factory == null) {
			factory = new ApplicationContextFactory();
			factory.getArguments().initial(varArgs);
			factory.loadFactory();
		}
		return factory;
	}

	public static ApplicationContextFactory getFactory() {
		return getFactory(new String[]{}) ;
	}
	
	public ApplicationArgs getArguments() {
		return arguments;
	}

	@Override
	public ApplicationContextFactory loadFactory() {
		ApplicationContext context = new ApplicationContext();
		context.start();
		this.register(ApplicationContext.class.getName(), context);
		this.register(ApplicationContext.class.getSimpleName(), context);
		return this;
	}

	@Override
	protected void preregister(String key, ApplicationContext value) {

	}

	@Override
	protected void postregister(String key, ApplicationContext value) {

	}
	
	public ApplicationContext getApplicationContext(){
		return getCache().get(ApplicationContext.class.getName());
	}

}
