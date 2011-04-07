package it.unipr.ce.dsg.nam4j.interfaces;

import it.unipr.ce.dsg.nam4j.impl.ContextConsumer;
import it.unipr.ce.dsg.nam4j.impl.ContextProvider;
import it.unipr.ce.dsg.nam4j.impl.ServiceConsumer;
import it.unipr.ce.dsg.nam4j.impl.ServiceProvider;

import java.util.HashMap;

public interface IFunctionalModule {

	/**
     * set the Functional Module name.
     *
     * @param name.
     */
	void setName(String name);
	
	/**
     * get the Functional Module name.
     *
     * @return the name of the Functional Module.
     */
	String getName();
	
	/**
     * set the Functional Module id.
     *
     * @param id.
     */
	void setId(String id);
	
	/**
     * get the Functional Module id.
     *
     * @return the id of the Functional Module.
     */
	String getId();
	
	/**
     * get the Context Consumer List of the Functional Module.
     *
     * @return the List of Context Consumers.
     */
	HashMap<String, ContextConsumer> getContextConsumers();
	
	/**
     * get Context Consumer from collection by id.
     *
     * @param id.
     * @return the Context Consumer Object.
     */
	ContextConsumer getContextConsumer(String id);
	
	/**
     * add Context Consumer to collection.
     *
     * @param contextConsumer.
	 * @throws Exception 
     */
	void addContextConsumer(ContextConsumer contextConsumer) throws Exception;
	
	/**
     * remove Context Consumer from collection by id.
     *
     * @param id.
     */
	void removeContextConsumer(String id);

	/**
     * get the Context Provider List of the Functional Module.
     *
     * @return the List of Context Providers.
     */
	HashMap<String, ContextProvider> getContextProviders();
	
	/**
     * get Context Provider from collection by id.
     *
     * @param id.
     * @return the Context Provider Object.
     */
	ContextProvider getContextProvider(String id);
	
	/**
     * add Context Provider from collection by index.
     *
     * @param contextProvider.
     */
	void addContextProvider(ContextProvider contextProvider);
	
	/**
     * remove Context Provider from collection by id.
     *
     * @param id.
     */
	void removeContextProvider(String id);
	
	/**
     * get the Service Consumer List of the Functional Module.
     *
     * @return the List of Service Consumers.
     */
	HashMap<String, ServiceConsumer> getServiceConsumers();
	
	/**
     * get Service Consumer from collection by id.
     *
     * @param id.
     * @return the Service Consumer Object.
     */
	ServiceConsumer getServiceConsumer(String id);
	
	/**
     * add Service Consumer to collection.
     *
     * @param serviceConsumer.
     */
	void addServiceConsumer(ServiceConsumer serviceConsumer);
	
	/**
     * remove Service Consumer from collection by id.
     *
     * @param id.
     */
	void removeServiceConsumer(String id);
	
	/**
     * get the Service Provider List of the Functional Module.
     *
     * @return the List of Service Providers.
     */
	HashMap<String, ServiceProvider> getServiceProviders();
	
	/**
     * get Service Provider from collection by id.
     * @param id.
     * @return the Service Provider Object.
     */
	ServiceProvider getServiceProvider(String id);

	/**
     * add Service Provider to collection.
     *
     * @param serviceProvider.
     */
	void addServiceProvider(ServiceProvider serviceProvider);
	
	/**
     * remove Service Provider from collection by id.
     *
     * @param id.
     */
	void removeServiceProvider(String id);

}
