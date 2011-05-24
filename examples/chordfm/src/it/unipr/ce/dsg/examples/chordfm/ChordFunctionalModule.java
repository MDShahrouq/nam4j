package it.unipr.ce.dsg.examples.chordfm;

import it.unipr.ce.dsg.nam4j.impl.FunctionalModule;
import it.unipr.ce.dsg.nam4j.impl.NetworkedAutonomicMachine;

public class ChordFunctionalModule extends FunctionalModule {

	public ChordFunctionalModule(NetworkedAutonomicMachine nam) {
		super(nam);
		this.setId("cfm");
		this.setName("ChordFunctionalModule");
		System.out.println("I am " + this.getId() + " and I own to " + nam.getId());
		
		// create Service objects and add to providedServices hashmap
		Notify notifyService = new Notify();
		notifyService.setId("s1");
		this.addProvidedService(notifyService.getId(), notifyService);
		Publish publishService = new Publish();
		publishService.setId("s2");
		this.addProvidedService(publishService.getId(), publishService);
		Subscribe subscribeService = new Subscribe();
		subscribeService.setId("s3");
		this.addProvidedService(subscribeService.getId(), subscribeService);
	}
	
	public void join() {
		System.out.println("JOIN");
	}
	
	public void leave() {
		System.out.println("LEAVE");
	}
	
	public void lookup(String item) {
		System.out.println("LOOKUP");
	}
	
	public void publish(String item) {
		System.out.println("PUBLISH");
	}
	
	public void subscribe(String fmId, String item) {
		System.out.println(fmId + " SUBSCRIBE to " + item);
		// http://sites.google.com/site/gson/gson-user-guide
	}
	
	public void execute(String serviceRequest) {
		if (serviceRequest.contains("Subscribe")) {
			String[] tokens = serviceRequest.split(" ");
			this.subscribe(tokens[0], tokens[2]);
		}
	}
}
