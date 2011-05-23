package it.unipr.ce.dsg.nam4j.interfaces;

import it.unipr.ce.dsg.nam4j.impl.context.ContextEvent;
import java.util.HashMap;

public interface IContextProvider {
	
	/**
     * Adds a provided context event to the hashmap.
     *
     * @param id
     * @param contextEvent
     */
	void addProvidedContextEvent(String id, ContextEvent contextEvent);
	
	/**
     * Removes a provided context event, given its id in the hash map.
     *
     * @param id
     */
	void removeProvidedContextEvent(String id);
	
	/**
     * Returns the list of provided context events.
     *
     * @return the list of provided context events
     */
	HashMap<String, ContextEvent> getProvidedContextEvents();
	
	/**
     * Returns the provided context event, given its id in the hash map.
     *
     * @param id
     * @return the provided context event
     */
	ContextEvent getProvidedContextEvent(String id);
}
