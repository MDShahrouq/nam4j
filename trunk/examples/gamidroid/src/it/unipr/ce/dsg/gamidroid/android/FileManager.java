package it.unipr.ce.dsg.gamidroid.android;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import android.os.Environment;
import android.util.Log;

public class FileManager {

	private static final String TAG = "FileManager";

	private String path_coordPeer_cfg;
	private String path_bs_cfg;

	private File sd;

	private static FileManager fileManager;

	private FileManager() {

		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			sd = new File(Environment.getExternalStorageDirectory()
					+ "/Android/data/it.unipr.ce.dsg.nam4j.android/cache/");
			path_coordPeer_cfg = "chordPeer.cfg";
			path_bs_cfg = "bs.cfg";

			if (!sd.exists()) {
				sd.mkdirs();
			}
		}

	}

	public void createFiles() {

		File file;
		file = new File(sd, path_coordPeer_cfg);

		if (!file.exists()) {
			createFileChordPeer(file);
			Log.d(NAM4JAndroidActivity.TAG, "creazione file chordPeer");
		}

		file = new File(sd, path_bs_cfg);

		if (!file.exists()) {
			createFileBs(file);
			Log.d(NAM4JAndroidActivity.TAG, "creazione file bs");
		}
	}

	public void createFileChordPeer(File file) {

		BufferedWriter writer;

		if (!file.exists()) {
			try {
				file.createNewFile();
				writer = new BufferedWriter(new FileWriter(file, true));
				writer.write("bootstrap_peer=bootstrap@192.168.1.155:5080\n");
				writer.close();

			} catch (FileNotFoundException e) {
				Log.e(NAM4JAndroidActivity.TAG, e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				Log.e(NAM4JAndroidActivity.TAG, e.getMessage());
				e.printStackTrace();
			}
		}

	}

	public void updateFileChordPeer(String ip) {

		BufferedWriter writer;
		Log.d(FileManager.TAG, "chordPeer path: " + sd.getPath());
		File file = new File(sd, path_coordPeer_cfg);

		file.delete();
		try {
			file.createNewFile();
			Log.d(NAM4JAndroidActivity.TAG,
					"update del file chordPeer " + file.getPath());
		} catch (IOException e1) {
			Log.e(NAM4JAndroidActivity.TAG, e1.getMessage());
			e1.printStackTrace();
		}
		try {
			writer = new BufferedWriter(new FileWriter(file, true));
			writer.write("bootstrap_peer=" + ip + "\n");
			writer.flush();
			writer.close();

		} catch (FileNotFoundException e) {
			Log.e(NAM4JAndroidActivity.TAG, e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			Log.e(NAM4JAndroidActivity.TAG, e.getMessage());
			e.printStackTrace();
		}

	}

	public void createFileBs(File file) {

		BufferedWriter writer;

		if (!file.exists()) {
			try {
				file.createNewFile();
				writer = new BufferedWriter(new FileWriter(file, true));
				writer.write("via_addr=AUTO-CONFIGURATION\n"
						+ "host_port=5080\n" + "peer_name=bootstrap\n"
						+ "#format_message=text\n"
						+ "test_address_reachability=no\n"
						+ "#list_path=list/\n" + "#log_path=log/\n"
						+ "keepalive_time=1\n" + "debug_level=1");
				writer.close();

			} catch (FileNotFoundException e) {
				Log.e(NAM4JAndroidActivity.TAG, e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				Log.e(NAM4JAndroidActivity.TAG, e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public static FileManager getFileManager() {

		if (fileManager == null) {
			fileManager = new FileManager();
		}
		return fileManager;
	}

}
