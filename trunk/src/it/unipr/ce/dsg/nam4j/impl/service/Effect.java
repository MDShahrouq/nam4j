package it.unipr.ce.dsg.nam4j.impl.service;

import it.unipr.ce.dsg.nam4j.interfaces.IEffect;

public abstract class Effect implements IEffect {

	String id = "effect";
	String name = "Effect";
	
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
		return this.id.equals(((Effect)obj).getId());
	}
}

