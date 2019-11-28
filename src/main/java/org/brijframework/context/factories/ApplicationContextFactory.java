package org.brijframework.context.factories;

import org.brijframework.context.ApplicationContext;
import org.brijframework.context.args.ApplicationArgs;
import org.brijframework.factories.impl.AbstractFactory;
import org.brijframework.support.config.SingletonFactory;
import org.brijframework.util.printer.ConsolePrint;
import org.brijframework.util.reflect.InstanceUtil;
import org.brijframework.util.reflect.ReflectionUtils;

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
			ConsolePrint.banner();
			ConsolePrint.screen("Application startup luncher");
			ConsolePrint.screen(this.getClass().getSimpleName(), "Lunching the factory to start the ApplicationContext");
			ReflectionUtils.getClassListFromExternal().forEach(applicationContextClass -> {
				if (ApplicationContext.class.isAssignableFrom(applicationContextClass) && InstanceUtil.isAssignable(applicationContextClass)) {
					ApplicationContext context = (ApplicationContext) InstanceUtil.getInstance(applicationContextClass);
					context.start();
					this.register(ApplicationContext.class.getName(), context);
					this.register(ApplicationContext.class.getSimpleName(), context);
				}
			});
			ReflectionUtils.getClassListFromInternal().forEach(applicationContextClass -> {
				if (ApplicationContext.class.isAssignableFrom(applicationContextClass) && InstanceUtil.isAssignable(applicationContextClass)) {
					ApplicationContext context = (ApplicationContext) InstanceUtil.getInstance(applicationContextClass);
					context.start();
					this.register(ApplicationContext.class.getName(), context);
					this.register(ApplicationContext.class.getSimpleName(), context);
				}
			});
			ConsolePrint.screen(this.getClass().getSimpleName(), "Lunched the factory to start the ApplicationContext");
		} catch (Exception e) {
			e.printStackTrace();
			ConsolePrint.screen(this.getClass().getSimpleName(), "Error to lunch the factory to start the ApplicationContext");
		}
		ConsolePrint.screen("Application Successfully started");
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

	@Override
	protected void loadContainer(String key, ApplicationContext value) {
		
	}


}
