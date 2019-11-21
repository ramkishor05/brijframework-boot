package org.brijframework.context;

import org.brijframework.container.BootstrapContainer;
import org.brijframework.context.bootstrap.impl.AbstractBootstrapContext;
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
				if (BootstrapContainer.class.isAssignableFrom(cls) && InstanceUtil.isAssignable(cls)) {
					register((Class<? extends BootstrapContainer>) cls);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ReflectionUtils.getClassListFromInternal().forEach(cls -> {
				if (BootstrapContainer.class.isAssignableFrom(cls) && InstanceUtil.isAssignable(cls)) {
					register((Class<? extends BootstrapContainer>) cls);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
