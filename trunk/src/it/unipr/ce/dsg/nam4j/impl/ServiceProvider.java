package it.unipr.ce.dsg.nam4j.impl;

import java.util.HashMap;

import it.unipr.ce.dsg.nam4j.impl.service.Service;
import it.unipr.ce.dsg.nam4j.interfaces.IServiceProvider;

public abstract class ServiceProvider implements IServiceProvider {

	String id = "serviceProvider";
	String name = "Service Provider";
	
	HashMap<String,Service> services = new HashMap<String,Service>();
	
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
	public HashMap<String,Service> getServices() {
		return services;
	}

	@Override
	public void addService(Service service) {
		services.put(service.getId(),service);
	}

	@Override
	public void removeService(String id) {
		services.remove(id);
	}
}
