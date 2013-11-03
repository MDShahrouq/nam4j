package it.unipr.ce.dsg.nam4j.impl.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import it.unipr.ce.dsg.nam4j.impl.FunctionalModule;
import it.unipr.ce.dsg.nam4j.impl.NetworkedAutonomicMachine;
import it.unipr.ce.dsg.nam4j.impl.logger.Logger;

public class ContextBus extends FunctionalModule {

	private Logger logger = null;
	private ArrayList<String> eventModuleList = null; // FIXME find a better data structure
	
	public ContextBus(NetworkedAutonomicMachine nam) {
		super(nam);
		this.logger = new Logger("log/", "ContextBusLogs.txt");
		eventModuleList = new ArrayList<String>();
	}
	
	public Logger getLogger() {
		return logger;
	}

	public void subscribe(String contextEventName, String functionalModuleId) {
		String eventModule = contextEventName + ":" + functionalModuleId;
		if (!eventModuleList.contains(eventModule)) {
			eventModuleList.add(eventModule);
			logger.log("added " + eventModule + " to eventModuleList");
		}
		else
			logger.log(eventModule + " was already in the eventModuleList, thus it has not been re-added");	
	}
	
	public void unsubscribe(String contextEventName, String functionalModuleId) {
		String eventModule = contextEventName + ":" + functionalModuleId;
		if (eventModuleList.contains(eventModule)) {
			eventModuleList.remove(eventModule);
			logger.log("removed " + eventModule + " from eventModuleList");
		}
	}
	
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
