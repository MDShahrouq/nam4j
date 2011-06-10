package it.unipr.ce.dsg.nam4j.impl.service;

import java.util.HashMap;
import it.unipr.ce.dsg.nam4j.interfaces.IService;

public abstract class Service implements IService {

	String id = "service";
	String name = "Service";
	
	HashMap<String,Parameter> inputs = new HashMap<String,Parameter>();
	HashMap<String,Parameter> outputs = new HashMap<String,Parameter>();
	HashMap<String,Effect> effects = new HashMap<String,Effect>();
	HashMap<String,Precondition> preconditions = new HashMap<String,Precondition>();
	
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
