package it.unipr.ce.dsg.examples.chordfm;

public class LookupRunnable implements Runnable {

	private String item = null;
	
	public LookupRunnable(String item) {
		this.item = item;
	}
	
	public void run() {
		System.out.println("Service execution: Lookup for " + item);
	}

}
