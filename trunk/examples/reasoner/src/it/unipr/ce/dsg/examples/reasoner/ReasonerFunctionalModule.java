package it.unipr.ce.dsg.examples.reasoner;

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
		
		// TODO search for lookup service 
	}
	
	// the reasoner exposes a notify service that is called by chordfm
	// when a context event of interest is called
	public void notify(String item) {
		System.out.println(this.getId() + "notified about " + item);
	}
	
	// the following method should be private and executed periodically
	public void subscribeToContextEvents() {
		// look into other functional modules, looking for requested service
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
	    		if (serviceName.equals("Subscribe"))
	    			fm.execute(this.getId() + " Subscribe Temperature"); // FIXME should use a JSON message..
	    	}  	
	    }
	}
	
	public void publishContextEvents() {
		Temperature temperature = new Temperature();
		temperature.setId("i21");
		temperature.setValue("20");
		
		TemperatureNotification tempNotif = new TemperatureNotification();
		tempNotif.setObject(temperature);
		
		// to json
		Gson gson = new Gson();
		String json = gson.toJson(temperature);
		System.out.println("JSON temperature = " + json);
		json = gson.toJson(tempNotif);
		System.out.println("JSON tempNotif = " + json);
		
		// call publish service passing json message
		// look into other functional modules, looking for requested service
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
	    		if (serviceName.equals("Publish"))
	    			fm.execute(this.getId() + " Publish " + json); 
	    	}  	
	    }
	}
	
	public void startLookupProcess() {
		Thread t = new Thread(this, "Child thread");
		System.out.println("Child thread: " + t);
		t.start();
		try {
			Thread.sleep(3000);
		}
		catch (InterruptedException e) {
			System.out.println("main thread interrupted");
		}
		System.out.println("end main thread");
	}

	public void run() {
		try {
			for (int i = 5; i > 0; i--) {
				System.out.println("call Lookup " + i);
				Thread.sleep(1000);
			}
		}
		catch (InterruptedException e) {
			System.out.println("child thread interrupted");
		}
		System.out.println("end child thread");
	}
	
}
