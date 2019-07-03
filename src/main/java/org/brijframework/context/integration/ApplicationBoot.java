package org.brijframework.context.integration;

import org.brijframework.asm.factories.AbstractFactory;
import org.brijframework.context.ApplicationContext;

public class ApplicationBoot extends AbstractFactory<String, Object>{
	
	static ApplicationBoot integration;
	String[] args;
	private ApplicationBoot() {
	}
	
	public static ApplicationBoot bootstraps(String[] args) {
		if(integration==null) {
			integration=new ApplicationBoot();
			integration.loadFactory();
		}
		return integration;
	} 
	
	public static ApplicationBoot bootstraps() {
		if(integration==null) {
			integration=new ApplicationBoot();
			integration.loadFactory();
		}
		return integration;
	} 

	@Override
	public ApplicationBoot loadFactory() {
		ApplicationContext context = new ApplicationContext();
		context.load();
		context.init();
		context.start();
		System.out.println(""+context.hashCode());
		this.register(""+context.hashCode(), context);
		return this;
	}

	@Override
	protected void preregister(String key, Object value) {
		
	}

	@Override
	protected void postregister(String key, Object value) {
		
	}

}
