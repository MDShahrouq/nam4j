package it.unipr.ce.dsg.examples.migration;

import it.unipr.ce.dsg.nam4j.impl.FunctionalModule;
import it.unipr.ce.dsg.nam4j.impl.NetworkedAutonomicMachine;
import it.unipr.ce.dsg.nam4j.thread.ThreadPool;
  
public class Migration extends NetworkedAutonomicMachine {
	
	ThreadPool pool;
	
	public Migration(String configuration) {
		
		this.setId("migration");
		
		/* The server needs the thread pool to manage the migration of the FMs */
		if (configuration.equals("SERVER")) {
			pool = createThreadPoolForMigration();
		}
	}

	public static void main(String[] args) {
		
		Migration migration = new Migration(args[0]);
		
		FunctionalModule fm;
		
		if (args[0].equals("SERVER")) {
			
			migration.activateMigration();
			
		}
		else if (args[0].equals("CLIENT")) {
	
			fm = migration.findRemoteFM();

		}
	}
}
