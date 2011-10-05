package it.unipr.ce.dsg.examples.reasonerfm;

public class NotifyRunnable implements Runnable {
	
	String item = null;
	
	public NotifyRunnable(String item) {
		this.item = item;
	}
	
	public void run() {
		System.out.println("Notified:\n" + item);
		// TODO parsing
	}

}
