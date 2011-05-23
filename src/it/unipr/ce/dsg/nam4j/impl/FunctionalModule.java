package it.unipr.ce.dsg.nam4j.impl;

import it.unipr.ce.dsg.nam4j.interfaces.IFunctionalModule;
import it.unipr.ce.dsg.nam4j.impl.context.ContextEvent;
import it.unipr.ce.dsg.nam4j.impl.service.Service;
import java.util.HashMap;


public abstract class FunctionalModule implements IFunctionalModule {

	String id = "functionalModule";
	String name = "Functional Module";
	
	HashMap<String,ContextEvent> consumableContextEvents = new HashMap<String,ContextEvent>();
	HashMap<String,ContextEvent> providedContextEvents = new HashMap<String,ContextEvent>();
	HashMap<String,Service> consumableServices = new HashMap<String,Service>();
	HashMap<String,Service> providedServices = new HashMap<String,Service>();
	
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}

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

}
