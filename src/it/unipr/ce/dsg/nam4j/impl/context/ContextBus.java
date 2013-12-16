package it.unipr.ce.dsg.nam4j.impl.context;

import it.unipr.ce.dsg.nam4j.impl.FunctionalModule;
import it.unipr.ce.dsg.nam4j.impl.NetworkedAutonomicMachine;
import it.unipr.ce.dsg.nam4j.impl.logger.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * <p>
 * This class represents a context bus.
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
 * 
 */

public class ContextBus extends FunctionalModule {

	private Logger logger = null;
	private ArrayList<String> eventModuleList = null; // TODO find a better data structure
	
	/**
     * constructor of the context bus
     *
     * @param nam, a reference to the NAM which hosts the context bus
     */
	public ContextBus(NetworkedAutonomicMachine nam) {
		super(nam);
		this.logger = new Logger("log/", "ContextBusLogs.txt");
		eventModuleList = new ArrayList<String>();
	}
	
	/**
     * get the logger of the context bus
     *
     * @return logger
     */
	public Logger getLogger() {
		return logger;
	}

	/**
     * subscribe a functional module, specified by id, 
     * to a type of context event, specified by name
     *
     * @param contextEventName
     * @param functionalModuleId
     */
	public void subscribe(String contextEventName, String functionalModuleId) {
		String eventModule = contextEventName + ":" + functionalModuleId;
		if (!eventModuleList.contains(eventModule)) {
			eventModuleList.add(eventModule);
			logger.log("added " + eventModule + " to eventModuleList");
		}
		else
			logger.log(eventModule + " was already in the eventModuleList, thus it has not been re-added");	
	}
	
	/**
     * unsubscribe a functional module, specified by id, 
     * from a type of context event, specified by name
     *
     * @param contextEventName
     * @param functionalModuleId
     */
	public void unsubscribe(String contextEventName, String functionalModuleId) {
		String eventModule = contextEventName + ":" + functionalModuleId;
		if (eventModuleList.contains(eventModule)) {
			eventModuleList.remove(eventModule);
			logger.log("removed " + eventModule + " from eventModuleList");
		}
	}
	
	/**
     * publish a context event to the context bus;
     * such a context event will be forwarded 
     * to all interested functional modules
     *
     * @param contextEvent
     */
	public void publish(ContextEvent contextEvent) {
		// get all the functional modules that subscribed to contextEvent
		ArrayList<String> interestedModulesIds = new ArrayList<String>();
		for (String s : eventModuleList) {
			if (s.contains(contextEvent.getName() + ":")) {
				interestedModulesIds.add(s.substring(s.indexOf(":") + 1));
			}
		}
		
		// send contextEvent to all interested functional modules
		Collection<FunctionalModule> c = this.getNam().getFunctionalModules().values();
		Iterator<FunctionalModule> itr = c.iterator();
		FunctionalModule tempfm = null;
		while (itr.hasNext()) {
			tempfm = itr.next();
			if (interestedModulesIds.contains(tempfm.getId())) {
				tempfm.receive(contextEvent);
				logger.log("notified " + contextEvent.getName() + " to " + tempfm.getId());
			}
		}
	}
}
