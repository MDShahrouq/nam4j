/**
Ê* 
Ê* This is the class which describes a Functional Module or a Service
 * requested for migration.
Ê* 
Ê*/

package it.unipr.ce.dsg.nam4j.impl;

import java.io.Serializable;

public class Descriptor implements Serializable {

	private static final long serialVersionUID = 1L;
	String fileName; // File name of the Functional Module jar
	String mainClassName;  // Main class of the Functional Module or class of the service
	String completeName; // Package and class name
	
	public String getFileName() {
		return this.fileName;
	}
	
	public String getMainClassName() {
		return this.mainClassName;
	}
	
	public String getCompleteName() {
		return this.completeName;
	}
	
	public Descriptor(String f, String m, String c){  
		this.fileName = f;  
		this.mainClassName = m;
		this.completeName = c;
	}
}