package it.unipr.ce.dsg.examples.reasonerfm;

public class NotifyRunnable implements Runnable {
	
	private ReasonerFunctionalModule rfm = null;
	private String item = null;
	
	public NotifyRunnable(ReasonerFunctionalModule rfm, String item) {
		this.rfm = rfm;
		this.item = item;
	}
	
	public void run() {
		rfm.getLogger().log("Notified:\n" + item);
		// TODO parsing
	}

}
