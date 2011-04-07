package it.unipr.ce.dsg.nam4j.prolog;

import it.unipr.ce.dsg.nam4j.utils.MysqlConnDB;
import it.unipr.ce.dsg.s2p.nam4j.impl.S2P_FunctionalModule;

import java.util.ArrayList;

import alice.tuprolog.InvalidTheoryException;

public class Test {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		PrologReasoner prologReasoner = new PrologReasoner();
		
		MysqlConnDB mysqlConnDB = prologReasoner.getMysqlConnDB();
		
		String acquiredTheory = "lightIsOffIn(kitchen).\n"  // <---- questo  un evento di contesto o il risultato di una service call
			+ "turnOnLightIn(X):-personEnter(X),lightIsOffIn(X).\n";  // <---- questa  una regola = conoscenza di base
		
		ArrayList<Object> dataEntry = new ArrayList<Object>();
		ArrayList<Object> dataOutputs = new ArrayList<Object>();
		
		 acquiredTheory += "personEnter(kitchen).";
		
		try {
			prologReasoner.setTheory(acquiredTheory);
			
		} catch (InvalidTheoryException e) {
			e.printStackTrace();
		}
		
			
			mysqlConnDB.connectToDB();
			mysqlConnDB.prepareQuery("SELECT * FROM Rules WHERE content LIKE ?");
			
			dataEntry.add(new String("%personEnter%"));
			//System.out.println(dataEntry.get(0));
			mysqlConnDB.setDataQuery(1, dataEntry);

			dataOutputs = mysqlConnDB.executeSelectQuerywithMappedResult();
			//System.out.println(dataOutputs.get(0));
			String a = ((String) dataOutputs.get(1)).split(":")[0];
			//System.out.println(a);
			String goal = a.split("\\(")[0];
			System.out.println("goal:"+goal);
		
			
			
			String info = prologReasoner.solveGoal(goal+"(kitchen).");
			System.out.println("info "+info);
	}

}
