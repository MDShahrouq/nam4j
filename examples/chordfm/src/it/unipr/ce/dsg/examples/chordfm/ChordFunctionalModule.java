package it.unipr.ce.dsg.examples.chordfm;

import it.unipr.ce.dsg.examples.ontology.Lookup;
import it.unipr.ce.dsg.examples.ontology.Notify;
import it.unipr.ce.dsg.examples.ontology.Publish;
import it.unipr.ce.dsg.examples.ontology.Subscribe;
import it.unipr.ce.dsg.nam4j.impl.FunctionalModule;
import it.unipr.ce.dsg.nam4j.impl.NetworkedAutonomicMachine;


public class ChordFunctionalModule extends FunctionalModule {

	public ChordFunctionalModule(NetworkedAutonomicMachine nam) {
		super(nam);
		this.setId("cfm");
		this.setName("ChordFunctionalModule");
		System.out.println("I am " + this.getId() + " and I own to " + nam.getId());
		
		// create Service objects and add to providedServices hashmap
		
		Lookup lookupService = new Lookup();
		lookupService.setId("s1");
		this.addProvidedService(lookupService.getId(), lookupService);
		
		Notify notifyService = new Notify();
		notifyService.setId("s2");
		this.addProvidedService(notifyService.getId(), notifyService);
		
		Publish publishService = new Publish();
		publishService.setId("s3");
		this.addProvidedService(publishService.getId(), publishService);
		
		Subscribe subscribeService = new Subscribe();
		subscribeService.setId("s4");
		this.addProvidedService(subscribeService.getId(), subscribeService);
	}
	
	private void join() {
		System.out.println("JOIN");
	}
	
	private void leave() {
		System.out.println("LEAVE");
	}
	
	private void lookup(String item) {
		Thread t = new Thread(new LookupRunnable(item), "Lookup thread");
		//System.out.println("Child thread: " + t);
		t.start();
	}
	
	private void publish(String item) {
		Thread t = new Thread(new PublishRunnable(item), "Publish thread");
		//System.out.println("Child thread: " + t);
		t.start();
	}
	
	private void subscribe(String fmId, String item) {
		System.out.println(fmId + " SUBSCRIBE to " + item);
	}
	
	public void execute(String serviceRequest) {
		if (serviceRequest.contains("Join")) {
			this.join();
		}
		if (serviceRequest.contains("Leave")) {
			this.leave();
		}
		if (serviceRequest.contains("Lookup")) {
			String[] tokens = serviceRequest.split(" ");
			this.lookup(tokens[2]);
		}
		if (serviceRequest.contains("Publish")) {
			String[] tokens = serviceRequest.split(" ");
			this.publish(tokens[2]);
		}
		if (serviceRequest.contains("Subscribe")) {
			String[] tokens = serviceRequest.split(" ");
			this.subscribe(tokens[0], tokens[2]);
		}
	}

}
