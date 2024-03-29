package it.unipr.ce.dsg.nam4j.impl.service;

import it.unipr.ce.dsg.nam4j.interfaces.IEffect;

/**
 * <p>
 * This class represents the effect of a service.
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

public class Effect implements IEffect {

	String id = "effect";
	String name = "Effect";
	
	/**
     * constructor
     */
	public Effect() {
	}
	
	/**
     * set the effect name
     *
     * @param name
     */
	public void setName(String name) {
		this.name = name;
	}

	/**
     * get the effect name
     *
     * @return the name of the effect
     */
	public String getName() {
		return name;
	}

	/**
     * set the effect id
     *
     * @param id
     */
	public void setId(String id) {
		this.id = id;
	}

	/**
     * get the effect id
     *
     * @return the id of the effect
     */
	public String getId() {
		return id;
	}

	/**
     * compares two effects
     * 
     * @param effect
     * @return true if the effect's id is equal to the one of the callee 
     */
	public boolean equals(Effect effect) {
		return this.id.equals(effect.getId());
	}
}

