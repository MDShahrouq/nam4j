package it.unipr.ce.dsg.nam4j.interfaces;

import it.unipr.ce.dsg.nam4j.impl.service.Parameter;

public interface IStatement {

	/**
     * get the Subject name.
     *
     * @return the name of the Subject.
     */
	String getName();
	
	/**
     * set the Subject id.
     *
     * @param id.
     */
	void setId(String id);
	
	/**
     * get the Subject id.
     *
     * @return the id of the Subject.
     */
	String getId();
	
	/**
     * get the Subject Content.
     *
     * @return the content of the Subject.
     */
	 Parameter getSubject();
	
	/**
     * set the Subject Content.
     *
     * @param subject.
     */
	void setSubject(Parameter subject);
	
	/**
     * get the Object Content.
     *
     * @return the content of the Object.
     */
	Parameter getObject();
	
	/**
     * set the Object Content.
     *
     * @param object.
     */
	void setObject(Parameter object);
	
	/**
     * get the Location Content.
     *
     * @return the content of the Location.
     */
	Parameter getLocation();
	
	/**
     * set the Location Content.
     *
     * @param location.
     */
	void setLocation(Parameter location);
	
	/**
     * get the Action Content.
     *
     * @return the content of the Action.
     */
	Parameter getAction();
	
	/**
     * set the Action Content.
     *
     * @param action.
     */
	void setAction(Parameter action);
}
