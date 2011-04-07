package it.unipr.ce.dsg.nam4j.impl.service;

import it.unipr.ce.dsg.nam4j.interfaces.IInput;

public abstract class Input implements IInput {

	String id = "input";
	String name = "Input";
	
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
		return this.id.equals(((Input)obj).getId());
	}

}
