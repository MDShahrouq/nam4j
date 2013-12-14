package it.unipr.ce.dsg.nam4j.impl.task;

import it.unipr.ce.dsg.nam4j.interfaces.ITaskDescriptor;

import java.util.ArrayList;

/**
 * <p>
 * This class represents a task descriptor.
 * </p>
 * 
 * <p>
 *  Copyright (c) 2011, Distributed Systems Group, University of Parma, Italy.
 *  Permission is granted to copy, distribute and/or modify this document
 *  under the terms of the GNU Free Documentation License, Version 1.3
 *  or any later version published by the Free Software Foundation;
 *  with no Invariant Sections, no Front-Cover Texts, and no Back-Cover Texts.
 *  A copy of the license is included in the section entitled "GNU
 *  Free Documentation License".
 * </p>
 * 
 * @author Michele Amoretti (michele.amoretti@unipr.it)
 * @author Alessandro Grazioli (grazioli@ce.unipr.it)
 * 
 */

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
	
	/**
     * get the list of allowed states.
     *
     * @return the list of allowed states.
     */
	public ArrayList<String> getAllowedStates() {
		return allowedStates;
	}

	/**
     * set the list of allowed states.
     *
     * @param allowedStates.
     */
	public void setAllowedStates(ArrayList<String> allowedStates) {
		this.allowedStates = allowedStates;
	}

}
