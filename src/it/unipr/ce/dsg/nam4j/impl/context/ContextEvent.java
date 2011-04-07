package it.unipr.ce.dsg.nam4j.impl.context;

import java.text.SimpleDateFormat;

import it.unipr.ce.dsg.nam4j.interfaces.IContextEvent;

public abstract class ContextEvent implements IContextEvent {
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'T' HH:mm:ss z");
	
	String id = "contextEvent";
	String name = "Context Event";
	String timestamp = null;
	String producerId = null;
	
	Object subject = null;
	Object object = null;
	Object action = null;
	Object location = null;
	
	@Override
	public Object getSubject() {
		return subject;
	}

	@Override
	public void setSubject(Object subject) {
		this.subject = subject;

	}

	@Override
	public Object getObject() {
		return object;
	}

	@Override
	public void setObject(Object object) {
		this.object = object;

	}

	@Override
	public Object getLocation() {
		return location;
	}

	@Override
	public void setLocation(Object location) {
		this.location = location;

	}

	@Override
	public Object getAction() {
		return action;
	}

	@Override
	public void setAction(Object action) {
		this.action = action;

	}

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
	public void setTimestamp(String timestamp) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getTimestamp() {
		return timestamp;
	}

	@Override
	public void setTemporalValidity(String temporalValidity) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getTemporalValidity() {
		// TODO Auto-generated method stub
		return null;
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
