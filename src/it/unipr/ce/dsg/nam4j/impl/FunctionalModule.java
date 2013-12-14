package it.unipr.ce.dsg.nam4j.impl;

import it.unipr.ce.dsg.nam4j.impl.context.ContextEvent;
import it.unipr.ce.dsg.nam4j.impl.service.Service;
import it.unipr.ce.dsg.nam4j.interfaces.IFunctionalModule;

import java.util.HashMap;

/**
 * <p>
 * This class represents a functional module.
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

public abstract class FunctionalModule implements IFunctionalModule {

	NetworkedAutonomicMachine nam = null;
	String id = "functionalModule";
	String name = "Functional Module";
	
	HashMap<String,ContextEvent> consumableContextEvents = new HashMap<String,ContextEvent>();
	HashMap<String,ContextEvent> providedContextEvents = new HashMap<String,ContextEvent>();
	HashMap<String,Service> consumableServices = new HashMap<String,Service>();
	HashMap<String,Service> providedServices = new HashMap<String,Service>();
	
	/**
	 * Class constructor.
	 * 
	 * @param nam a reference to the NAM whose the functional module has been added
	 */
	public FunctionalModule(NetworkedAutonomicMachine nam) {
		this.nam = nam;
	}
	
	/**
	 * Returns a reference to the NAM the functional module belongs to.
	 * 
	 * @return a reference to the NAM the functional module belongs to
	 */
	public NetworkedAutonomicMachine getNam() {
		return nam;
	}

	/**
	 * Sets the NAM the functional module belongs to.
	 * 
	 * @param a reference to the NAM the functional module belongs to
	 */
	public void setNam(NetworkedAutonomicMachine nam) {
		this.nam = nam;
	}
	
	/**
	 * Sets the identifier of the functional module.
	 * 
	 * @param id a String identifying the functional module
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the identifier of the functional module.
	 * 
	 * @return a String identifying the functional module
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the name of the functional module.
	 * 
	 * @param name a String identifying the name of the functional module
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the name of the functional module.
	 * 
	 * @return a String identifying the name of the functional module
	 */
	public String getName() {
		return name;
	}
	
	
	/*
	 * Implementation of IContextConsumer methods
	 */
	
	public void addConsumableContextEvent(String id, ContextEvent contextEvent) {
		consumableContextEvents.put(id, contextEvent);
	}
	
	public void removeConsumableContextEvent(String id) {
		consumableContextEvents.remove(id);
	} 
	
	public HashMap<String,ContextEvent> getConsumableContextEvents(){
		return consumableContextEvents;
	}
	
	public ContextEvent getConsumableContextEvent(String id) {
		return consumableContextEvents.get(id);
	}
	
	
	/*
	 * Implementation of IContextProvider methods
	 */
	
	public void addProvidedContextEvent(String id, ContextEvent contextEvent) {
		providedContextEvents.put(id, contextEvent);
	}
	
	public void removeProvidedContextEvent(String id) {
		providedContextEvents.remove(id);
	}
	
	public HashMap<String,ContextEvent> getProvidedContextEvents(){
		return providedContextEvents;
	}
	
	public ContextEvent getProvidedContextEvent(String id) {
		return providedContextEvents.get(id);
	}
	
	
	/*
	 * Implementation of IServiceConsumer methods
	 */
	
	public void addConsumableService(String id, Service service) {
		consumableServices.put(id, service);
	}
	
	public void removeConsumableService(String id) {
		consumableServices.remove(id);
	} 
	
	public HashMap<String,Service> getConsumableServices(){
		return consumableServices;
	}
	
	public Service getConsumableService(String id) {
		return consumableServices.get(id);
	}
	
	
	/*
	 * Implementation of IServiceProvider methods
	 */
	
	public void addProvidedService(String id, Service service) {
		providedServices.put(id, service);
	}
	
	public void removeProvidedService(String id) {
		providedServices.remove(id);
	}
	
	public HashMap<String, Service> getProvidedServices() {
		return providedServices;
	}
	
	public Service getProvidedService(String id) {
		return providedServices.get(id);
	}
	
	
	/**
	 * Service execution method
	 * 
	 * @param requestorId a String identifying the requestor of the service
	 * @param requestedService a String identifying the requested service
	 * @param parameters
	 */
	public void execute(String requestorId, String requestedService, String parameters) {
	}

	/**
	 * ContextEvent reception method
	 * 
	 * @param contextEvent a ContextEvent
	 */
	public void receive(ContextEvent contextEvent) {
	}
}
