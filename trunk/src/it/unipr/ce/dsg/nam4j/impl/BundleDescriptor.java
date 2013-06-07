/**
Ê* 
Ê* This is the class which describes a Functional Module or a Service
 * requested for migration.
Ê* 
Ê*/

package it.unipr.ce.dsg.nam4j.impl;

import it.unipr.ce.dsg.nam4j.interfaces.IBundleDescriptor;

public class BundleDescriptor implements IBundleDescriptor {

	private static final long serialVersionUID = 1L;
	
	/**
	 * File name of the Functional Module jar or of the Service java file
	 */
	String fileName;
	
	/**
	 *  Name of the functional module main class or name of the service class
	 */
	String mainClassName;
	
	/**
	 * String containing the package and the class names
	 */
	String completeName;
	
	/**
     * Returns the name of the file describing the functional module or the service.
     *
     * @return the name of the file describing the functional module or the service
     */
	public String getFileName() {
		return this.fileName;
	}
	
	/**
     * Returns the name of the main class of the functional module or the service.
     *
     * @return the name of the main class of the functional module or the service
     */
	public String getMainClassName() {
		return this.mainClassName;
	}
	
	/**
     * Returns the complete name of the main class of the functional module or the service.
     *
     * @return the complete name of the main class of the functional module or the service
     */
	public String getCompleteName() {
		return this.completeName;
	}
	
	/**
	 * Class constructor.
	 * 
	 * @param f the name of the described file
	 * @param m the name of the described functional module or service main class
	 * @param c the complete name of the described functional module or service main class
	 */
	public BundleDescriptor(String f, String m, String c){  
		this.fileName = f;  
		this.mainClassName = m;
		this.completeName = c;
	}
}