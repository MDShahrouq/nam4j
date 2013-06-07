package it.unipr.ce.dsg.nam4j.interfaces;

import it.unipr.ce.dsg.nam4j.impl.service.Service;

import java.util.HashMap;

public interface IServiceConsumer {

	/**
     * Adds a consumable service to the hashmap.
     *
     * @param id
     * @param service
     */
	void addConsumableService(String id, Service service);
	
	/**
     * Removes a consumable service, given its id in the hash map.
     *
     * @param id
     */
	void removeConsumableService(String id);
	
	/**
     * Returns the list of consumable services.
     *
     * @return the list of consumable services
     */
	HashMap<String, Service> getConsumableServices();
	
	/**
     * Returns the consumable service, given its id in the hash map.
     *
     * @param id
     * @return the consumable service
     */
	Service getConsumableService(String id);

}
