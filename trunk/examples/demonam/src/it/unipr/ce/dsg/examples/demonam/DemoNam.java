package it.unipr.ce.dsg.examples.demonam;

import it.unipr.ce.dsg.examples.buildingfm.BuildingFunctionalModule;
import it.unipr.ce.dsg.examples.chordfm.ChordFunctionalModule;
import it.unipr.ce.dsg.examples.reasonerfm.ReasonerFunctionalModule;
import it.unipr.ce.dsg.examples.sensorfm.SensorFunctionalModule;
import it.unipr.ce.dsg.examples.taskmanagerfm.TaskManagerFunctionalModule;
import it.unipr.ce.dsg.examples.taskmanagerfm.UPCPFTaskDescriptor;
import it.unipr.ce.dsg.nam4j.impl.NetworkedAutonomicMachine;

public class DemoNam extends NetworkedAutonomicMachine {

	ChordFunctionalModule cfm = null;
	TaskManagerFunctionalModule tmfm = null;
	ReasonerFunctionalModule rfm = null;
	SensorFunctionalModule sfm = null;
	BuildingFunctionalModule bfm = null;

	public ChordFunctionalModule getCfm() {
		return cfm;
	}

	public void setCfm(ChordFunctionalModule cfm) {
		this.cfm = cfm;
	}

	public TaskManagerFunctionalModule getTmfm() {
		return tmfm;
	}

	public void setTmfm(TaskManagerFunctionalModule tmfm) {
		this.tmfm = tmfm;
	}

	public ReasonerFunctionalModule getRfm() {
		return rfm;
	}

	public void setRfm(ReasonerFunctionalModule rfm) {
		this.rfm = rfm;
	}

	public SensorFunctionalModule getSfm() {
		return sfm;
	}

	public void setSfm(SensorFunctionalModule sfm) {
		this.sfm = sfm;
	}

	public DemoNam(String configuration, String sp2ConfFilePath) {
		super(10, "", 3);

		this.setId("mccdemonam");

		cfm = new ChordFunctionalModule(this);
		this.addFunctionalModule(cfm);

		tmfm = new TaskManagerFunctionalModule(this);
		this.addFunctionalModule(tmfm);

		if (configuration.equals("LOOKUP")
				|| configuration.equals("BUILDINGLOOKUP")) {

			rfm = new ReasonerFunctionalModule(this);
			this.addFunctionalModule(rfm);

		} else if (configuration.equals("NOTIFICATION")) {

			sfm = new SensorFunctionalModule(this);
			this.addFunctionalModule(sfm);

		} else if (configuration.equals("BUILDINGNOTIFICATION")) {

			bfm = new BuildingFunctionalModule(this);
			this.addFunctionalModule(bfm);
		}

	}

	
	/* main function arguments:
	 * 
	 * args[0]: either NOTIFICATION / LOOKUP / BUILDINGNOTIFICATION /
	 * 			BUILDINGLOOKUP
	 * 
	 * args[1]: a building address
	 * 
	 * args[2]: a floor name
	 * 
	 * args[3]: a room name
	 * 
	 * args[4]: a sensor name
	 * 
	 * args[5]: the sensor value
	 * 
	 * args[6]: a latitude value
	 * 
	 * args[7]: a longitude value
	 * 
	 */
	public static void main(String[] args) {
		
		DemoNam mccdemonam = new DemoNam(args[0], "config/chordPeer.cfg");

		UPCPFTaskDescriptor amiTask = new UPCPFTaskDescriptor("AmITask", "T1");
		amiTask.setState("UNSTARTED");

		if (args[0].equals("NOTIFICATION")) {

			mccdemonam.getSfm().startTemperatureNotification(args[1], args[2],
					args[3], args[4], args[5], args[6], args[7]);
			amiTask.addProcessingService("Publish");

		} else if (args[0].equals("LOOKUP")) {

			mccdemonam.getRfm().startTemperatureNotificationLookup(args[1],
					args[2], args[3], args[4]);
			amiTask.addProcessingService("Lookup");

		} else if (args[0].equals("BUILDINGNOTIFICATION")) {

			mccdemonam.getBfm().startBuildingNotification();
			amiTask.addProcessingService("Publish");

		} else if (args[0].equals("BUILDINGLOOKUP")) {

			mccdemonam.getRfm().startBuildingNotificationLookup(args[1]);
			amiTask.addProcessingService("Lookup");
		}

		mccdemonam.getTmfm().addTaskDescriptor(amiTask);
		mccdemonam.getTmfm().startTaskManagement();
	}

	public BuildingFunctionalModule getBfm() {
		return bfm;
	}

	public void setBfm(BuildingFunctionalModule bfm) {
		this.bfm = bfm;
	}
}
