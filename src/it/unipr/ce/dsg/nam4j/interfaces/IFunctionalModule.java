package it.unipr.ce.dsg.nam4j.interfaces;


public interface IFunctionalModule extends IContextConsumer, IContextProvider, IServiceConsumer, IServiceProvider {

	/**
     * Sets the name of the functional module.
     *
     * @param name
     */
	void setName(String name);
	
	/**
     * Returns the name of the functional module.
     *
     * @return the name of the functional module
     */
	String getName();
	
	/**
     * Sets the id of the of the functional module.
     *
     * @param id
     */
	void setId(String id);
	
	/**
     * Gets the id of the functional module.
     *
     * @return the id of the functional module
     */
	String getId();

}
