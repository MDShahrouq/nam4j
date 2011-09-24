package it.unipr.ce.dsg.examples.demonam;

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
	
	public DemoNam() {
		this.setId("demonam");
		cfm = new ChordFunctionalModule(this);
		this.addFunctionalModule(cfm);
		tmfm = new TaskManagerFunctionalModule(this);
		this.addFunctionalModule(tmfm);
		rfm = new ReasonerFunctionalModule(this);
		this.addFunctionalModule(rfm);
		sfm = new SensorFunctionalModule(this);
		this.addFunctionalModule(sfm);
	}
	
	public static void main(String[] args) {
		DemoNam demonam = new DemoNam();
		System.out.println("Demonam has " 
				+ demonam.getFunctionalModules().size() 
				+ " functional modules");
		
		UPCPFTaskDescriptor amiTask = new UPCPFTaskDescriptor("AmITask", "T1");
		amiTask.setState("UNSTARTED");
		amiTask.addProcessingService("Publish");
		amiTask.addProcessingService("Lookup");
		demonam.tmfm.addTaskDescriptor(amiTask);
		demonam.tmfm.startTaskManagement();
		
		if (args[0].equals("NOTIFICATION")) 
			demonam.sfm.startTemperatureNotification();
		else if (args[0].equals("LOOKUP"))
			demonam.rfm.startTemperatureNotificationLookup();
	}
}
