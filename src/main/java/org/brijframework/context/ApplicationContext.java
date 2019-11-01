package org.brijframework.context;

import org.brijframework.context.bootstrap.impl.AbstractBootstrapContext;
import org.brijframework.model.context.ModelContext;
import org.brijframework.util.reflect.InstanceUtil;
import org.brijframework.util.reflect.ReflectionUtils;

public class ApplicationContext extends AbstractBootstrapContext {

	public ApplicationContext() {

	}

	@SuppressWarnings("unchecked")
	@Override
	public void init() {
		try {
			ReflectionUtils.getClassListFromExternal().forEach(cls -> {
				if (ModuleContext.class.isAssignableFrom(cls) && InstanceUtil.isAssignable(cls)) {
					register((Class<? extends ModuleContext>) cls);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ReflectionUtils.getClassListFromInternal().forEach(cls -> {
				if (ModuleContext.class.isAssignableFrom(cls) && InstanceUtil.isAssignable(cls)) {
					register((Class<? extends ModuleContext>) cls);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start() {
		System.err.println("=============================ApplicationContext startup==============================");
		super.start();
		System.err.println("=============================ApplicationContext started==============================");
	}

	@Override
	public void stop() {
		System.err.println("=============================ApplicationContext startup==============================");

		System.err.println("=============================ApplicationContext started==============================");

	}

	

}
