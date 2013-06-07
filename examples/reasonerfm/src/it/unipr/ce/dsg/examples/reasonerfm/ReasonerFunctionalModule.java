package it.unipr.ce.dsg.examples.reasonerfm;

import it.unipr.ce.dsg.examples.ontology.Notify;
import it.unipr.ce.dsg.examples.ontology.Temperature;
import it.unipr.ce.dsg.examples.ontology.TemperatureNotification;
import it.unipr.ce.dsg.nam4j.impl.FunctionalModule;
import it.unipr.ce.dsg.nam4j.impl.NetworkedAutonomicMachine;
import it.unipr.ce.dsg.nam4j.impl.service.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import com.google.gson.Gson;

public class ReasonerFunctionalModule extends FunctionalModule {

	private ReasonerLogger rLogger = null;
	private HashMap<String,Date> locationMap = null;
	
	public ReasonerFunctionalModule(NetworkedAutonomicMachine nam) {
		super(nam);
		this.setId("rfm");
		this.setName("ReasonerFunctionalModule");
		this.rLogger = new ReasonerLogger("log/");
		rLogger.log("I am " + this.getId() + " and I own to " + nam.getId());

		this.locationMap = new HashMap<String,Date>();
		
		Notify notifyService = new Notify();
		notifyService.setId("s1");
		this.addProvidedService(notifyService.getId(), notifyService);
		
		// create Context objects 
		// and add to providedContextEvents and consumableContextEvents
		Temperature temperature = new Temperature();
		TemperatureNotification tempNotif = new TemperatureNotification();
		tempNotif.setObject(temperature);
		this.addProvidedContextEvent("c1", tempNotif); 
	}
	
	
	public ReasonerLogger getLogger() {
		return rLogger;
	}
	
	public HashMap<String,Date> getLocationMap() {
		return locationMap;
	}

	public void setLocationMap(HashMap<String,Date> locationMap) {
		this.locationMap = locationMap;
	}

	
	// the reasoner exposes a notify service that is called 
	// when a context event of interest is called
	public void notify(String parameters) {
		Thread t = new Thread(new NotifyRunnable(this, parameters), "Notify thread");
		t.start();
	}
	
	
	public void execute(String requestorId, String requestedService, String parameters) {
		if (requestedService.equals("Notify")) {
			this.notify(parameters);
		}
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
	    	rLogger.log("FM: " + fm.getName());
	    	Collection<Service> cc = fm.getProvidedServices().values();
	    	Iterator<Service> itrr = cc.iterator();
	    	while(itrr.hasNext()) {
	    		serviceName = itrr.next().getName();
	    		rLogger.log("Service: " + serviceName);
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
	

	public void startTemperatureNotificationLookup(String locationsFileName) {
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date initDay = null;
		try {
			initDay = df.parse("25/12/2010 00:00:00");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		FileReader reader = null;
		try {
			reader = new FileReader(locationsFileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		Scanner in = new Scanner(reader);
		while (in.hasNextLine()) {
			 String location = in.nextLine();     
			 locationMap.put(location, initDay);       
		}
		in.close();
		
		//System.out.println("rfm location map size: " + locationMap.size());
		
		// create and start a thread that periodically looks up for Temperature notification events
		Thread t = new Thread(new SearchTemperatureRunnable(this), "Search temperature thread");
		//System.out.println("Child thread: " + t);
		t.start();
	}
	
}
