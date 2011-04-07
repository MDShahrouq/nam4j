package it.unipr.ce.dsg.nam4j.impl;

import java.util.HashMap;

import it.unipr.ce.dsg.nam4j.impl.resource.ResourceDescriptor;
import it.unipr.ce.dsg.nam4j.interfaces.INetworkedAutonomicMachine;

public abstract class NetworkedAutonomicMachine implements INetworkedAutonomicMachine {

	String id = "networkedAutonomicMachine";
	String name = "Networked Autonomic Machine";

	HashMap<String,FunctionalModule> functionalModules = new HashMap<String,FunctionalModule>();
	HashMap<String,ResourceDescriptor> resourceDescriptors = new HashMap<String,ResourceDescriptor>();

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
	public void addFunctionalModule(FunctionalModule functionalModule) {
		functionalModules.put(functionalModule.getId(),functionalModule);
	}

	@Override
	public void removeFunctionalModule(String id) {
		functionalModules.remove(id);
	}

	@Override
	public HashMap<String,FunctionalModule> getFunctionalModules() {
		return functionalModules;
	}

	@Override
	public FunctionalModule getFunctionalModule(String id) {
		return functionalModules.get(id);
	}

	@Override
	public HashMap<String,ResourceDescriptor> getResources() {
		return resourceDescriptors;
	}

	@Override
	public ResourceDescriptor getResource(String id) {
		return resourceDescriptors.get(id);
	}
	
	@Override
	public void removeResource(String id) {
		resourceDescriptors.remove(id);
	}
}
