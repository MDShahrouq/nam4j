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
	
	/**
     * constructor
     */
	public Service() {
	}
	
	/**
     * get the service name
     *
     * @return the name of the service
     */
	protected void setName(String name) {
		this.name = name;
	}

	/**
     * set the service id
     *
     * @param id
     */
	public String getName() {
		return name;
	}

	/**
     * get the service id
     *
     * @return the id of the service
     */
	public void setId(String id) {
		this.id = id;
	}

	/**
     * get the service id
     *
     * @return the id of the service
     */
	public String getId() {
		return id;
	}

	/**
     * get the input list of the service
     *
     * @return the list of inputs
     */
	public HashMap<String,Parameter> getInputs() {
		return inputs;
	}

	/**
     * get the input of the service by id
     *
     * @param id
     * @return the input
     */
	public Parameter getInput(String id) {
		return inputs.get(inputs.get(id));
	}

	/**
     * add an input to the service
     *
     *@param input
     */
	public void addInput(Parameter input) {
		inputs.put(input.getId(),input);
	}

	/**
     * remove input from service by id
     *
     *@param id
     */
	public void removeInput(String id) {
		inputs.remove(id);
	}

	/**
     * get the output list of the service
     *
     * @return the list of outputs
     */
	public HashMap<String,Parameter> getOutputs() {
		return outputs;
	}

	/**
     * get the output of the service by id
     *
     * @param id
     * @return the output
     */
	public Parameter getOutput(String id) {
		return outputs.get(id);
	}

	/**
     * add output to service
     *
     *@param output
     */
	public void addOutput(Parameter output) {
		outputs.put(output.getId(),output);
	}

	/**
     * remove output from service by id
     *
     *@param id
     */
	public void removeOutput(String id) {
		outputs.remove(id);
	}

	/**
     * get the precondition list of the service
     *
     * @return the list of preconditions
     */
	public HashMap<String,Precondition> getPreconditions() {
		return preconditions;
	}

	/**
     * get a precondition of the service by id
     *
     * @param id
     * @return the precondition
     */
	public Precondition getPrecondition(String id) {
		return preconditions.get(id);
	}

	/**
     * add a precondition to the service
     *
     *@param precondition
     */
	public void addPrecondition(Precondition precondition) {
		preconditions.put(precondition.getId(),precondition);
	}

	/**
     * remove a precondition from the service by id
     *
     *@param id
     */
	public void removePrecondition(String id) {
		preconditions.remove(id);
	}

	/**
     * get the effect list of the service
     *
     * @return the list of effects
     */
	public HashMap<String,Effect> getEffects() {
		return effects;
	}

	/**
     * get an effect of the service by id
     *
     * @param id
     * @return the effect
     */
	public Effect getEffect(String id) {
		return effects.get(id);
	}

	/**
     * add an effect to the service
     *
     *@param effect
     */
	public void addEffect(Effect effect) {
		effects.put(effect.getId(),effect);
	}

	/**
     * remove an effect from the service by id
     *
     *@param id
     */
	public void removeEffect(String id) {
		effects.remove(id);
	}
	
}
