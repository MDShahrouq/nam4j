package it.unipr.ce.dsg.nam4j.prolog;

import java.util.Vector;

import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.MalformedGoalException;
import alice.tuprolog.NoMoreSolutionException;
import alice.tuprolog.NoSolutionException;
import alice.tuprolog.Prolog;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.Theory;
import it.unipr.ce.dsg.nam4j.impl.FunctionalModule;
import it.unipr.ce.dsg.nam4j.utils.MysqlConnDB;


public class PrologReasoner extends FunctionalModule {

	String id = "prologReasonerModule";
	String name = "PrologReasonerModule";
	
	Prolog engine = new Prolog();
	MysqlConnDB mysqlConnDB = new MysqlConnDB();
	
	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setId(String id) {
		this.id = id;

	}

	@Override
	public String getId() {
		return id;
	}

	public Vector<PrologContextConsumer> getPrologContextConsumers() {
		Vector<PrologContextConsumer> prologContextConsumers = new Vector<PrologContextConsumer>();
		for(int i = 0; i <getContextConsumers().size(); i++ )
			prologContextConsumers.add((PrologContextConsumer)getContextConsumers().get(i));

		return prologContextConsumers;
	}

	public PrologContextConsumer getPrologContextConsumer(String id) {
		return (PrologContextConsumer) getContextConsumer(id);
	}

	public void addPrologContextConsumer(PrologContextConsumer prologContextConsumer){
			this.getContextConsumers().put(prologContextConsumer.getId(),prologContextConsumer);
	}

	public Prolog getEngine() {
		return engine;
	}

	public void setEngine(Prolog engine) {
		this.engine = engine;
	}

	public MysqlConnDB getMysqlConnDB() {
		return mysqlConnDB;
	}

	public void setMysqlConnDB(MysqlConnDB mysqlConnDB) {
		this.mysqlConnDB = mysqlConnDB;
	}

	public void setTheory(String acquiredTheory) throws InvalidTheoryException{
		Theory theory = new Theory(acquiredTheory);
		this.engine.setTheory(theory);
	}
	
	public String solveGoal(String goal) throws MalformedGoalException, NoSolutionException, NoMoreSolutionException{
		
		SolveInfo info = engine.solve(goal);
		System.out.println("query: " + info.getQuery());
		String solveInfo = "";
		while (info.isSuccess()) {
			solveInfo = "solution: " + info.getSolution() +" - bindings: " + info;
			if (engine.hasOpenAlternatives()) {
				info = engine.solveNext();
			} else {
				break;
			}
		}
		
		return solveInfo;
	}
	

//	@Override
//	public Vector<ContextConsumer> getContextConsumers() {
//	/*	Vector<String> contextConsumersIds = new Vector<String>();
//		for(int i = 0; i <contextConsumers.size(); i++ )
//			contextConsumersIds.add(contextConsumers.get(i).getId());
//		return contextConsumersIds;*/
//		//return contextConsumers;
//		return new Vector<ContextConsumer>(this.prologContextConsumers);
//	}
}
