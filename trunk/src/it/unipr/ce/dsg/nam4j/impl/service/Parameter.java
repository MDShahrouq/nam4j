package it.unipr.ce.dsg.nam4j.impl.service;

import it.unipr.ce.dsg.nam4j.interfaces.IParameter;

public class Parameter implements IParameter {

	String id = "param";
	String name = "Parameter";
	String value = "value";
	
	public Parameter() {
	}
	
	/**
     * set the Parameter name.
     *
     * @param name.
     */
	protected void setName(String name) {
		this.name = name;
	}
	
	/**
     * get the Parameter name.
     *
     * @return the name of the Input.
     */
	public String getName() {
		return this.name;
	}
	
	/**
     * set the Parameter id.
     *
     * @param id.
     */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
     * get the Parameter id.
     *
     * @return the id of the Parameter.
     */
	public String getId() {
		return this.id;
	}
	
	/**
     * set the Parameter value.
     *
     * @param value.
     */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
     * get the Parameter value.
     *
     * @return the value of the Parameter.
     */
	public String getValue() {
		return this.value;
	}
	
}
