package it.unipr.ce.dsg.examples.taskmanagerfm;

import java.util.ArrayList;

import it.unipr.ce.dsg.nam4j.impl.task.TaskDescriptor;

public class UPCPFTaskDescriptor extends TaskDescriptor {

	/*
	public UPCPFTaskDescriptor(String name, String id,
			ArrayList<String> allowedStates) {
		super(name, id, allowedStates);
	}
	*/
	
	public UPCPFTaskDescriptor(String name, String id) {
		super(name, id);
		ArrayList<String> allowedStates = new ArrayList<String>();
		allowedStates.add("UNSTARTED");
		allowedStates.add("PROCESSING");
		allowedStates.add("COMPLETED");
		allowedStates.add("PAUSED");
		allowedStates.add("FAILED");
		this.setAllowedStates(allowedStates);
	}

}
