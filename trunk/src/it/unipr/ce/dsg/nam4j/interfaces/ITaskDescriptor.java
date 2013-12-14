package it.unipr.ce.dsg.nam4j.interfaces;

import java.util.ArrayList;

/**
 * <p>
 * This interface represents a task descriptor.
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
 * 
 */

public interface ITaskDescriptor {

	/**
     * set the Task name.
     *
     * @param name.
     */
	void setName(String name);
	
	/**
     * get the Task name.
     *
     * @return the name of the Task.
     */
	String getName();
	
	/**
     * set the Task id.
     *
     * @param id.
     */
	void setId(String id);
	
	/**
     * get the Task id.
     *
     * @return the id of the Task.
     */
	String getId();
	
	/**
     * set the Task state.
     *
     * @param state.
     */
	void setState(String state);
	
	/**
     * get the Task state.
     *
     * @return the state of the Task.
     */
	String getState();

	/**
     * set the Task allowed states.
     *
     * @param allowedStates.
     */
	public void setAllowedStates(ArrayList<String> allowedStates);
	
	/**
     * get the Task allowed states.
     *
     * @return the allowed states of the Task.
     */
	public ArrayList<String> getAllowedStates();
	
}
