package it.unipr.ce.dsg.nam4j.impl.service;

import java.util.HashMap;
import it.unipr.ce.dsg.nam4j.interfaces.IService;

public abstract class Service implements IService {

	String id = "service";
	String name = "Service";
	
	HashMap<String,Input> inputs = new HashMap<String,Input>();
	HashMap<String,Output> outputs = new HashMap<String,Output>();
	HashMap<String,Effect> effects = new HashMap<String,Effect>();
	HashMap<String,Precondition> preconditions = new HashMap<String,Precondition>();
	
	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public HashMap<String,Input> getInputs() {
		return inputs;
	}

	@Override
	public Input getInput(String id) {
		return inputs.get(inputs.get(id));
	}

	@Override
	public void addInput(Input input) {
		inputs.put(input.getId(),input);
	}

	@Override
	public void removeInput(String id) {
		inputs.remove(id);
	}

	@Override
	public HashMap<String,Output> getOutputs() {
		return outputs;
	}

	@Override
	public Output getOutput(String id) {
		return outputs.get(id);
	}

	@Override
	public void addOutput(Output output) {
		outputs.put(output.getId(),output);
	}

	@Override
	public void removeOutput(String id) {
		outputs.remove(id);
	}

	@Override
	public HashMap<String,Precondition> getPreconditions() {
		return preconditions;
	}

	@Override
	public Precondition getPrecondition(String id) {
		return preconditions.get(id);
	}

	@Override
	public void addPrecondition(Precondition precondition) {
		preconditions.put(precondition.getId(),precondition);
	}

	@Override
	public void removePrecondition(String id) {
		preconditions.remove(id);
	}

	@Override
	public HashMap<String,Effect> getEffects() {
		return effects;
	}

	@Override
	public Effect getEffect(String id) {
		return effects.get(id);
	}

	@Override
	public void addEffect(Effect effect) {
		effects.put(effect.getId(),effect);
	}

	@Override
	public void removeEffect(String id) {
		effects.remove(id);
	}
	
/*	@Override
	public boolean equals(Object obj) {
		return this.id.equals(((Service)obj).getId());
	}
*/
}
