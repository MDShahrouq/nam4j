package it.unipr.ce.dsg.nam4j.impl.service;

import it.unipr.ce.dsg.nam4j.interfaces.IEffect;

public class Effect implements IEffect {

	String id = "effect";
	String name = "Effect";
	
	public Effect() {
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
		return this.id.equals(((Effect)obj).getId());
	}
}

