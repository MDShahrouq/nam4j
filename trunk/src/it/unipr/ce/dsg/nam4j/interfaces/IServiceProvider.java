package it.unipr.ce.dsg.nam4j.interfaces;

import it.unipr.ce.dsg.nam4j.impl.service.Service;

import java.util.HashMap;

public interface IServiceProvider {

	/**
     * set the Service Provider name.
     *
     * @param name.
     */
	void setName(String name);
	
	/**
     * get the Service Provider name.
     *
     * @return the name of the Service Provider.
     */
	String getName();
	
	/**
     * set the Service Provider id.
     *
     * @param id.
     */
	void setId(String id);
	
	/**
     * get the Service Provider id.
     *
     * @return the id of the Service Provider.
     */
	String getId();
	
	/**
     * get the Services List from Service Provider.
     *
     * @return the list of Services.
     */
	HashMap<String, Service> getServices();
	
	/**
     * add Service to Service Provider.
     *
     *@param service.
     */
	void addService(Service service);
	
	/**
     * remove Service from Service Provider by id.
     *
     *@param id.
     */
	void removeService(String id);
}
