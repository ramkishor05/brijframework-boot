package org.brijframework.context;

import org.brijframework.container.bootstrap.BootstrapContainer;
import org.brijframework.context.impl.bootstrap.AbstractBootstrapContext;
import org.brijframework.util.factories.ReflectionFactory;
import org.brijframework.util.reflect.InstanceUtil;

public class ApplicationContext extends AbstractBootstrapContext {

	public ApplicationContext() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init() {
		try {
			ReflectionFactory.getFactory().getClassListFromExternal().forEach(cls -> {
				if (BootstrapContainer.class.isAssignableFrom(cls) && InstanceUtil.isAssignable(cls)) {
					register((Class<? extends BootstrapContainer>) cls);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ReflectionFactory.getFactory().getClassListFromInternal().forEach(cls -> {
				if (BootstrapContainer.class.isAssignableFrom(cls) && InstanceUtil.isAssignable(cls)) {
					register((Class<? extends BootstrapContainer>) cls);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
