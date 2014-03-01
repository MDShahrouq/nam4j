package it.unipr.ce.dsg.examples.migration;

import it.unipr.ce.dsg.nam4j.impl.NetworkedAutonomicMachine;

public class Migration extends NetworkedAutonomicMachine {

	public Migration(String configuration) {

		super(10, "examples/migration", 3);

		this.setId("migration");

	}

	public static void main(String[] args) {

		Migration migration = new Migration(args[0]);

		if (args[0].equals("SERVER")) {
			migration.startMobilityAction();
		} else if (args[0].equals("CLIENT")) {

			migration.startCopyAction("ChordFunctionalModule", null, null,
					Platform.DESKTOP);

			migration.startCopyAction("TestFunctionalModule",
					new String[] { "TestService" },
					new String[] { "serviceId" }, Platform.DESKTOP);
		}
	}

}
