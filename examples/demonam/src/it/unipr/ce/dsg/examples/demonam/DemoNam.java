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
	
	public DemoNam(String configuration) {
		
		super(10); // The parameter is the thread pool size
		
		this.setId("demonam");
		cfm = new ChordFunctionalModule(this);
		this.addFunctionalModule(cfm);
		tmfm = new TaskManagerFunctionalModule(this);
		this.addFunctionalModule(tmfm);
		if (configuration.equals("LOOKUP")) {
			rfm = new ReasonerFunctionalModule(this);
			this.addFunctionalModule(rfm);
		}
		else if (configuration.equals("NOTIFICATION")) {
			sfm = new SensorFunctionalModule(this);
			this.addFunctionalModule(sfm);
		}
	}
	
	public static void main(String[] args) {
		DemoNam demonam = new DemoNam(args[0]);
		
		UPCPFTaskDescriptor amiTask = new UPCPFTaskDescriptor("AmITask", "T1");
		amiTask.setState("UNSTARTED");
			
		/*
		 * args[0]: either NOTIFICATION or LOOKUP
		 * args[1]: a location name, e.g. Building1-Apartment3-Kitchen
		 * args[2]: a temperature value, e.g. 22
		 */
		
		if (args[0].equals("NOTIFICATION")) {
			demonam.getSfm().startTemperatureNotification(args[1], args[2]);
			amiTask.addProcessingService("Publish");
		}
		else if (args[0].equals("LOOKUP")) { 
			demonam.getRfm().startTemperatureNotificationLookup(args[3]);
			amiTask.addProcessingService("Lookup");
		}
		
		demonam.getTmfm().addTaskDescriptor(amiTask);
		demonam.getTmfm().startTaskManagement();
	}
}
