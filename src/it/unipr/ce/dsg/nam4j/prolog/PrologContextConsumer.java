package it.unipr.ce.dsg.nam4j.prolog;

import it.unipr.ce.dsg.nam4j.impl.ContextConsumer;

public class PrologContextConsumer extends ContextConsumer {

	String name = "Prolog Context Consumer";
	
	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

}
