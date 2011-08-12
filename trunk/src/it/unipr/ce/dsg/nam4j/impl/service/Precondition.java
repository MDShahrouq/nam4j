package it.unipr.ce.dsg.nam4j.impl.service;

import it.unipr.ce.dsg.nam4j.interfaces.IPrecondition;

public class Precondition implements IPrecondition {

	String id = "precondition";
	String name = "Precondition";
	
	public Precondition() {
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

	public boolean equals(Object obj) {
		return this.id.equals(((Precondition)obj).getId());
	}
}

