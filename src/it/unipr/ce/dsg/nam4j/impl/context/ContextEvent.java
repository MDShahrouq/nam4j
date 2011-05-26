package it.unipr.ce.dsg.nam4j.impl.context;

import it.unipr.ce.dsg.nam4j.impl.service.Parameter;
import it.unipr.ce.dsg.nam4j.interfaces.IContextEvent;


public abstract class ContextEvent implements IContextEvent {

	String id = "contextEvent";
	String name = "Context Event";
	String timestamp = null;
	String temporalValidity = null;
	String producerId = null;
	Parameter subject = null;
	Parameter object = null;
	Parameter action = null;
	Parameter location = null;
	
	
	public Parameter getSubject() {
		return subject;
	}

	public void setSubject(Parameter subject) {
		this.subject = subject;

	}

	public Parameter getObject() {
		return object;
	}

	public void setObject(Parameter object) {
		this.object = object;

	}

	public Parameter getLocation() {
		return location;
	}

	public void setLocation(Parameter location) {
		this.location = location;

	}

	public Parameter getAction() {
		return action;
	}

	public void setAction(Parameter action) {
		this.action = action;

	}

	protected void setName(String name) {
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

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTemporalValidity(String temporalValidity) {
		this.temporalValidity = temporalValidity;
	}

	public String getTemporalValidity() {
		return this.temporalValidity;
	}

	public Boolean validateContextEvent(ContextEvent contextEvent) {
		if(this.subject == null || this.action == null)
			return false;
		else
			return true;
	}

	public String getContextEventProducer() {
		return producerId;
	}

	public void setContextEventProducer(String producerId) {
		this.producerId = producerId;
		
	}

	public boolean equals(Object obj) {
		return this.id.equals(((ContextEvent)obj).getId());
	}
	
}
