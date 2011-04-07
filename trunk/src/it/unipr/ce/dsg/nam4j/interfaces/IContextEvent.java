package it.unipr.ce.dsg.nam4j.interfaces;

import it.unipr.ce.dsg.nam4j.impl.context.ContextEvent;

public interface IContextEvent extends IStatement{

	/**
     * set the Context Event name.
     *
     * @param name.
     */
	void setName(String name);
	
	/**
     * get the Context Event name.
     *
     * @return the name of the Context Event.
     */
	String getName();
	
	/**
     * set the Context Event id.
     *
     * @param id.
     */
	void setId(String id);
	
	/**
     * get the Context Event id.
     *
     * @return the id of the Context Event.
     */
	String getId();
	
	/**
     * set the timestamp for the Context Event formatted in.
     *
     * @param timestamp.
     */
	void setTimestamp(String timestamp);
	
	/**
     * get the Timestamp for the Context Event formatted in .
     *
     * @return the Timestamp of the Context Event.
     */
	String getTimestamp();
	
	/**
     * set the Temporal Validity for the Context Event.
     * 
     * @param temporalValidity.
     */
	void setTemporalValidity(String temporalValidity);
	
	/**
     * get the the Temporal Validity for the Context Event.
     *
     * @return the Temporal Validity of the Context Event.
     */
	String getTemporalValidity();
	
	/**
     * Validate the Context Event.
     *
     * @return the value indicating the validation of the Context Event.
     */
	Boolean validateContextEvent(ContextEvent contextEvent);
	
	/**
     * return the producer of the Context Event.
     *
     * @return the Context Event Producer's id.
     */
	String getContextEventProducer();
	
	/**
     * set the producer of the Context Event.
     *
     * @param the Producer's id.
     */
	void setContextEventProducer(String producerId);
}
