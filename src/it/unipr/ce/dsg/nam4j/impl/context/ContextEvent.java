package it.unipr.ce.dsg.nam4j.impl.context;

import it.unipr.ce.dsg.nam4j.impl.service.Parameter;
import it.unipr.ce.dsg.nam4j.interfaces.IContextEvent;

/**
 * <p>
 * This class represents a context event.
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

public class ContextEvent implements IContextEvent {

	String id = "contextEvent";
	String name = "ContextEvent";
	String timestamp = null;
	String temporalValidity = null;
	String producerId = null;
	Parameter subject = null;
	Parameter object = null;
	Parameter action = null;
	Parameter location = null;
	
	
	/**
     * constructor of the context event
     */
	public ContextEvent() {
	}
	
	/**
     * get the subject of the context event
     *
     * @return the subject (a Parameter) of the context event
     */
	public Parameter getSubject() {
		return subject;
	}

	/**
     * set the subject of the context event
     *
     * @param the subject (a Parameter) of the context event
     */
	public void setSubject(Parameter subject) {
		this.subject = subject;
	}

	/**
     * get the object of the context event
     *
     * @return the object (a Parameter) of the context event
     */
	public Parameter getObject() {
		return object;
	}

	/**
     * set the object of the context event
     *
     * @param the object (a Parameter) of the context event
     */
	public void setObject(Parameter object) {
		this.object = object;
	}

	/**
     * get the location of the context event
     *
     * @return the location (a Parameter) of the context event
     */
	public Parameter getLocation() {
		return location;
	}

	/**
     * set the location of the context event
     *
     * @param the location (a Parameter) of the context event
     */
	public void setLocation(Parameter location) {
		this.location = location;
	}

	/**
     * get the action of the context event
     *
     * @return the action (a Parameter) of the context event
     */
	public Parameter getAction() {
		return action;
	}

	/**
     * set the action of the context event
     *
     * @param the action (a Parameter) of the context event
     */
	public void setAction(Parameter action) {
		this.action = action;
	}

	/**
     * set the name of the context event
     *
     * @param name (a String) of the context event
     */
	protected void setName(String name) {
		this.name = name;
	}

	/**
     * get the name of the context event
     *
     * @return name (a String) of the context event
     */
	public String getName() {
		return name;
	}

	/**
     * set the id of the context event
     *
     * @param id (a String) of the context event
     */
	public void setId(String id) {
		this.id = id;
	}

	/**
     * get the id of the context event
     *
     * @return id (a String) of the context event
     */
	public String getId() {
		return id;
	}

	/**
     * set the timestamp for the context event
     *
     * @param timestamp
     */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	/**
     * get the timestamp of the context event 
     *
     * @return the timestamp of the context event
     */
	public String getTimestamp() {
		return timestamp;
	}

	/**
     * set the temporal validity of the context event
     * 
     * @param temporalValidity
     */
	public void setTemporalValidity(String temporalValidity) {
		this.temporalValidity = temporalValidity;
	}

	/**
     * get the the temporal validity of the context event
     *
     * @return the temporal validity of the context event
     */
	public String getTemporalValidity() {
		return this.temporalValidity;
	}

	/**
     * validate the context event
     *
     * @return the value indicating the validation of the context event
     */
	public Boolean validateContextEvent(ContextEvent contextEvent) {
		if(this.subject == null || this.action == null)
			return false;
		else
			return true;
	}

	/**
     * return the producer of the context event
     *
     * @return the id of the context event producer
     */
	public String getContextEventProducer() {
		return producerId;
	}

	/**
     * set the producer of the context event
     *
     * @param the id of the context event producer
     */
	public void setContextEventProducer(String producerId) {
		this.producerId = producerId;	
	}

	/**
     * compare context events by id
     *
     * @param contextEvent
     */
	public boolean equals(ContextEvent contextEvent) {
		return this.id.equals(contextEvent.getId());
	}
	
}
