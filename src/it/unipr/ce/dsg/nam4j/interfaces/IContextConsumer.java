package it.unipr.ce.dsg.nam4j.interfaces;

import it.unipr.ce.dsg.nam4j.impl.context.ContextEvent;

import java.util.HashMap;

public interface IContextConsumer {
	
	/**
     * Adds a consumable context event to the hashmap.
     *
     * @param id
     * @param contextEvent
     */
	void addConsumableContextEvent(String id, ContextEvent contextEvent);
	
	/**
     * Removes a consumable context event, given its id in the hash map.
     *
     * @param id
     */
	void removeConsumableContextEvent(String id);
	
	/**
     * Returns the list of context events.
     *
     * @return the list of context events
     */
	HashMap<String, ContextEvent> getConsumableContextEvents();
	
	/**
     * Returns the consumable context event, given its id in the hash map.
     *
     * @param id
     * @return the consumable context event
     */
	ContextEvent getConsumableContextEvent(String id);
	
}
