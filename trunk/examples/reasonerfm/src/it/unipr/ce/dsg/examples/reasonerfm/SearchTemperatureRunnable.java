package it.unipr.ce.dsg.examples.reasonerfm;

import it.unipr.ce.dsg.examples.ontology.Room;
import it.unipr.ce.dsg.examples.ontology.Temperature;
import it.unipr.ce.dsg.examples.ontology.TemperatureNotification;
import it.unipr.ce.dsg.nam4j.impl.FunctionalModule;
import it.unipr.ce.dsg.nam4j.impl.service.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

import com.google.gson.Gson;

public class SearchTemperatureRunnable implements Runnable {

	ReasonerFunctionalModule rfm = null;
	String locationsFileName = "nowhere";
	ArrayList<String> locations = null;
	Random r = null;
	
	public SearchTemperatureRunnable(ReasonerFunctionalModule rfm, String locationsFileName) {
		this.rfm = rfm;
		this.locationsFileName = locationsFileName;
		r = new Random(987654321);
		
		locations = new ArrayList<String>();
		FileReader reader = null;
		try {
			reader = new FileReader(this.locationsFileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		Scanner in = new Scanner(reader);
		while (in.hasNextLine()) {
			 String line = in.nextLine();     
			 locations.add(line);       
		}
		in.close();
		System.out.println("Number of locations = " + locations.size());
	}
	
	public void run() {
		// call publish service passing json message
		// look into other functional modules, looking for requested service
		Collection<FunctionalModule> c = rfm.getNam()
				.getFunctionalModules().values();
		Iterator<FunctionalModule> itr = c.iterator();
		String serviceName = null;
		FunctionalModule fm = null;
		FunctionalModule tempfm = null;
		while (itr.hasNext()) {
			tempfm = itr.next();
			if (tempfm.getName().equals(rfm.getName()))
				continue;
			//System.out.println("Temp FM: " + tempfm.getName());
			Collection<Service> cc = tempfm.getProvidedServices().values();
			Iterator<Service> itrr = cc.iterator();
			while (itrr.hasNext()) {
				serviceName = itrr.next().getName();
				//System.out.println("Service: " + serviceName);
				if (serviceName.equals("Lookup")) {
					fm = tempfm;
					//System.out.println("FM: " + fm.getName());
				}
			}
		}
			
		Temperature temperature = new Temperature();
		TemperatureNotification tempNotif = new TemperatureNotification();
		tempNotif.setSubject(temperature);
		Room room = new Room();
		Gson gson = new Gson();
	
		while (true) {
			// pick a random location among those allowed
			room.setValue(locations.get(r.nextInt(locations.size())));
			tempNotif.setLocation(room);
			String json = gson.toJson(tempNotif);
			System.out.println("JSON tempNotif = " + json);
			
			fm.execute(rfm.getId(), "Lookup", json);
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
