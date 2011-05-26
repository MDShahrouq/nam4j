package it.unipr.ce.dsg.examples.chordfm;

import com.google.gson.Gson;

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
		
		Temperature temperature = new Temperature();
		temperature.setId("i21");
		temperature.setValue("20");
		
		Publish publishService = new Publish();
		publishService.setId("s2");
		publishService.addInput(temperature);
		this.addProvidedService(publishService.getId(), publishService);
		
		Gson gson = new Gson();
		String json = gson.toJson(temperature);
		System.out.println("JSON temperature = " + json);
		json = gson.toJson(publishService);
		System.out.println("JSON publishService = " + json);
		
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
