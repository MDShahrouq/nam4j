package it.unipr.ce.dsg.nam4j.impl.context;

import java.text.SimpleDateFormat;
import it.unipr.ce.dsg.nam4j.impl.service.Parameter;
import it.unipr.ce.dsg.nam4j.interfaces.IContextEvent;

public abstract class ContextEvent implements IContextEvent {
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'T' HH:mm:ss z");
	
	String id = "contextEvent";
	String name = "Context Event";
	String timestamp = null;
	String temporalValidity = null;
	String producerId = null;
	
	Parameter subject = null;
	Parameter object = null;
	Parameter action = null;
	Parameter location = null;
	
	@Override
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
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String getTimestamp() {
		return timestamp;
	}

	@Override
	public void setTemporalValidity(String temporalValidity) {
		this.temporalValidity = temporalValidity;
	}

	@Override
	public String getTemporalValidity() {
		return this.temporalValidity;
	}

	@Override
	public Boolean validateContextEvent(ContextEvent contextEvent) {
		if(this.subject == null || this.action == null)
			return false;
		else
			return true;
	}

	@Override
	public String getContextEventProducer() {
		return producerId;
	}

	@Override
	public void setContextEventProducer(String producerId) {
		this.producerId = producerId;
		
	}

	@Override
	public boolean equals(Object obj) {
		return this.id.equals(((ContextEvent)obj).getId());
	}
	
}
