package org.brijframework.context.container;

import org.brijframework.container.impl.bootstrap.AbstractBootstrapContainer;
import org.brijframework.context.group.BootstrapGroup;
import org.brijframework.factories.bootstrap.BootstrapFactory;
import org.brijframework.group.Group;
import org.brijframework.support.config.SingletonFactory;
import org.brijframework.util.reflect.InstanceUtil;
import org.brijframework.util.reflect.ReflectionUtils;

public class ApplicationContainer extends AbstractBootstrapContainer {

	private static ApplicationContainer container;

	@SingletonFactory
	public static ApplicationContainer getContainer() {
		if (container == null) {
			container = new ApplicationContainer();
		}
		return container;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() {
		try {
			ReflectionUtils.getClassListFromExternal().forEach(cls -> {
				if (BootstrapFactory.class.isAssignableFrom(cls) && InstanceUtil.isAssignable(cls)) {
					register((Class<? extends BootstrapFactory<?,?>>) cls);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ReflectionUtils.getClassListFromInternal().forEach(cls -> {
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
