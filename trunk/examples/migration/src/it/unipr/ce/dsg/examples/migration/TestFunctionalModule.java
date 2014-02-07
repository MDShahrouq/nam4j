package it.unipr.ce.dsg.examples.migration;

import it.unipr.ce.dsg.nam4j.impl.FunctionalModule;
import it.unipr.ce.dsg.nam4j.impl.NetworkedAutonomicMachine;
import it.unipr.ce.dsg.nam4j.interfaces.IService;

public class TestFunctionalModule extends FunctionalModule {

	public TestFunctionalModule(NetworkedAutonomicMachine nam) {
		super(nam);
		this.setId("testfm");
		this.setName("TestFunctionalModule");

		System.out.println("\nI am " + this.getId() + " and I own to "
				+ nam.getId());
	}

	@Override
	public void addConsumableService(String id, IService service) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addProvidedService(String id, IService service) {
		// TODO Auto-generated method stub
		
	}
}