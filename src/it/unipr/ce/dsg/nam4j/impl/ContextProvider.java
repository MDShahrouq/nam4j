package it.unipr.ce.dsg.nam4j.impl;

import java.util.HashMap;

import it.unipr.ce.dsg.nam4j.impl.context.ContextEvent;
import it.unipr.ce.dsg.nam4j.interfaces.IContextProvider;

public abstract class ContextProvider implements IContextProvider {

	String id = "contextProvider";
	String name = "Context Provider";
	
	HashMap<String,ContextEvent> contextEvents = new HashMap<String,ContextEvent>();
	
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
	public HashMap<String,ContextEvent> getContextEvents(){
		return contextEvents;
	}

	@Override
	public void removeContextEvent(String id) {
		contextEvents.remove(id);
	}
}
