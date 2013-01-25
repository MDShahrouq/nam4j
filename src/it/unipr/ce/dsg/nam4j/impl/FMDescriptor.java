/**
Ê* 
Ê* This is the class which describes a Functional Module requested for migration.
Ê* 
Ê*/

package it.unipr.ce.dsg.nam4j.impl;

import java.io.Serializable;

public class FMDescriptor implements Serializable {

	private static final long serialVersionUID = 1L;
	String fileName; // File name of the Functional Module jar
	String mainClassName;  // Main class of the Functional Module
 
	public String getFileName() {
		return this.fileName;
	}
	
	public String getMainClassName() {
		return this.mainClassName;
	}
	
	public FMDescriptor(String f, String m){  
		this.fileName = f;  
		this.mainClassName = m;
	}
}