package it.unipr.ce.dsg.nam4j.impl.service;

import it.unipr.ce.dsg.nam4j.interfaces.IService;

import java.util.HashMap;

/**
 * <p>
 * This class represents a service.
 * </p>
 * 
 * <p>
 *  Copyright (c) 2011, Distributed Systems Group, University of Parma, Italy.
 *  Permission is granted to copy, distribute and/or modify this document
 *  under the terms of the GNU Free Documentation License, Version 1.3
 *  or any later version published by the Free Software Foundation;
 *  with no Invariant Sections, no Front-Cover Texts, and no Back-Cover Texts.
 *  A copy of the license is included in the section entitled "GNU
 *  Free Documentation License".
 * </p>
 * 
 * @author Michele Amoretti (michele.amoretti@unipr.it)
 * @author Alessandro Grazioli (grazioli@ce.unipr.it)
 * 
 */

public class Service implements IService {

	String id = "service";
	String name = "Service";
	
	HashMap<String,Parameter> inputs = new HashMap<String,Parameter>();
	HashMap<String,Parameter> outputs = new HashMap<String,Parameter>();
	HashMap<String,Effect> effects = new HashMap<String,Effect>();
	HashMap<String,Precondition> preconditions = new HashMap<String,Precondition>();
	
	public Service() {
	}
	
	protected void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public HashMap<String,Parameter> getInputs() {
		return inputs;
	}

	public Parameter getInput(String id) {
		return inputs.get(inputs.get(id));
	}

	public void addInput(Parameter input) {
		inputs.put(input.getId(),input);
	}

	public void removeInput(String id) {
		inputs.remove(id);
	}

	public HashMap<String,Parameter> getOutputs() {
		return outputs;
	}

	public Parameter getOutput(String id) {
		return outputs.get(id);
	}

	public void addOutput(Parameter output) {
		outputs.put(output.getId(),output);
	}

	public void removeOutput(String id) {
		outputs.remove(id);
	}

	public HashMap<String,Precondition> getPreconditions() {
		return preconditions;
	}

	public Precondition getPrecondition(String id) {
		return preconditions.get(id);
	}

	public void addPrecondition(Precondition precondition) {
		preconditions.put(precondition.getId(),precondition);
	}

	public void removePrecondition(String id) {
		preconditions.remove(id);
	}

	public HashMap<String,Effect> getEffects() {
		return effects;
	}

	public Effect getEffect(String id) {
		return effects.get(id);
	}

	public void addEffect(Effect effect) {
		effects.put(effect.getId(),effect);
	}

	public void removeEffect(String id) {
		effects.remove(id);
	}
	
}
