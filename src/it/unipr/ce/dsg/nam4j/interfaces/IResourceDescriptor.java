package it.unipr.ce.dsg.nam4j.interfaces;

public interface IResourceDescriptor {

	/**
     * set the Resource name.
     *
     * @param name.
     */
	void setName(String name);
	
	/**
     * get the Resource name.
     *
     * @return the name of the Resource.
     */
	String getName();
	
	/**
     * set the Resource id.
     *
     * @param id.
     */
	void setId(String id);
	
	/**
     * get the Resource id.
     *
     * @return the id of the Resource.
     */
	String getId();

}
