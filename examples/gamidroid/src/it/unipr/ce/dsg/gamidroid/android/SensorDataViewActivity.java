package it.unipr.ce.dsg.gamidroid.android;

import it.unipr.ce.dsg.gamidroid.R;
import it.unipr.ce.dsg.gamidroid.gaminode.GamiNode;
import it.unipr.ce.dsg.gamidroid.ontology.Building;
import it.unipr.ce.dsg.gamidroid.ontology.Floor;
import it.unipr.ce.dsg.gamidroid.ontology.Location;
import it.unipr.ce.dsg.gamidroid.ontology.Room;
import it.unipr.ce.dsg.gamidroid.ontology.Sensor;
import it.unipr.ce.dsg.s2pchord.resource.ResourceDescriptor;
import it.unipr.ce.dsg.s2pchord.resource.ResourceListener;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * <p>
 * This class represents an activity that receives data for a sensor.
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

public class SensorDataViewActivity extends Activity implements
		ResourceListener {

	private Location location;
	private String sensorName;
	private static String temperatureMessageType = "TemperatureNotification";
	private volatile boolean stopThread = false;

	TextView titleTv;
	
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.sensor_data);
		
		context = this;
		
		overridePendingTransition(R.anim.animate_left_in, R.anim.animate_left_out);

		Button backButton = (Button) findViewById(R.id.backButtonSensor);

		backButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				stopThread();

				GamiNode.getAndroidGamiNode(context).getRfm()
						.stopTemperatureNotificationLookup();

				onBackPressed();
			}
		});

		stopThread = false;

		Bundle b = getIntent().getExtras();

		location = new Location();
		location.setBuilding(new Building(b.getString("Building")));
		location.setFloor(new Floor(b.getString("Floor")));
		location.setRoom(new Room(b.getString("Room")));
		location.setSensor(new Sensor(b.getString("Sensor")));

		sensorName = b.getString("Sensor");

		String buildingToShow = b.getString("Building");
		if (buildingToShow.length() > 28)
			buildingToShow = buildingToShow.substring(0, 28) + "...";
		String floorToShow = getResources().getString(R.string.floor)
				+ b.getString("Floor");
		if (floorToShow.length() > 28)
			floorToShow = floorToShow.substring(0, 28) + "...";
		String roomToShow = b.getString("Room");
		if (roomToShow.length() > 28)
			roomToShow = roomToShow.substring(0, 28) + "...";

		titleTv = (TextView) findViewById(R.id.SensorResumeText);
		titleTv.setText(buildingToShow + "\n" + floorToShow + "\n" + roomToShow);
		titleTv.setTextSize(15);

		GamiNode
				.getAndroidGamiNode(context)
				.getRfm()
				.startTemperatureNotificationLookup(
						location.getBuilding().getValue(),
						location.getFloor().getValue(),
						location.getRoom().getValue(),
						location.getSensor().getValue());

		GamiNode.addResourceListener(this);

	}

	@Override
	public void onReceivedResource(ResourceDescriptor rd, String reason) {

		String attachment = rd.getAttachment();

		if (attachment != null) {

			System.err.println("\n--- Processing the sensor data...\n");

			try {
				JSONObject obj = new JSONObject(attachment);

				String name = obj.getString("name");
				JSONObject subjectObj = obj.getJSONObject("subject");
				JSONObject locationObj = obj.getJSONObject("location");

				if (name.equalsIgnoreCase(temperatureMessageType)) {

					final String subjectValue = subjectObj.getString("value");

					JSONObject locationValue = new JSONObject(
							locationObj.getString("value"));

					JSONObject building = locationValue
							.getJSONObject("building");
					final String buildingValue = building.getString("value");

					JSONObject room = locationValue.getJSONObject("room");
					final String roomValue = room.getString("value");

					JSONObject floor = locationValue.getJSONObject("floor");
					final String floorValue = floor.getString("value");

					JSONObject sensor = locationValue.getJSONObject("sensor");
					final String sensorValue = sensor.getString("value");

					if (sensorValue.equalsIgnoreCase(sensorName)) {

						runOnUiThread(new Runnable() {
							public void run() {

								if (!stopThread) {

									TextView textBuilding = (TextView) findViewById(R.id.textAddress);
									TextView textFloor = (TextView) findViewById(R.id.textFloor);
									TextView textRoom = (TextView) findViewById(R.id.textRoom);
									TextView textSensor = (TextView) findViewById(R.id.textSensor);
									TextView textTemperature = (TextView) findViewById(R.id.textTemperature);

									textBuilding.setText("Address: "
											+ buildingValue);
									textFloor.setText("Floor: " + floorValue);
									textRoom.setText("Room: " + roomValue);
									textSensor
											.setText("Sensor: " + sensorValue);
									textTemperature.setText("Temperature: "
											+ subjectValue);
								}
							}
						});
					}
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

		} else {

			Toast.makeText(this, "No data received for the sensor",
					Toast.LENGTH_LONG).show();

			System.err
					.println("\n--- The attachement of the resource is null\n");
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			stopThread();

			GamiNode.getAndroidGamiNode(context).getRfm()
					.stopTemperatureNotificationLookup();
			
			onBackPressed();
		}
		return super.onKeyDown(keyCode, event);
	}

	public void stopThread() {
		stopThread = true;
	}
	
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.animate_right_in, R.anim.animate_right_out);
	}

}
