package it.unipr.ce.dsg.nam4j.interfaces;

import it.unipr.ce.dsg.nam4j.impl.service.Parameter;
import it.unipr.ce.dsg.nam4j.impl.service.Effect;
import it.unipr.ce.dsg.nam4j.impl.service.Precondition;

import java.util.HashMap;

public interface IService {

	/**
     * get the Service name.
     *
     * @return the name of the Service.
     */
	String getName();
	
	/**
     * set the Service id.
     *
     * @param id.
     */
	void setId(String id);
	
	/**
     * get the Service id.
     *
     * @return the id of the Service.
     */
	String getId();
	
	/**
     * get the Input List of the Service.
     *
     * @return the List of Input.
     */
	HashMap<String, Parameter> getInputs();
	
	/**
     * get the Input of the Service by id.
     *
     * @param id.
     * @return the Input.
     */
	Parameter getInput(String id);
	
	/**
     * add Input to Service.
     *
     *@param input.
     */
	void addInput(Parameter input);
	
	/**
     * remove Input from Service by id.
     *
     *@param id.
     */
	void removeInput(String id);
	
	/**
     * get the Output List of the Service.
     *
     * @return the List of Outputs.
     */
	HashMap<String, Parameter> getOutputs();
	
	/**
     * get the Output of the Service by id.
     *
     * @param id.
     * @return the Output.
     */
	Parameter getOutput(String id);
	
	/**
     * add Output to Service.
     *
     *@param output.
     */
	void addOutput(Parameter output);
	
	/**
     * remove Output from Service by id.
     *
     *@param id.
     */
	void removeOutput(String id);
	
	/**
     * get the Preconditions List of the Service.
     *
     * @return the List of Preconditions.
     */
	HashMap<String, Precondition> getPreconditions();
	
	/**
     * get the Precondition of the Service by id.
     *
     * @param id.
     * @return the Precondition.
     */
	Precondition getPrecondition(String id);
	
	/**
     * add Precondition to Service.
     *
     *@param precondition.
     */
	void addPrecondition(Precondition precondition);
	
	/**
     * remove Precondition from Service by id.
     *
     *@param id.
     */
	void removePrecondition(String id);
	
	/**
     * get the Effects List of the Service.
     *
     * @return the List of Effects.
     */
	HashMap<String, Effect> getEffects();
	
	/**
     * get the Effect of the Service by id.
     *
     * @param id.
     * @return the Effect.
     */
	Effect getEffect(String id);
	
	/**
     * add Effect to Service.
     *
     *@param effect.
     */
	void addEffect(Effect effect);
	
	/**
     * remove Effect from Service by id.
     *
     *@param id.
     */
	void removeEffect(String id);
}
