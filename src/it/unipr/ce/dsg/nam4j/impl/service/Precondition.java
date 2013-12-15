package it.unipr.ce.dsg.nam4j.impl.service;

import it.unipr.ce.dsg.nam4j.interfaces.IPrecondition;

/**
 * <p>
 * This class represents the precondition of a service.
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

public class Precondition implements IPrecondition {

	String id = "precondition";
	String name = "Precondition";
	
	/**
     * constructor
     */
	public Precondition() {
	}
	
	/**
     * set the precondition name
     *
     * @param name
     */
	public void setName(String name) {
		this.name = name;
	}

	/**
     * get the precondition name
     *
     * @return the name of the precondition
     */
	public String getName() {
		return name;
	}

	/**
     * set the precondition id
     *
     * @param id
     */
	public void setId(String id) {
		this.id = id;
	}

	/**
     * get the precondition id
     *
     * @return the id of the precondition
     */
	public String getId() {
		return id;
	}

	/**
     * compares two preconditions
     * 
     * @param precondition
     * @return true if the precondition's id is equal to the one of the callee 
     */
	public boolean equals(Precondition precondition) {
		return this.id.equals(precondition.getId());
	}
}

