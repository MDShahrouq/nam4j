package it.unipr.ce.dsg.nam4j.interfaces;

import it.unipr.ce.dsg.nam4j.impl.context.ContextEvent;

import java.util.HashMap;

public interface IContextProvider {

	/**
     * set the Context Provider name.
     *
     * @param name.
     */
	void setName(String name);
	
	/**
     * get the Context Provider name.
     *
     * @return the name of the Context Provider.
     */
	String getName();
	
	/**
     * set the Context Provider id.
     *
     * @param id.
     */
	void setId(String id);
	
	/**
     * get the Context Provider id.
     *
     * @return the id of the Context Provider.
     */
	String getId();
	
	/**
     * get the Context Events List from Context Provider.
     *
     * @return the List of Context Events.
     */
	HashMap<String, ContextEvent> getContextEvents();
	
	/**
     * add Context Event to Context Provider.
     *
     *@param contextEvent.
     */
	void addContextEvent(ContextEvent contextEvent);
	
	/**
     * remove contextEvent from Context Provider by id.
     *
     *@param id.
     */
	void removeContextEvent(String id);
}
