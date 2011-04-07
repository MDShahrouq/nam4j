package it.unipr.ce.dsg.nam4j.interfaces;

import it.unipr.ce.dsg.nam4j.impl.service.Service;

import java.util.HashMap;

public interface IServiceConsumer {

	/**
     * set the Service Consumer name.
     *
     * @param name.
     */
	void setName(String name);
	
	/**
     * get the Service Consumer name.
     *
     * @return the name of the Service Consumer.
     */
	String getName();
	
	/**
     * set the Service Consumer id.
     *
     * @param id.
     */
	void setId(String id);
	
	/**
     * get the Service Consumer id.
     *
     * @return the id of the Service Consumer.
     */
	String getId();
	
	/**
     * get the Services List from Service Consumer.
     *
     * @return the List of Services.
     */
	HashMap<String, Service> getServices();
	
	/**
     * get the Service from available Services by id.
     *
     * @param id
     * @return the Service.
     */
	Service getService(String id);

}
