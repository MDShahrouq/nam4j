package it.unipr.ce.dsg.examples.migration;

import it.unipr.ce.dsg.nam4j.impl.FunctionalModule;
import it.unipr.ce.dsg.nam4j.impl.NetworkedAutonomicMachine;

public class TestFunctionalModule extends FunctionalModule {
	
	public TestFunctionalModule(NetworkedAutonomicMachine nam) {
		super(nam);
		this.setId("testfm");
		this.setName("TestFunctionalModule");
		
		System.out.println("\nI am " + this.getId() + " and I own to " + nam.getId());
	}

}