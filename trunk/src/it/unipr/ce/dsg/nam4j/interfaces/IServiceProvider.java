package it.unipr.ce.dsg.nam4j.interfaces;

import it.unipr.ce.dsg.nam4j.impl.service.Service;
import java.util.HashMap;

public interface IServiceProvider {

	/**
     * Adds a provided service to the hashmap.
     * 
     * @param id
     * @param service.
     */
	void addProvidedService(String id, Service service);
	
	/**
     * Removes a provided service, given its id in the hash map.
     *
     * @param id.
     */
	void removeProvidedService(String id);
	
	/**
     * Returns the list of provided services.
     *
     * @return the list of provided services
     */
	HashMap<String, Service> getProvidedServices();
	
	/**
     * Returns the provided service, given its id in the hash map.
     *
     * @param id
     * @return the provided service
     */
	Service getProvidedService(String id);
	
}
