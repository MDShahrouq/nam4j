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

	public void startTemperatureNotification(String buildingName,
			String floorName, String roomName, String sensorName,
			String temperatureValue, String latitude, String longitude) {

		System.out
				.println("\n*************************************************************");
		System.out
				.println("I'm SensorFM and I'm starting to notify:\n* building: "
						+ buildingName
						+ "\n* floor: "
						+ floorName
						+ "\n* room: "
						+ roomName
						+ "\n* sensor: "
						+ sensorName
						+ "\n* value: " + temperatureValue);
		System.out
				.println("*************************************************************");

		Thread t = new Thread(new ProvideTemperatureRunnable(this,
				buildingName, floorName, roomName, sensorName,
				temperatureValue, latitude, longitude),
				"Provide temperature thread");
		t.start();
	}
}
