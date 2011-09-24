package it.unipr.ce.dsg.examples.reasonerfm;

import it.unipr.ce.dsg.examples.ontology.Temperature;
import it.unipr.ce.dsg.examples.ontology.TemperatureNotification;
import it.unipr.ce.dsg.nam4j.impl.FunctionalModule;
import it.unipr.ce.dsg.nam4j.impl.service.Service;

import java.util.Collection;
import java.util.Iterator;

import com.google.gson.Gson;

public class SearchTemperatureRunnable implements Runnable {

	ReasonerFunctionalModule rfm = null;
	
	public SearchTemperatureRunnable(ReasonerFunctionalModule rfm) {
		this.rfm = rfm;
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

		Gson gson = new Gson();
		String json = gson.toJson(tempNotif);
		System.out.println("JSON tempNotif = " + json);

		while (true) {
			fm.execute(rfm.getId(), "Lookup", json);
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
