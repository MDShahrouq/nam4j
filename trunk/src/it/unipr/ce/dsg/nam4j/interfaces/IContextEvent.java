package it.unipr.ce.dsg.nam4j.interfaces;

import it.unipr.ce.dsg.nam4j.impl.context.ContextEvent;

/**
 * <p>
 * This interface represents a context event.
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

public interface IContextEvent extends IStatement{
	
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
