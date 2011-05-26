package it.unipr.ce.dsg.nam4j.interfaces;

public interface IParameter {
	
	/**
     * get the Parameter name.
     *
     * @return the name of the Input.
     */
	String getName();
	
	/**
     * set the Parameter id.
     *
     * @param id.
     */
	void setId(String id);
	
	/**
     * get the Parameter id.
     *
     * @return the id of the Parameter.
     */
	String getId();
	
	/**
     * set the Parameter value.
     *
     * @param value.
     */
	void setValue(String value);
	
	/**
     * get the Parameter value.
     *
     * @return the value of the Parameter.
     */
	String getValue();
	
}
