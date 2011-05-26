package it.unipr.ce.dsg.examples.demonam;

import it.unipr.ce.dsg.examples.chordfm.ChordFunctionalModule;
import it.unipr.ce.dsg.examples.reasoner.ReasonerFunctionalModule;
import it.unipr.ce.dsg.nam4j.impl.NetworkedAutonomicMachine;

public class DemoNam extends NetworkedAutonomicMachine {

	ChordFunctionalModule cfm = null;
	ReasonerFunctionalModule rfm = null;
	
	public DemoNam() {
		this.setId("demonam");
		cfm = new ChordFunctionalModule(this);
		this.addFunctionalModule(cfm);
		rfm = new ReasonerFunctionalModule(this);
		this.addFunctionalModule(rfm);
	}
	
	public static void main(String[] args) {
		DemoNam demonam = new DemoNam();
		System.out.println("Demonam has " 
				+ demonam.getFunctionalModules().size() 
				+ " functional modules");
		demonam.rfm.subscribeToContextEvents();
		demonam.rfm.publishContextEvents();
	}
}
