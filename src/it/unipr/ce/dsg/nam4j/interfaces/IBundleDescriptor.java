package it.unipr.ce.dsg.nam4j.interfaces;

import java.io.Serializable;


public interface IBundleDescriptor extends Serializable {

	/**
     * Returns the name of the file describing the functional module or the service.
     *
     * @return the name of the file describing the functional module or the service
     */
	String getFileName();
	
	/**
     * Returns the name of the main class of the functional module or the service.
     *
     * @return the name of the main class of the functional module or the service
     */
	String getMainClassName();
	
	/**
     * Returns the complete name of the main class of the functional module or the service.
     *
     * @return the complete name of the main class of the functional module or the service
     */
	String getCompleteName();
	
}
