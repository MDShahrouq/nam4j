package it.unipr.ce.dsg.examples.demonam;

import it.unipr.ce.dsg.examples.chordfm.ChordFunctionalModule;
import it.unipr.ce.dsg.examples.reasonerfm.ReasonerFunctionalModule;
import it.unipr.ce.dsg.examples.sensorfm.SensorFunctionalModule;
import it.unipr.ce.dsg.examples.taskmanagerfm.TaskManagerFunctionalModule;
import it.unipr.ce.dsg.examples.taskmanagerfm.UPCPFTaskDescriptor;
import it.unipr.ce.dsg.nam4j.impl.NetworkedAutonomicMachine;

public class DemoNam extends NetworkedAutonomicMachine {

	ChordFunctionalModule cfm = null;
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
		demonam.getTmfm().addTaskDescriptor(amiTask);
		demonam.getTmfm().startTaskManagement();
		
		if (args[0].equals("NOTIFICATION")) 
			demonam.getSfm().startTemperatureNotification();
		else if (args[0].equals("LOOKUP"))
			demonam.getRfm().startTemperatureNotificationLookup();
	}
}
