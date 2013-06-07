package it.unipr.ce.dsg.examples.migration;

import it.unipr.ce.dsg.nam4j.impl.FunctionalModule;
import it.unipr.ce.dsg.nam4j.impl.NetworkedAutonomicMachine;
import it.unipr.ce.dsg.nam4j.impl.service.Service;
import it.unipr.ce.dsg.nam4j.thread.ThreadPool;
  
public class Migration extends NetworkedAutonomicMachine {
	
	ThreadPool pool;
	
	public Migration(String configuration) {
		
		super(10); // The parameter is the thread pool size
		
		this.setId("migration");
		
		/* The server needs the thread pool to manage the migration of the FMs */
		if (configuration.equals("SERVER")) {
			pool = createThreadPoolForMigration();
		}
	}

	public static void main(String[] args) {
		
		Migration migration = new Migration(args[0]);
		
		FunctionalModule chordfm = null;
		FunctionalModule testfm = null;
		Service serv = null;
		String serviceId = null;
		
		if (args[0].equals("SERVER")) {
			
			migration.activateMigration();
			
		}
		else if (args[0].equals("CLIENT")) {
	
			chordfm = migration.findRemoteFM("ChordFunctionalModule", "F");
			testfm = migration.findRemoteFM("TestFunctionalModule", "F");
			serv = migration.findRemoteService("TestService", "F");
			serviceId = "TestService";
			
			if(serv != null) testfm.addProvidedService(serviceId, serv);
			else System.out.println("CLIENT: Error in service migration");

		}
	}
	
}
