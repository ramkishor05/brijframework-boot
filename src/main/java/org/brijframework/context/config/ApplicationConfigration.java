package org.brijframework.context.config;

import java.util.Properties;

import org.brijframework.config.EnvConfigration;

public class ApplicationConfigration implements EnvConfigration{
	
	Properties  properties;
	
	@Override
	public Properties getProperties() {
		if(properties==null) {
			properties=new Properties();
		}
		return properties;
	}

	
}
