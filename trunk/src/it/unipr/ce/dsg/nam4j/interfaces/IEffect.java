package it.unipr.ce.dsg.nam4j.interfaces;

/**
 * <p>
 * This interface represents the effect of a service.
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
 * @author Marco Muro 
 * @author Michele Amoretti (michele.amoretti@unipr.it)
 * 
 */

public interface IEffect {

	/**
     * set the Effect name.
     *
     * @param name.
     */
	void setName(String name);
	
	/**
     * get the Effect name.
     *
     * @return the name of the Effect.
     */
	String getName();
	
	/**
     * set the Effect id.
     *
     * @param id.
     */
	void setId(String id);
	
	/**
     * get the Effect id.
     *
     * @return the id of the Effect.
     */
	String getId();
}
