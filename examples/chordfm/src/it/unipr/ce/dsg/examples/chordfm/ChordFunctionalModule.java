package it.unipr.ce.dsg.examples.chordfm;

import it.unipr.ce.dsg.nam4j.impl.FunctionalModule;

public class ChordFunctionalModule extends FunctionalModule {

	public ChordFunctionalModule() {
		// create Service objects and add to providedServices hashmap
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
		System.out.println("SUBSCRIBE");
		// http://sites.google.com/site/gson/gson-user-guide
	}
}
