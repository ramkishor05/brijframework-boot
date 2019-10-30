package org.brijframework.context.integration;

import org.brijframework.context.ApplicationContext;
import org.brijframework.factories.impl.AbstractFactory;

public class ApplicationBoot extends AbstractFactory<String, ApplicationContext> {
	
	private ApplicationArgs arguments= new ApplicationArgs();
	static ApplicationBoot integration;

	private ApplicationBoot() {
	}

	public static ApplicationBoot bootstraps(String[] varArgs) {
		if (integration == null) {
			integration = new ApplicationBoot();
			integration.getArguments().initial(varArgs);
			integration.loadFactory();
		}
		return integration;
	}

	public static ApplicationBoot bootstraps() {
		if (integration == null) {
			integration = new ApplicationBoot();
			integration.loadFactory();
		}
		return integration;
	}
	
	public ApplicationArgs getArguments() {
		return arguments;
	}

	@Override
	public ApplicationBoot loadFactory() {
		ApplicationContext context = new ApplicationContext();
		context.load();
		context.init();
		context.start();
		this.register(ApplicationContext.class.getName(), context);
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
