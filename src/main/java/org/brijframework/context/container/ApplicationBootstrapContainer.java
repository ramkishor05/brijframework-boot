package org.brijframework.context.container;

import org.brijframework.container.BootstrapContainer;
import org.brijframework.container.impl.AbstractContainer;
import org.brijframework.container.impl.DefaultContainer;
import org.brijframework.context.group.BootstrapGroup;
import org.brijframework.factories.BootstrapFactory;
import org.brijframework.group.Group;
import org.brijframework.support.config.Assignable;
import org.brijframework.util.reflect.InstanceUtil;
import org.brijframework.util.reflect.ReflectionUtils;

public class ApplicationBootstrapContainer extends AbstractContainer implements DefaultContainer, BootstrapContainer {

	private static ApplicationBootstrapContainer container;

	@Assignable
	public static ApplicationBootstrapContainer getContainer() {
		if (container == null) {
			container = new ApplicationBootstrapContainer();
		}
		return container;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() {
		try {
			ReflectionUtils.getClassListFromExternal().forEach(cls -> {
				if (BootstrapFactory.class.isAssignableFrom(cls) && InstanceUtil.isAssignable(cls)) {
					register((Class<? extends BootstrapFactory>) cls);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ReflectionUtils.getClassListFromInternal().forEach(cls -> {
				if (BootstrapFactory.class.isAssignableFrom(cls) && InstanceUtil.isAssignable(cls)) {
					register((Class<? extends BootstrapFactory>) cls);
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
			System.err.println("Group        : " + groupKey);
			this.add(groupKey, group);
		}
		return group;
	}

}
