package it.unipr.ce.dsg.nam4j.interfaces;

/**
 * <p>
 * This interface represents a parameter, 
 * which may be a service input or output, 
 * but in general any ontology entity.
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

public interface IParameter {
	
	/**
     * get the parameter name
     *
     * @return the name of the parameter
     */
	String getName();
	
	/**
     * set the parameter id
     *
     * @param id
     */
	void setId(String id);
	
	/**
     * get the parameter id
     *
     * @return the id of the parameter
     */
	String getId();
	
	/**
     * set the parameter value
     *
     * @param value
     */
	void setValue(String value);
	
	/**
     * get the parameter value
     *
     * @return the value of the parameter
     */
	String getValue();
	
}
