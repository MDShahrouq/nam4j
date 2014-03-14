package it.unipr.ce.dsg.gamidroid.monitor;

import it.unipr.ce.dsg.gamidroid.android.NAM4JAndroidActivity;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Service to monitor the CPU and memory usage.
 */
public class DeviceMonitorService extends Service {

	private final int UPDATE_INTERVAL = 1000;
	private Timer timer = new Timer();

	/**
	 * Constructor.
	 */
	public DeviceMonitorService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
	}

	@Override
	public void onDestroy() {
		if (timer != null) {
			timer.cancel();
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startid) {

		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {

				float cpuUsage = cpuUsage();
				float[] memUsage = readMem();

				BigDecimal bd = new BigDecimal(Float.toString(cpuUsage));
				bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
				cpuUsage = bd.floatValue();

				float memUsagePerc = memUsage[0] / memUsage[1];

				BigDecimal bd2 = new BigDecimal(Float.toString(memUsagePerc));
				bd2 = bd2.setScale(2, BigDecimal.ROUND_HALF_UP);
				memUsagePerc = bd2.floatValue() * 100;
				
				ResourceMonitor rm = new ResourceMonitor(cpuUsage, memUsage[0], memUsage[1]);

				Intent RTReturn = new Intent(NAM4JAndroidActivity.RECEIVED_RESOURCES_UPDATE);
				
				RTReturn.putExtra("resourceDescriptor", rm);
				
				sendBroadcast(RTReturn);

			}
		}, 0, UPDATE_INTERVAL);

		return START_STICKY;
	}

	public void stopService() {
		if (timer != null)
			timer.cancel();
	}

	/**
	 * Method to get the current CPU load.
	 * 
	 * @return a float representing the CPU load
	 */
	private float cpuUsage() {
		try {
			
			/* Access the cpu statistics file */
			RandomAccessFile reader = new RandomAccessFile("/proc/stat", "r");
			String load = reader.readLine();

			String[] toks = load.split(" ");

			long idle1 = Long.parseLong(toks[5]);
			long cpu1 = Long.parseLong(toks[2]) + Long.parseLong(toks[3])
					+ Long.parseLong(toks[4]) + Long.parseLong(toks[6])
					+ Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

			try {
				Thread.sleep(360);
			} catch (Exception e) {
			}

			reader.seek(0);
			load = reader.readLine();
			reader.close();

			toks = load.split(" ");

			long idle2 = Long.parseLong(toks[5]);
			long cpu2 = Long.parseLong(toks[2]) + Long.parseLong(toks[3])
					+ Long.parseLong(toks[4]) + Long.parseLong(toks[6])
					+ Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

			return (float) (cpu2 - cpu1) / ((cpu2 + idle2) - (cpu1 + idle1));

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return 0;
	}

	/**
	 * Method to monitor the current available memory.
	 * 
	 * @return an array of floats containing the available mem, the total mem
	 *         and the used mem percentage
	 */
	private float[] readMem() {
		try {
			RandomAccessFile reader = new RandomAccessFile("/proc/meminfo", "r");
			String load = reader.readLine();
			
			reader.close();

			String[] toks = load.split(" ");
			
			int toksLength = toks.length;
			
			/* toks[toksLength - 1] is the "kB" string
			 * toks[toksLength - 2] is a number representing the total memory */
			float totalMemoryMegs = (Float.parseFloat(toks[toksLength - 2])) / 1024;

			BigDecimal bd = new BigDecimal(Float.toString(totalMemoryMegs));
			bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
			totalMemoryMegs = bd.floatValue();

			MemoryInfo mi = new MemoryInfo();
			ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
			activityManager.getMemoryInfo(mi);
			float availableMegs = Float.parseFloat((mi.availMem / 1048576L)
					+ "");

			float[] results = { availableMegs, totalMemoryMegs };

			return results;

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return null;
	}

}