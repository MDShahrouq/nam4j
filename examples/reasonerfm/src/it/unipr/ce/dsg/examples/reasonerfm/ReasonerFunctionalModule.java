package it.unipr.ce.dsg.examples.reasonerfm;

import java.util.Collection;
import java.util.Iterator;

import com.google.gson.Gson;

import it.unipr.ce.dsg.examples.ontology.Temperature;
import it.unipr.ce.dsg.examples.ontology.TemperatureNotification;
import it.unipr.ce.dsg.nam4j.impl.FunctionalModule;
import it.unipr.ce.dsg.nam4j.impl.NetworkedAutonomicMachine;
import it.unipr.ce.dsg.nam4j.impl.service.Service;

public class ReasonerFunctionalModule extends FunctionalModule {

	public ReasonerFunctionalModule(NetworkedAutonomicMachine nam) {
		super(nam);
		this.setId("rfm");
		this.setName("ReasonerFunctionalModule");
		System.out.println("I am " + this.getId() + " and I own to " + nam.getId());

		// create Context objects 
		// and add to providedContextEvents and consumableContextEvents
		Temperature temperature = new Temperature();
		TemperatureNotification tempNotif = new TemperatureNotification();
		tempNotif.setObject(temperature);
		this.addProvidedContextEvent("c1", tempNotif); 
	}
	
	// the reasoner exposes a notify service that is called by chordfm
	// when a context event of interest is called
	public void notify(String item) {
		System.out.println(this.getId() + "notified about " + item);
	}
	
	// the following method should be private and executed periodically
	public void subscribeToTemperatureNotifications() {
		// search other functional modules, looking for requested service
	    Collection<FunctionalModule> c = this.getNam().getFunctionalModules().values();
	    Iterator<FunctionalModule> itr = c.iterator();
	    String serviceName = null;
	    FunctionalModule fm = null;
	    while(itr.hasNext()) {
	    	fm = itr.next();
	    	if (fm.getName().equals(this.getName()))
	    		continue;
	    	System.out.println("FM: " + fm.getName());
	    	Collection<Service> cc = fm.getProvidedServices().values();
	    	Iterator<Service> itrr = cc.iterator();
	    	while(itrr.hasNext()) {
	    		serviceName = itrr.next().getName();
	    		System.out.println("Service: " + serviceName);
	    		if (serviceName.equals("Subscribe")) {
	    			Temperature temperature = new Temperature();
	    			TemperatureNotification tempNotif = new TemperatureNotification();
	    			tempNotif.setSubject(temperature);
	    			Gson gson = new Gson();
	    			String json = gson.toJson(tempNotif);
	    			fm.execute(this.getId(), "Subscribe", json); 
	    		}
	    	}  	
	    }
	}
	

	public void startTemperatureNotificationLookup() {
		// create and start a thread that periodically looks up for Temperature notification events
		Thread t = new Thread(new SearchTemperatureRunnable(this), "Search temperature thread");
		//System.out.println("Child thread: " + t);
		t.start();
	}
	
}
