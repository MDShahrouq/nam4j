package it.unipr.ce.dsg.nam4j.impl;

import java.util.HashMap;
import it.unipr.ce.dsg.nam4j.interfaces.IFunctionalModule;

public abstract class FunctionalModule implements IFunctionalModule {

	String id = "functionalModule";
	String name = "Functional Module";
	
	HashMap<String,ContextConsumer> contextConsumers = new HashMap<String,ContextConsumer>();
	HashMap<String,ContextProvider> contextProviders = new HashMap<String,ContextProvider>();
	HashMap<String,ServiceConsumer> serviceConsumers = new HashMap<String,ServiceConsumer>();
	HashMap<String,ServiceProvider> serviceProviders = new HashMap<String,ServiceProvider>();

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return id;
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
	public ContextConsumer getContextConsumer(String id) {
		return (contextConsumers.get(contextConsumers.get(id)));
	}

	@Override
	public void addContextConsumer(ContextConsumer contextConsumer) throws Exception {
			this.contextConsumers.put(contextConsumer.getId(),contextConsumer);
	}

	@Override
	public void removeContextConsumer(String id) {
		contextConsumers.remove(id);
	}

	@Override
	public HashMap<String,ContextConsumer> getContextConsumers() {
		return contextConsumers;
	}

	@Override
	public HashMap<String,ContextProvider> getContextProviders() {
		return contextProviders;
	}

	@Override
	public ContextProvider getContextProvider(String id) {
		return contextProviders.get(contextProviders.get(id));
	}

	@Override
	public void addContextProvider(ContextProvider contextProvider) {
		contextProviders.put(contextProvider.getId(),contextProvider);
	}

	@Override
	public void removeContextProvider(String id) {
		contextProviders.remove(id);
	}

	@Override
	public HashMap<String,ServiceConsumer> getServiceConsumers() {
		return serviceConsumers;
	}

	@Override
	public ServiceConsumer getServiceConsumer(String id) {
		return serviceConsumers.get(serviceConsumers.get(id));
	}

	@Override
	public void addServiceConsumer(ServiceConsumer serviceConsumer) {
		serviceConsumers.put(serviceConsumer.getId(),serviceConsumer);	
	}

	@Override
	public void removeServiceConsumer(String id) {
		serviceConsumers.remove(id);
	}

	@Override
	public HashMap<String,ServiceProvider> getServiceProviders() {
		return serviceProviders;
	}

	@Override
	public ServiceProvider getServiceProvider(String id) {
		return serviceProviders.get(serviceProviders.get(id));
	}

	@Override
	public void addServiceProvider(ServiceProvider serviceProvider) {
		serviceProviders.put(serviceProvider.getId(),serviceProvider);
	}

	@Override
	public void removeServiceProvider(String id) {
		serviceProviders.remove(id);
	}
}
