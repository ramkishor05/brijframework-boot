package org.brijframework.context.factories;

import org.brijframework.context.ApplicationContext;
import org.brijframework.context.args.ApplicationArgs;
import org.brijframework.factories.impl.AbstractFactory;
import org.brijframework.support.config.SingletonFactory;
import org.brijframework.util.factories.ReflectionFactory;
import org.brijframework.util.printer.LoggerConsole;
import org.brijframework.util.reflect.InstanceUtil;

public class ApplicationContextFactory extends AbstractFactory<String, ApplicationContext> {
	
	private ApplicationArgs arguments= new ApplicationArgs();
	private static ApplicationContextFactory factory;

	private ApplicationContextFactory() {
	}

	@SingletonFactory
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
		try {
			LoggerConsole.banner();
			LoggerConsole.screen("Application startup luncher");
			LoggerConsole.screen(this.getClass().getSimpleName(), "Lunching the factory to start the ApplicationContext");
			ReflectionFactory.getFactory().getClassListFromExternal().forEach(applicationContextClass -> {
				if (ApplicationContext.class.isAssignableFrom(applicationContextClass) && InstanceUtil.isAssignable(applicationContextClass)) {
					ApplicationContext context = (ApplicationContext) InstanceUtil.getInstance(applicationContextClass);
					context.start();
					this.register(ApplicationContext.class.getName(), context);
					this.register(ApplicationContext.class.getSimpleName(), context);
				}
			});
			ReflectionFactory.getFactory().getClassListFromInternal().forEach(applicationContextClass -> {
				if (ApplicationContext.class.isAssignableFrom(applicationContextClass) && InstanceUtil.isAssignable(applicationContextClass)) {
					ApplicationContext context = (ApplicationContext) InstanceUtil.getInstance(applicationContextClass);
					context.start();
					this.register(ApplicationContext.class.getName(), context);
					this.register(ApplicationContext.class.getSimpleName(), context);
				}
			});
			LoggerConsole.screen(this.getClass().getSimpleName(), "Lunched the factory to start the ApplicationContext");
		} catch (Exception e) {
			e.printStackTrace();
			LoggerConsole.screen(this.getClass().getSimpleName(), "Error to lunch the factory to start the ApplicationContext");
		}
		LoggerConsole.screen("Application Successfully started");
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
