package it.unipr.ce.dsg.examples.sensorfm;

import it.unipr.ce.dsg.nam4j.impl.FunctionalModule;
import it.unipr.ce.dsg.nam4j.impl.NetworkedAutonomicMachine;


public class SensorFunctionalModule extends FunctionalModule {
	
	private SensorLogger sLogger = null;

	public SensorFunctionalModule(NetworkedAutonomicMachine nam) {
		super(nam);
		this.setId("sfm");
		this.setName("SensorFunctionalModule");
		this.sLogger = new SensorLogger("log/");
		sLogger.log("I am " + this.getId() + " and I own to " + nam.getId());
	}

	public SensorLogger getLogger() {
		return sLogger;
	}
	
	public void startTemperatureNotification(String locationName, String temperatureValue) {
		Thread t = new Thread(new ProvideTemperatureRunnable(this, locationName, temperatureValue), "Provide temperature thread");
		t.start();
	}
}
