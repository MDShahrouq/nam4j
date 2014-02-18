package it.unipr.ce.dsg.gamidroid.gaminode;

import it.unipr.ce.dsg.gamidroid.buildingfm.BuildingFunctionalModule;
import it.unipr.ce.dsg.gamidroid.chordfm.ChordFunctionalModule;
import it.unipr.ce.dsg.gamidroid.reasonerfm.ReasonerFunctionalModule;
import it.unipr.ce.dsg.gamidroid.sensorfm.SensorFunctionalModule;
import it.unipr.ce.dsg.gamidroid.taskmanagerfm.TaskManagerFunctionalModule;
import it.unipr.ce.dsg.gamidroid.taskmanagerfm.UPCPFTaskDescriptor;
import it.unipr.ce.dsg.nam4j.impl.NetworkedAutonomicMachine;
import it.unipr.ce.dsg.s2pchord.msg.MessageListener;
import it.unipr.ce.dsg.s2pchord.resource.ResourceListener;

import org.w3c.dom.Document;

import android.os.Environment;

public class GamiNode extends NetworkedAutonomicMachine {

	public static String TAG = "AndroidDemoNam";
	private static GamiNode androidDemoNam;
	private TaskManagerFunctionalModule tmfm = null;
	private ReasonerFunctionalModule rfm = null;
	private static ChordFunctionalModule cfm = null;
	private static BuildingFunctionalModule bfm = null;
	private static SensorFunctionalModule sfm = null;

	private static NetworkedAutonomicMachine thisNam;

	private static String pathToSaveFile = Environment
			.getExternalStorageDirectory().toString() + "/";

	private GamiNode(String configuration, String confFile) {

		super(10, pathToSaveFile, 3);

		// PeerConfig peerConfig = new PeerConfig(confFile);

		this.setId("demonam");

		thisNam = this;

		cfm = new ChordFunctionalModule(this);
		this.addFunctionalModule(cfm);

		tmfm = new TaskManagerFunctionalModule(this);
		this.addFunctionalModule(tmfm);

		if (configuration.equals("LOOKUP")) {
			rfm = new ReasonerFunctionalModule(this);
			this.addFunctionalModule(rfm);
		}
	}

	public static void addResourceListener(ResourceListener rl) {
		cfm.addResourceListener(rl);
	}

	public static void addMessageListener(MessageListener ml) {
		cfm.addMessageListener(ml);
	}

	/* Leave Chord ring */
	public static void disconnect() {
		cfm.disconnect();
		androidDemoNam = null;
	}

	public ChordFunctionalModule getCfm() {
		return cfm;
	}

	public void setCfm(ChordFunctionalModule cfm) {
		GamiNode.cfm = cfm;
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

	public static GamiNode getAndroidDemoNam() {
		if (androidDemoNam == null) {
			androidDemoNam = new GamiNode("LOOKUP", null);
		}
		return androidDemoNam;
	}

	public static void publishBuilding(Document xml) {

		UPCPFTaskDescriptor amiTask = new UPCPFTaskDescriptor("AmITask", "T1");
		amiTask.setState("UNSTARTED");
		bfm = new BuildingFunctionalModule(thisNam);
		thisNam.addFunctionalModule(bfm);

		bfm.startBuildingNotificationFromMobile(xml);
		amiTask.addProcessingService("Publish");
	}

	public static void publishSensor(String address, String lat, String lng,
			String name, String floor, String room, String value) {

		UPCPFTaskDescriptor amiTask = new UPCPFTaskDescriptor("AmITask", "T1");
		amiTask.setState("UNSTARTED");
		sfm = new SensorFunctionalModule(thisNam);
		thisNam.addFunctionalModule(sfm);

		sfm.startTemperatureNotification(address, floor, room, name, value,
				lat, lng);
		amiTask.addProcessingService("Publish");
	}

}
