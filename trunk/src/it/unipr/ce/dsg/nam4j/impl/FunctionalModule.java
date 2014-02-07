package it.unipr.ce.dsg.nam4j.impl;

import it.unipr.ce.dsg.nam4j.impl.context.ContextEvent;
import it.unipr.ce.dsg.nam4j.impl.service.Service;
import it.unipr.ce.dsg.nam4j.interfaces.IFunctionalModule;
import it.unipr.ce.dsg.nam4j.interfaces.IService;

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
	HashMap<String,IService> consumableServices = new HashMap<String,IService>();
	HashMap<String,IService> providedServices = new HashMap<String,IService>();
	
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
	
	/**
     * Adds a consumable context event to the hashmap.
     *
     * @param id
     * @param contextEvent
     */
	public void addConsumableContextEvent(String id, ContextEvent contextEvent) {
		consumableContextEvents.put(id, contextEvent);
	}
	
	/**
     * Removes a consumable context event, given its id in the hash map.
     *
     * @param id
     */
	public void removeConsumableContextEvent(String id) {
		consumableContextEvents.remove(id);
	} 
	
	/**
     * Returns the list of context events.
     *
     * @return the list of context events
     */
	public HashMap<String,ContextEvent> getConsumableContextEvents(){
		return consumableContextEvents;
	}
	
	/**
     * Returns the consumable context event, given its id in the hash map.
     *
     * @param id
     * @return the consumable context event
     */
	public ContextEvent getConsumableContextEvent(String id) {
		return consumableContextEvents.get(id);
	}
	
	
	/*
	 * Implementation of IContextProvider methods
	 */
	
	/**
     * Adds a provided context event to the hashmap.
     *
     * @param id
     * @param contextEvent
     */
	public void addProvidedContextEvent(String id, ContextEvent contextEvent) {
		providedContextEvents.put(id, contextEvent);
	}
	
	/**
     * Removes a provided context event, given its id in the hash map.
     *
     * @param id
     */
	public void removeProvidedContextEvent(String id) {
		providedContextEvents.remove(id);
	}
	
	/**
     * Returns the list of provided context events.
     *
     * @return the list of provided context events
     */
	public HashMap<String,ContextEvent> getProvidedContextEvents(){
		return providedContextEvents;
	}
	
	/**
     * Returns the provided context event, given its id in the hash map.
     *
     * @param id
     * @return the provided context event
     */
	public ContextEvent getProvidedContextEvent(String id) {
		return providedContextEvents.get(id);
	}
	
	
	/*
	 * Implementation of IServiceConsumer methods
	 */
	
	/**
     * Adds a consumable service to the hashmap.
     *
     * @param id
     * @param service
     */
	public void addConsumableService(String id, Service service) {
		consumableServices.put(id, service);
	}
	
	/**
     * Removes a consumable service, given its id in the hash map.
     *
     * @param id
     */
	public void removeConsumableService(String id) {
		consumableServices.remove(id);
	} 
	
	/**
     * Returns the list of consumable services.
     *
     * @return the list of consumable services
     */
	public HashMap<String,IService> getConsumableServices(){
		return consumableServices;
	}
	
	/**
     * Returns the consumable service, given its id in the hash map.
     *
     * @param id
     * @return the consumable service
     */
	public IService getConsumableService(String id) {
		return consumableServices.get(id);
	}
	
	
	/*
	 * Implementation of IServiceProvider methods
	 */
	
	/**
     * Adds a provided service to the hashmap.
     * 
     * @param id
     * @param service.
     */
	public void addProvidedService(String id, Service service) {
		providedServices.put(id, service);
	}
	
	/**
     * Removes a provided service, given its id in the hash map.
     *
     * @param id.
     */
	public void removeProvidedService(String id) {
		providedServices.remove(id);
	}
	
	/**
     * Returns the list of provided services.
     *
     * @return the list of provided services
     */
	public HashMap<String, IService> getProvidedServices() {
		return providedServices;
	}
	
	/**
     * Returns the provided service, given its id in the hash map.
     *
     * @param id
     * @return the provided service
     */
	public IService getProvidedService(String id) {
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
