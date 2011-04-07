package it.unipr.ce.dsg.nam4j.impl.resource;

import it.unipr.ce.dsg.nam4j.interfaces.IResourceDescriptor;

public abstract class ResourceDescriptor implements IResourceDescriptor {

	String id = "resourceDescriptor";
	String name = "ResourceDescriptor";
	
	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		return this.id.equals(((ResourceDescriptor)obj).getId());
	}
}