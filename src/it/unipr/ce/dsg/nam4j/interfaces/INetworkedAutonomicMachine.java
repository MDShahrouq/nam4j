package it.unipr.ce.dsg.nam4j.interfaces;

import it.unipr.ce.dsg.nam4j.impl.FunctionalModule;
import it.unipr.ce.dsg.nam4j.impl.resource.ResourceDescriptor;

import java.util.HashMap;

public interface INetworkedAutonomicMachine {

	/**
     * set the NAM name.
     *
     * @param name.
     */
	void setName(String name);
	
	/**
     * get the NAM name.
     *
     * @return the name of the NAM.
     */
	String getName();
	
	/**
     * set the NAM id.
     *
     * @param id.
     */
	void setId(String id);
	
	/**
     * get the NAM id.
     *
     * @return the id of the NAM.
     */
	String getId();
	
	/**
     * add Functional Module to NAM node.
     *
     * @param functionalModule.
     */
	void addFunctionalModule(FunctionalModule functionalModule);
	
	/**
     * remove Functional Module from NAM node by id.
     *
     * @param id.
     */
	void removeFunctionalModule(String id);

	/**
     * get the Functional Module Map from NAM node.
     *
     * @return the Map of Functional Modules of the NAM node.
     */
	HashMap<String, FunctionalModule> getFunctionalModules();
	
	/**
     * get Functional Module from NAM node by id.
     *
     * @param id.
     * @return the Functional Module object.
     */
	FunctionalModule getFunctionalModule(String id);
	
	/**
     * get the Resources List from NAM node.
     *
     * @return the Resources's ids of the NAM node.
     */
	HashMap<String, ResourceDescriptor> getResources();
	
	/**
     * get the Resource from NAM node by id.
     *
     * @param id.
     * @return the Resource of the NAM node.
     */
	ResourceDescriptor getResource(String id);

	/**
     * remove the Resource from NAM node.
     *
     * @param id.
     */
	void removeResource(String id);
	
}
