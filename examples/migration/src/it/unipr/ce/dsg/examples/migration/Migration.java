package it.unipr.ce.dsg.examples.migration;

import it.unipr.ce.dsg.nam4j.impl.FunctionalModule;
import it.unipr.ce.dsg.nam4j.impl.NetworkedAutonomicMachine;
import it.unipr.ce.dsg.nam4j.impl.service.Service;

public class Migration extends NetworkedAutonomicMachine {

	public Migration(String configuration) {

		super(10, "examples/migration");

		this.setId("migration");

		/* The server needs the thread pool to manage the migration of the FMs */
		if (configuration.equals("SERVER")) {
			createThreadPoolForMigration();
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
		} else if (args[0].equals("CLIENT")) {

			// Requesting Chord FM
			chordfm = migration.findRemoteFM("ChordFunctionalModule",
					Platform.DESKTOP);
			if (chordfm != null) {
				// Requesting a test FM
				testfm = migration.findRemoteFM("TestFunctionalModule",
						Platform.DESKTOP);
				if (testfm != null) {
					// Requesting a test service
					serv = migration.findRemoteService("TestService",
							Platform.DESKTOP);
					serviceId = "TestService";
					if (serv != null)
						testfm.addProvidedService(serviceId, serv);
					else
						System.out
								.println("CLIENT: Error in service migration");
				} else
					System.out.println("CLIENT: Error in testfm migration");
			} else
				System.out.println("CLIENT: Error in chordfm migration");
		}
	}
}
