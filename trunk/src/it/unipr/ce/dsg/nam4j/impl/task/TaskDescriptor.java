package it.unipr.ce.dsg.nam4j.impl.task;

import it.unipr.ce.dsg.nam4j.interfaces.ITaskDescriptor;

import java.util.ArrayList;

public class TaskDescriptor implements ITaskDescriptor {

	private String name = "TaskName";
	private String id = "TaskId";
	String state = "TaskState";
	ArrayList<String> allowedStates = null;
	
	public TaskDescriptor(String name, String id) {
		this.name = name;
		this.id = id;
		allowedStates = new ArrayList<String>();
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setState(String state)
		throws IllegalStateException {
		if (!allowedStates.contains(state))
			throw new IllegalStateException("Such a state is not allowed!");
		else
			this.state = state;
	}

	@Override
	public String getState() {
		return state;
	}
	
	public ArrayList<String> getAllowedStates() {
		return allowedStates;
	}

	public void setAllowedStates(ArrayList<String> allowedStates) {
		this.allowedStates = allowedStates;
	}

}
