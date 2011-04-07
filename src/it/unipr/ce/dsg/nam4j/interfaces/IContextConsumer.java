package it.unipr.ce.dsg.nam4j.interfaces;

import it.unipr.ce.dsg.nam4j.impl.context.ContextEvent;

import java.util.HashMap;

public interface IContextConsumer {

	/**
     * set the Context Consumer name.
     *
     * @param name.
     */
	void setName(String name);
	
	/**
     * get the Context Consumer name.
     *
     * @return the name of the Context Consumer.
     */
	String getName();
	
	/**
     * set the Context Consumer id.
     *
     * @param id.
     */
	void setId(String id);
	
	/**
     * get the Context Consumer id.
     *
     * @return the id of the Context Consumer.
     */
	String getId();
	
	/**
     * get the Context Events List from Context Consumer.
     *
     * @return the List of Context Events.
     */
	HashMap<String, ContextEvent> getContextEvents();
	
	/**
     * get the Context Event from available Context Events by id.
     *
     * @param id
     * @return the Context Event.
     */
	ContextEvent getContextEvent(String id);
}
