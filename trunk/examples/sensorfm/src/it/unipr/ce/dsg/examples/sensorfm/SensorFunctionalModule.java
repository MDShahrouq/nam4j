package it.unipr.ce.dsg.examples.sensorfm;

import it.unipr.ce.dsg.nam4j.impl.FunctionalModule;
import it.unipr.ce.dsg.nam4j.impl.NetworkedAutonomicMachine;


public class SensorFunctionalModule extends FunctionalModule {

	public SensorFunctionalModule(NetworkedAutonomicMachine nam) {
		super(nam);
		this.setId("sfm");
		this.setName("SensorFunctionalModule");
		System.out.println("I am " + this.getId() + " and I own to " + nam.getId());
	}

	public void startTemperatureNotification() {
		Thread t = new Thread(new ProvideTemperatureRunnable(this), "Provide temperature thread");
		t.start();
	}
}
