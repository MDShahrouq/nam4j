package it.unipr.ce.dsg.examples.migration;

import it.unipr.ce.dsg.nam4j.impl.service.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TestService extends Service {

	String id = "TestService";
	String name = "TestService";

	public TestService() {

		this.setId(id);
		this.setName(name);

		Thread t = new Thread(new TestServiceRunnable(this),
				"TestService thread");
		t.start();

	}
}

class TestServiceRunnable implements Runnable {

	private TestService ts = null;

	public TestServiceRunnable(TestService ts) {
		this.ts = ts;
	}

	public void run() {

		Date date = new Date();
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		int seconds = calendar.get(Calendar.SECOND);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH) + 1; // The month is 0-based
		int year = calendar.get(Calendar.YEAR);

		System.out.println("\nSERVICE STARTED\n\n" + month + "/" + day + "/"
				+ year + " - " + hour + ":" + minutes + ":" + seconds + "\n");
		System.out.println("	Reading Temperature ...");

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("	Accessing Microphone ...");

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("	Getting accelerometer data ...");

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("	Getting position ...");

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("	Evaluating User Context ...");

	}

}