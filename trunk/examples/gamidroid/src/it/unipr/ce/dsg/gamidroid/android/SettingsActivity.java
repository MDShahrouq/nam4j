package it.unipr.ce.dsg.gamidroid.android;

import it.unipr.ce.dsg.gamidroid.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * <p>
 * This class represents an Activity that allows the user to manage the settings
 * of the application.
 * </p>
 * 
 * <p>
 * Copyright (c) 2013, Distributed Systems Group, University of Parma, Italy.
 * Permission is granted to copy, distribute and/or modify this document under
 * the terms of the GNU Free Documentation License, Version 1.3 or any later
 * version published by the Free Software Foundation; with no Invariant
 * Sections, no Front-Cover Texts, and no Back-Cover Texts. A copy of the
 * license is included in the section entitled "GNU Free Documentation License".
 * </p>
 * 
 * @author Alessandro Grazioli (grazioli@ce.unipr.it)
 * 
 */

public class SettingsActivity extends Activity {

	public static String TAG = "SettingsActivity";

	private FileManager fileManager;
	private EditText textIp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.settings);
		
		overridePendingTransition(R.anim.animate_left_in, R.anim.animate_right_out);

		fileManager = FileManager.getFileManager();

		Button buttonConfirm = (Button) findViewById(R.id.buttonConfirm);

		buttonConfirm.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText textIp = (EditText) findViewById(R.id.editIpBoostrap);
				String ip = textIp.getText().toString();
				fileManager.updateFileChordPeer(ip);
				onBackPressed();
				finish();
			}
		});

		Button backButton = (Button) findViewById(R.id.backButtonSetting);

		backButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		textIp = (EditText) findViewById(R.id.editIpBoostrap);

		// Load file containing bootstrap address
		File sd = new File(Environment.getExternalStorageDirectory()
				+ "/Android/data/it.unipr.ce.dsg.nam4j.android/cache/");
		File file = new File(sd, "chordPeer.cfg");

		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(file.getPath()));
			String ipBoostrap = properties.getProperty("bootstrap_peer");
			textIp.setText(ipBoostrap);
		} catch (FileNotFoundException e1) {
			Log.e(SettingsActivity.TAG, e1.getMessage()
					+ " (reading file containing bootstrap properties)");
			e1.printStackTrace();
		} catch (IOException e1) {
			Log.e(SettingsActivity.TAG, e1.getMessage()
					+ " (reading file containing bootstrap properties)");
			e1.printStackTrace();
		}

	}
	
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.animate_left_in, R.anim.animate_right_out);
	}

}
