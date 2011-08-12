package it.unipr.ce.dsg.nam4j.impl.resource;

import it.unipr.ce.dsg.nam4j.interfaces.IResourceDescriptor;

public class ResourceDescriptor implements IResourceDescriptor {

	String id = "resourceDescriptor";
	String name = "ResourceDescriptor";
	
	public ResourceDescriptor() {
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		return this.id.equals(((ResourceDescriptor)obj).getId());
	}
}