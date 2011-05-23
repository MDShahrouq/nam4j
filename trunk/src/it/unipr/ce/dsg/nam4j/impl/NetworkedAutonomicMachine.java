package it.unipr.ce.dsg.nam4j.impl;

import java.util.HashMap;

import it.unipr.ce.dsg.nam4j.impl.resource.ResourceDescriptor;
import it.unipr.ce.dsg.nam4j.interfaces.INetworkedAutonomicMachine;

public abstract class NetworkedAutonomicMachine implements INetworkedAutonomicMachine {

	String id = "networkedAutonomicMachine";
	String name = "Networked Autonomic Machine";

	HashMap<String,FunctionalModule> functionalModules = new HashMap<String,FunctionalModule>();
	HashMap<String,ResourceDescriptor> resourceDescriptors = new HashMap<String,ResourceDescriptor>();

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void addFunctionalModule(FunctionalModule functionalModule) {
		functionalModules.put(functionalModule.getId(),functionalModule);
	}

	public void removeFunctionalModule(String id) {
		functionalModules.remove(id);
	}

	public HashMap<String,FunctionalModule> getFunctionalModules() {
		return functionalModules;
	}

	public FunctionalModule getFunctionalModule(String id) {
		return functionalModules.get(id);
	}

	public HashMap<String,ResourceDescriptor> getResources() {
		return resourceDescriptors;
	}

	public ResourceDescriptor getResource(String id) {
		return resourceDescriptors.get(id);
	}
	
	public void removeResource(String id) {
		resourceDescriptors.remove(id);
	}
}
