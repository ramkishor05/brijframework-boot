package org.brijframework.boot.factories;

import org.brijframework.boot.context.BootstrapContextImpl;
import org.brijframework.boot.context.args.BootstrapArgs;
import org.brijframework.context.bootstrap.BootstrapContext;
import org.brijframework.factories.impl.AbstractFactory;
import org.brijframework.support.factories.SingletonFactory;
import org.brijframework.util.factories.ReflectionFactory;
import org.brijframework.util.printer.LoggerConsole;
import org.brijframework.util.reflect.InstanceUtil;

public class BootstrapContextFactory extends AbstractFactory<String, BootstrapContext> {
	
	private BootstrapArgs arguments= new BootstrapArgs();
	private static BootstrapContextFactory factory;

	private BootstrapContextFactory() {
	}

	@SingletonFactory
	public static BootstrapContextFactory getFactory(String[] varArgs) {
		if (factory == null) {
			factory = new BootstrapContextFactory();
			factory.getArguments().initial(varArgs);
			factory.loadFactory();
		}
		return factory;
	}

	public static BootstrapContextFactory getFactory() {
		return getFactory(new String[]{}) ;
	}
	
	public BootstrapArgs getArguments() {
		return arguments;
	}

	@Override
	public BootstrapContextFactory loadFactory() {
		try {
			LoggerConsole.banner();
			LoggerConsole.screen("Application startup luncher");
			LoggerConsole.screen(this.getClass().getSimpleName(), "Lunching the factory to start the ApplicationContext");
			ReflectionFactory.getFactory().getClassListFromExternal().forEach(applicationContextClass -> {
				if (BootstrapContext.class.isAssignableFrom(applicationContextClass) && InstanceUtil.isAssignable(applicationContextClass)) {
					BootstrapContext context = (BootstrapContext) InstanceUtil.getInstance(applicationContextClass);
					context.start();
					this.register(BootstrapContext.class.getName(), context);
					this.register(BootstrapContext.class.getSimpleName(), context);
				}
			});
			ReflectionFactory.getFactory().getClassListFromInternal().forEach(applicationContextClass -> {
				if (BootstrapContextImpl.class.isAssignableFrom(applicationContextClass) && InstanceUtil.isAssignable(applicationContextClass)) {
					BootstrapContext context = (BootstrapContext) InstanceUtil.getInstance(applicationContextClass);
					context.start();
					this.register(BootstrapContext.class.getName(), context);
					this.register(BootstrapContext.class.getSimpleName(), context);
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
	protected void preregister(String key, BootstrapContext value) {
		LoggerConsole.screen("ApplicationContext", "Lunching the factory to start the ApplicationContext");
	}

	@Override
	protected void postregister(String key, BootstrapContext value) {

	}
	
	public BootstrapContext getApplicationContext(){
		return getCache().get(BootstrapContext.class.getName());
	}

}
