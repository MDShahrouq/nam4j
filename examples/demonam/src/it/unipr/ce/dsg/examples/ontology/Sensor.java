package it.unipr.ce.dsg.examples.ontology;

import it.unipr.ce.dsg.nam4j.impl.service.Parameter;

public class Sensor extends Parameter {

	public Sensor() {
		this.setName("Sensor");
	}
	
	public Sensor( String value ) {
		this.setName("Sensor");
		this.setValue(value);
	}
}
