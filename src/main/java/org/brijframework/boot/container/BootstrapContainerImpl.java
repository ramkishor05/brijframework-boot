package org.brijframework.boot.container;

import org.brijframework.boot.group.BootstrapGroup;
import org.brijframework.container.impl.bootstrap.AbstractBootstrapContainer;
import org.brijframework.factories.bootstrap.BootstrapFactory;
import org.brijframework.group.Group;
import org.brijframework.support.factories.SingletonFactory;
import org.brijframework.util.factories.ReflectionFactory;
import org.brijframework.util.reflect.InstanceUtil;

public class BootstrapContainerImpl extends AbstractBootstrapContainer {

	private static BootstrapContainerImpl container;

	@SingletonFactory
	public static BootstrapContainerImpl getContainer() {
		if (container == null) {
			container = new BootstrapContainerImpl();
		}
		return container;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() {
		try {
			ReflectionFactory.getFactory().getExternalClassList().forEach(cls -> {
				if (BootstrapFactory.class.isAssignableFrom(cls) && InstanceUtil.isAssignable(cls)) {
					register((Class<? extends BootstrapFactory<?,?>>) cls);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ReflectionFactory.getFactory().getInternalClassList().forEach(cls -> {
				if (BootstrapFactory.class.isAssignableFrom(cls) && InstanceUtil.isAssignable(cls)) {
					register((Class<? extends BootstrapFactory<?,?>>) cls);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Group load(Object groupKey) {
		Group group = get(groupKey);
		if (group == null) {
			group = new BootstrapGroup(groupKey);
			this.add(groupKey, group);
		}
		return group;
	}

}
