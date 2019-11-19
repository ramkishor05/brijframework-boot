package org.brijframework.context.factories;

import org.brijframework.context.ApplicationContext;
import org.brijframework.context.args.ApplicationArgs;
import org.brijframework.factories.impl.AbstractFactory;
import org.brijframework.support.config.Assignable;
import org.brijframework.util.reflect.InstanceUtil;
import org.brijframework.util.reflect.ReflectionUtils;

public class ApplicationContextFactory extends AbstractFactory<String, ApplicationContext> {
	
	private ApplicationArgs arguments= new ApplicationArgs();
	private static ApplicationContextFactory factory;

	private ApplicationContextFactory() {
	}

	@Assignable
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
			ReflectionUtils.getClassListFromExternal().forEach(applicationContextClass -> {
				if (ApplicationContext.class.isAssignableFrom(applicationContextClass) && InstanceUtil.isAssignable(applicationContextClass)) {
					ApplicationContext context = (ApplicationContext) InstanceUtil.getInstance(applicationContextClass);
					context.init();
					context.start();
					this.register(ApplicationContext.class.getName(), context);
					this.register(ApplicationContext.class.getSimpleName(), context);
				}
			});
			ReflectionUtils.getClassListFromInternal().forEach(applicationContextClass -> {
				if (ApplicationContext.class.isAssignableFrom(applicationContextClass) && InstanceUtil.isAssignable(applicationContextClass)) {
					ApplicationContext context = (ApplicationContext) InstanceUtil.getInstance(applicationContextClass);
					context.init();
					context.start();
					this.register(ApplicationContext.class.getName(), context);
					this.register(ApplicationContext.class.getSimpleName(), context);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
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
