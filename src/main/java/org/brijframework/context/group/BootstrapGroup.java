package org.brijframework.context.group;

import java.util.concurrent.ConcurrentHashMap;

import org.brijframework.group.impl.DefaultGroup;

public class BootstrapGroup implements DefaultGroup {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ConcurrentHashMap<String, Object> cache=new ConcurrentHashMap<String, Object>();

	private Object groupKey;

	public BootstrapGroup(Object groupKey) {
		this.groupKey=groupKey;
	}

	@Override
	public Object getGroupKey() {
		return groupKey;
	}

	@Override
	public ConcurrentHashMap<String, Object> getCache() {
		return cache;
	}

	@Override
	public <T> T find(String parentID, Class<?> type) {
		return null;
	}

}
