package it.unipr.ce.dsg.nam4j.impl.service;

import it.unipr.ce.dsg.nam4j.interfaces.IPrecondition;

public abstract class Precondition implements IPrecondition {

	String id = "precondition";
	String name = "Precondition";
	
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
		return this.id.equals(((Precondition)obj).getId());
	}
}

