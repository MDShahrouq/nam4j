package it.unipr.ce.dsg.examples.buildingfm;

import it.unipr.ce.dsg.nam4j.impl.FunctionalModule;
import it.unipr.ce.dsg.nam4j.impl.NetworkedAutonomicMachine;

public class BuildingFunctionalModule extends FunctionalModule {

	public BuildingFunctionalModule(NetworkedAutonomicMachine nam) {
		super(nam);
		this.setId("bfm");
		this.setName("BuildingFunctionalModule");

	}

	public void startBuildingNotification ( ){
		Thread t = new Thread(new ProvideBuildingRunnable(this), "Provide Building thread");
		t.start();
	}
}
