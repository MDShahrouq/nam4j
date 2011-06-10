package it.unipr.ce.dsg.examples.sensorfm;

import it.unipr.ce.dsg.examples.ontology.Temperature;
import it.unipr.ce.dsg.examples.ontology.TemperatureNotification;
import it.unipr.ce.dsg.nam4j.impl.FunctionalModule;
import it.unipr.ce.dsg.nam4j.impl.service.Service;

import java.util.Collection;
import java.util.Iterator;

import com.google.gson.Gson;

public class ProvideTemperatureRunnable implements Runnable {
	
	SensorFunctionalModule sfm = null;
	
	public ProvideTemperatureRunnable(SensorFunctionalModule sfm) {
		this.sfm = sfm;
	}
	
	public void run() {
		
		// call publish service passing json message
		// look into other functional modules, looking for requested service
		Collection<FunctionalModule> c = sfm.getNam()
				.getFunctionalModules().values();
		Iterator<FunctionalModule> itr = c.iterator();
		String serviceName = null;
		FunctionalModule fm = null;
		FunctionalModule tempfm = null;
		while (itr.hasNext()) {
			tempfm = itr.next();
			if (tempfm.getName().equals(sfm.getName()))
				continue;
			System.out.println("Temp FM: " + tempfm.getName());
			Collection<Service> cc = tempfm.getProvidedServices().values();
			Iterator<Service> itrr = cc.iterator();
			while (itrr.hasNext()) {
				serviceName = itrr.next().getName();
				System.out.println("Service: " + serviceName);
				if (serviceName.equals("Publish")) {
					fm = tempfm;
					System.out.println("FM: " + fm.getName());
				}
			}
		}
		
		for (int i = 0; i < 5; i++) {
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

			fm.execute(sfm.getId() + " Publish " + json);
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}