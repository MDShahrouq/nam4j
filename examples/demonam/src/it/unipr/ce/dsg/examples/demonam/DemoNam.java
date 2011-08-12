package it.unipr.ce.dsg.examples.demonam;

import java.util.Random;

import it.unipr.ce.dsg.examples.chordfm.ChordFunctionalModule;
import it.unipr.ce.dsg.examples.reasonerfm.ReasonerFunctionalModule;
import it.unipr.ce.dsg.examples.sensorfm.SensorFunctionalModule;
import it.unipr.ce.dsg.nam4j.impl.NetworkedAutonomicMachine;

public class DemoNam extends NetworkedAutonomicMachine {

	ChordFunctionalModule cfm = null;
	ReasonerFunctionalModule rfm = null;
	SensorFunctionalModule sfm = null;
	
	public DemoNam() {
		this.setId("demonam");
		cfm = new ChordFunctionalModule(this);
		this.addFunctionalModule(cfm);
		rfm = new ReasonerFunctionalModule(this);
		this.addFunctionalModule(rfm);
		sfm = new SensorFunctionalModule(this);
		this.addFunctionalModule(sfm);
	}
	
	public static void main(String[] args) {
		DemoNam demonam = new DemoNam();
		System.out.println("Demonam has " 
				+ demonam.getFunctionalModules().size() 
				+ " functional modules");
		Random ran = new Random(System.currentTimeMillis());
		int i = ran.nextInt(2);
		if (i == 0)
			demonam.sfm.startTemperatureNotificationProcess();
		else
			demonam.rfm.startTemperatureNotificationLookupProcess();
	}
}
