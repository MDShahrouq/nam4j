package it.unipr.ce.dsg.examples.chordfm;

import java.util.Random;

public class PublishRunnable implements Runnable {

	private String item = null;
	
	public PublishRunnable(String item) {
		this.item = item;
	}
	
	public void run() {
		Random rand = new Random(); 
		try {
			Thread.sleep(1000 * rand.nextInt(10));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Service executed: Publish " + item);
	}

}
