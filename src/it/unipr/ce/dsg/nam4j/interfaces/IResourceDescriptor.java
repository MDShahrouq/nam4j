package it.unipr.ce.dsg.nam4j.interfaces;

/**
 * <p>
 * This interface represents a resource descriptor.
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

public interface IResourceDescriptor {

	/**
     * set the resource name
     *
     * @param name
     */
	void setName(String name);
	
	/**
     * get the resource name
     *
     * @return the name of the resource
     */
	String getName();
	
	/**
     * set the resource id
     *
     * @param id
     */
	void setId(String id);
	
	/**
     * get the resource id
     *
     * @return the id of the resource
     */
	String getId();

}
