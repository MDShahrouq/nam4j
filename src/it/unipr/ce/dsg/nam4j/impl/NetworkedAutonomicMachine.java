
/**
Ê* 
Ê* This is the main class of nam4j.
Ê* 
Ê* @author Michele Amoretti (michele.amoretti@unipr.it)
 * @author Marco Muro
 * 
 * This file is part of nam4j.
 *
 * nam4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * nam4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with nam4j. If not, see <http://www.gnu.org/licenses/>.
Ê* 
Ê*/


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
