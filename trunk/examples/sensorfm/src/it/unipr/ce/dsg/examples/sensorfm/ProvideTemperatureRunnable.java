package it.unipr.ce.dsg.examples.sensorfm;

import it.unipr.ce.dsg.examples.ontology.Room;
import it.unipr.ce.dsg.examples.ontology.Temperature;
import it.unipr.ce.dsg.examples.ontology.TemperatureNotification;
import it.unipr.ce.dsg.nam4j.impl.FunctionalModule;
import it.unipr.ce.dsg.nam4j.impl.service.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import com.google.gson.Gson;

public class ProvideTemperatureRunnable implements Runnable {
	
	private SensorFunctionalModule sfm = null;
	
	public ProvideTemperatureRunnable(SensorFunctionalModule sfm) {
		this.sfm = sfm;
	}
	
	public void run() {
		
		// call publish service passing json message
		// look into other functional modules, looking for requested service
		Collection<FunctionalModule> c = sfm.getNam().getFunctionalModules().values();
		Iterator<FunctionalModule> itr = c.iterator();
		String serviceName = null;
		FunctionalModule fm = null;
		FunctionalModule tempfm = null;
		while (itr.hasNext()) {
			tempfm = itr.next();
			if (tempfm.getName().equals(sfm.getName()))
				continue;
			//System.out.println("Temp FM: " + tempfm.getName());
			Collection<Service> cc = tempfm.getProvidedServices().values();
			Iterator<Service> itrr = cc.iterator();
			while (itrr.hasNext()) {
				serviceName = itrr.next().getName();
				//System.out.println("Service: " + serviceName);
				if (serviceName.equals("Publish")) {
					fm = tempfm;
					//System.out.println("FM: " + fm.getName());
				}
			}
		}

		Temperature temperature = new Temperature();
		temperature.setId("i21");
		temperature.setValue("20");

		//TemperatureNotification tempNotif = new TemperatureNotification();
		TemperatureNotification tempNotif = new TemperatureNotification();
		Room room = new Room();
		room.setValue("kitchen");
		tempNotif.setLocation(room);
		tempNotif.setSubject(temperature);
		Date timestamp = new Date();
		tempNotif.setTimestamp(timestamp.toString());

		Gson gson = new Gson();
		String json = gson.toJson(tempNotif);
		System.out.println("ProvideTemperature: JSON tempNotif = " + json);

		fm.execute(sfm.getId(), "Publish", json);
	}
}
