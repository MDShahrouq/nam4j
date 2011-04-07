package it.unipr.ce.dsg.s2p.nam4j.groups;

import it.unipr.ce.dsg.nam4j.impl.FunctionalModule;


public class GroupManager extends FunctionalModule{

	GroupServiceProvider groupServiceProvider = new GroupServiceProvider();
	
	public void start(String config,String key){
		System.out.println("Start Group Manager");
		this.addServiceProvider(groupServiceProvider);
	}
	
	
}
