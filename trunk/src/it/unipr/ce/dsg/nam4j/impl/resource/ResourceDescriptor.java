package it.unipr.ce.dsg.nam4j.impl.resource;

import it.unipr.ce.dsg.nam4j.interfaces.IResourceDescriptor;

/**
 * <p>
 * This class represents a resource descriptor.
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

public class ResourceDescriptor implements IResourceDescriptor {

	String name = "ResourceDescriptor";
	String id = "ResourceId";
	
	/**
     * constructor
     */
	public ResourceDescriptor() {
	}
	
	/**
     * set the resource name
     *
     * @param name
     */
	public void setName(String name) {
		this.name = name;
	}

	/**
     * get the resource name
     *
     * @return the name of the resource
     */
	public String getName() {
		return name;
	}

	/**
     * set the resource id
     *
     * @param id
     */
	public void setId(String id) {
		this.id = id;
	}

	/**
     * get the resource id
     *
     * @return the id of the resource
     */
	public String getId() {
		return id;
	}

	/**
     * compares two resource descriptors
     * 
     * @param resourceDescriptor
     * @return true if the resourceDescriptor's id is equal to the one of the callee 
     */
	public boolean equals(ResourceDescriptor resourceDescriptor) {
		return this.id.equals(resourceDescriptor.getId());
	}
}