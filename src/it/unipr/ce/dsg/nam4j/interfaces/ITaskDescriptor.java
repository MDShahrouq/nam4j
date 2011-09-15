package it.unipr.ce.dsg.nam4j.interfaces;

import java.util.ArrayList;

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
