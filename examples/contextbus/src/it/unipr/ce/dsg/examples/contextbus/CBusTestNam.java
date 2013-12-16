package it.unipr.ce.dsg.examples.contextbus;

import it.unipr.ce.dsg.examples.ontology.Room;
import it.unipr.ce.dsg.examples.ontology.Temperature;
import it.unipr.ce.dsg.examples.ontology.TemperatureNotification;
import it.unipr.ce.dsg.examples.reasonerfm.ReasonerFunctionalModule;
import it.unipr.ce.dsg.nam4j.impl.NetworkedAutonomicMachine;
import it.unipr.ce.dsg.nam4j.impl.context.ContextBus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CBusTestNam extends NetworkedAutonomicMachine {

	private ContextBus cBus = null;
	private ReasonerFunctionalModule rfm = null;
	
	public CBusTestNam(int poolSize, String migrationStorePath, int trialsNumber) {
		super(poolSize, migrationStorePath, trialsNumber);
		
		this.setId("cbtestnam");
		cBus = new ContextBus(this);
		this.addFunctionalModule(cBus);
		rfm = new ReasonerFunctionalModule(this);
		this.addFunctionalModule(rfm);
	}
	
	private ContextBus getContextBus() {
		return cBus;
	}
	
	private ReasonerFunctionalModule getReasonerFunctionalModule() {
		return rfm;
	}

	public static void main(String[] args) {
		CBusTestNam cbtestnam = new CBusTestNam(10, "examples/migration", 3);
		
		ContextBus cBus = cbtestnam.getContextBus();
		cBus.subscribe("TemperatureNotification", cbtestnam.getReasonerFunctionalModule().getId());
		
		Temperature temperature = new Temperature();
		temperature.setId("i21");
		temperature.setValue("23");
		TemperatureNotification tempNotif = new TemperatureNotification();
		Room room = new Room();
		room.setValue("Kitchen");
		tempNotif.setLocation(room);
		tempNotif.setSubject(temperature);
		Date timestamp = new Date();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		tempNotif.setTimestamp(df.format(timestamp));
		
		cBus.publish(tempNotif);
	}
}
