package org.brijframework.boot.context;

import org.brijframework.container.bootstrap.BootstrapContainer;
import org.brijframework.context.impl.bootstrap.AbstractBootstrapContext;
import org.brijframework.util.factories.ReflectionFactory;
import org.brijframework.util.reflect.InstanceUtil;

public class BootstrapContextImpl extends AbstractBootstrapContext {

	public BootstrapContextImpl() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init() {
		try {
			ReflectionFactory.getFactory().getExternalClassList().forEach(cls -> {
				if (BootstrapContainer.class.isAssignableFrom(cls) && InstanceUtil.isAssignable(cls)) {
					register((Class<? extends BootstrapContainer>) cls);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ReflectionFactory.getFactory().getInternalClassList().forEach(cls -> {
				if (BootstrapContainer.class.isAssignableFrom(cls) && InstanceUtil.isAssignable(cls)) {
					register((Class<? extends BootstrapContainer>) cls);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
