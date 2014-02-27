package it.unipr.ce.dsg.gamidroid.android;

import it.unipr.ce.dsg.gamidroid.gaminode.GamiNode;
import it.unipr.ce.dsg.gamidroid.taskmanagerfm.UPCPFTaskDescriptor;
import it.unipr.ce.dsg.s2pchord.msg.MessageListener;
import it.unipr.ce.dsg.s2pchord.resource.ResourceDescriptor;
import it.unipr.ce.dsg.s2pchord.resource.ResourceListener;
import it.unipr.ce.dsg.s2pchord.resource.ResourceParameter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Rect;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * <p>
 * This class represents the main Activity of the Android application.
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

public class NAM4JAndroidActivity extends FragmentActivity implements
		LocationListener, GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener,
		OnMarkerClickListener, ResourceListener, MessageListener {

	/* A request to connect to Location Services */
	private LocationRequest mLocationRequest;

	/* Stores the current instantiation of the location client in this object */
	private LocationClient mLocationClient;

	public static String TAG = "NAM4JAndroidActivity";
	final static int MAX_RESULT = 1;

	private FileManager fileManager;

	private UPCPFTaskDescriptor amiTask;

	private GoogleMap map;

	MarkerOptions markerOptions;

	HashMap<String, Marker> ml;

	private final String reasonResearched = "RESEARCHED";
	private final String reasonAssigned = "ASSIGNED";

	Context context;

	private RelativeLayout mainRL;
	private RelativeLayout listRL;
	private RelativeLayout titleBarRL;

	private LatLng currentLocation;

	private ProgressDialog dialog;

	private int animationDuration = 300;

	private boolean showingMenu = false;

	/*
	 * The boolean is used to decide what to do after a LatLng has been
	 * geocoded. If true, it means that the user asked to publish a building so
	 * the related function is called. If false, the user asked to look for a
	 * resource in the LatLng, so the related function is called.
	 */
	private boolean requestPendingForBuilding = false;
	private boolean requestPendingForSensor = false;

	private boolean connected = false;

	private static String temperatureMessageType = "TemperatureNotification";
	private static String buildingMessageType = "BuildingNotification";
	private static String peer_listMessageType = "peer_list";

	/*
	 * The first time a location is available, the map is centered on it and the
	 * boolean is set to false so that further updates do not move the camera
	 */
	private boolean firstLocation = true;

	// Initial zoom level
	private int zoom = 15;

	/*
	 * Descriptors to set marker colors (blue ones are placed for assigned
	 * resources, red for researched resources)
	 */
	BitmapDescriptor bitmapDescriptorBlue, bitmapDescriptorRed;

	/* Set to true when the user asks to close the app. */
	private boolean close = false;

	Button menuButton, infoButton;

	int screenWidth = 0;
	int screenHeight = 0;

	/* Ratio between the menu size and the screen width */
	double menuWidth = 0.66;

	ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		context = this;

		/* Settings menu elements */
		ArrayList<MenuListElement> listElements = new ArrayList<MenuListElement>();

		listElements.add(new MenuListElement(getResources().getString(
				R.string.connectMenuTitle), getResources().getString(
				R.string.connectMenuSubTitle)));
		listElements.add(new MenuListElement(getResources().getString(
				R.string.settingsMenuTitle), getResources().getString(
				R.string.settingsMenuSubTitle)));
		listElements.add(new MenuListElement(getResources().getString(
				R.string.exitMenuTitle), getResources().getString(
				R.string.exitMenuSubTitle)));

		listView = (ListView) findViewById(R.id.ListViewContent);

		MenuListAdapter adapter = new MenuListAdapter(this,
				R.layout.menu_list_view_row, listElements);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new ListItemClickListener());

		mainRL = (RelativeLayout) findViewById(R.id.rlContainer);
		listRL = (RelativeLayout) findViewById(R.id.listContainer);
		titleBarRL = (RelativeLayout) findViewById(R.id.titleLl);

		/* Adding swipe gesture listener to the top bar */
		titleBarRL.setOnTouchListener(new SwipeAndClickListener());

		menuButton = (Button) findViewById(R.id.menuButton);
		menuButton.setOnTouchListener(new SwipeAndClickListener());

		infoButton = (Button) findViewById(R.id.infoButton);
		infoButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				AlertDialog dialog = new AlertDialog.Builder(context)
						.setMessage(R.string.aboutApp).setTitle(R.string.nam4j)
						.setCancelable(true).create();

				dialog.show();

				dialog.setCanceledOnTouchOutside(true);
			}
		});

		/*
		 * Check if the device has the Google Play Services installed and
		 * updated. They are necessary to use Google Maps
		 */
		int code = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(context);

		if (code != ConnectionResult.SUCCESS) {

			showErrorDialog(code);

			System.out.println("Google Play Services error");
			
			FrameLayout fl = (FrameLayout) findViewById(R.id.frameId);
			fl.removeAllViews();
		}

		else {
			// Create a new global location parameters object
			mLocationRequest = LocationRequest.create();

			// Set the update interval
			mLocationRequest
					.setInterval(LocationUtils.UPDATE_INTERVAL_IN_MILLISECONDS);

			// Use high accuracy
			mLocationRequest
					.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

			// Set the interval ceiling to one minute
			mLocationRequest
					.setFastestInterval(LocationUtils.FAST_INTERVAL_CEILING_IN_MILLISECONDS);

			bitmapDescriptorBlue = BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);

			bitmapDescriptorRed = BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_RED);

			/*
			 * Create a new location client, using the enclosing class to handle
			 * callbacks.
			 */
			mLocationClient = new LocationClient(this, this, this);

			map = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.mapview)).getMap();

			if (map != null) {

				/* Set default map center and zoom on Parma */
				double lat = 44.7950156;
				double lgt = 10.32547;
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
						lat, lgt), 12.0f));

				/*
				 * Adding listeners to the map respectively for zoom level
				 * change and onTap event.
				 */
				map.setOnCameraChangeListener(getCameraChangeListener());
				map.setOnMapClickListener(getOnMapClickListener());

				/* Set map type as normal (i.e. not the satellite view) */
				map.setMapType(com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL);

				/* Hide traffic layer */
				map.setTrafficEnabled(false);

				/*
				 * Enable the 'my-location' layer, which continuously draws an
				 * indication of a user's current location and bearing, and
				 * displays UI controls that allow the interaction with the
				 * location itself
				 */
				map.setMyLocationEnabled(true);

				ml = new HashMap<String, Marker>();

				// Get file manager for config files
				fileManager = FileManager.getFileManager();
				fileManager.createFiles();

				map.setOnMarkerClickListener(this);

			} else {
				AlertDialog.Builder dialog = new AlertDialog.Builder(this);
				dialog.setMessage("The map cannot be initialized.");
				dialog.setCancelable(true);
				dialog.setPositiveButton(getResources().getString(R.string.ok),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						});

				dialog.show();
			}

		}

	}

	/**
	 * Method to display the menu of the app.
	 */
	private void displaySideMenu() {

		TranslateAnimation animation = null;

		if (!showingMenu) {

			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mainRL
					.getLayoutParams();

			animation = new TranslateAnimation(0, listRL.getMeasuredWidth()
					- layoutParams.leftMargin, 0, 0);

			animation.setDuration(animationDuration);
			animation.setFillEnabled(true);
			animation.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {

					/*
					 * At the end, set the final position as the current one
					 */
					RelativeLayout.LayoutParams lpList = (LayoutParams) mainRL
							.getLayoutParams();
					lpList.setMargins(listRL.getMeasuredWidth(), 0,
							-listRL.getMeasuredWidth(), 0);
					mainRL.setLayoutParams(lpList);

					showingMenu = true;

				}
			});

		} else {

			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mainRL
					.getLayoutParams();

			animation = new TranslateAnimation(0, -layoutParams.leftMargin, 0,
					0);

			animation.setDuration(animationDuration);
			animation.setFillEnabled(true);
			animation.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {

					/*
					 * At the end, set the final position as the current one
					 */
					RelativeLayout.LayoutParams mainContenrLP = (LayoutParams) mainRL
							.getLayoutParams();
					mainContenrLP.setMargins(0, 0, 0, 0);
					mainRL.setLayoutParams(mainContenrLP);

					showingMenu = false;

				}
			});
		}

		mainRL.startAnimation(animation);

	}

	/*
	 * When the location changes, the map is moved so, to let the user choose
	 * the zoom level, this method defines a listener which sets the zoom
	 * variable to the value set by the user.
	 */
	public OnCameraChangeListener getCameraChangeListener() {
		return new OnCameraChangeListener() {
			@Override
			public void onCameraChange(CameraPosition position) {
				zoom = (int) position.zoom;
				// Log.d(NAM4JAndroidActivity.TAG, "New zoom value: " + zoom);
			}
		};
	}

	public OnMapClickListener getOnMapClickListener() {
		return new OnMapClickListener() {
			@Override
			public void onMapClick(LatLng arg0) {

				if (connected) {
					// Reverse geocoding the tapped location
					new ReverseGeocodingTask(getBaseContext()).execute(arg0);
				} else {
					Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.not_connected),
							Toast.LENGTH_LONG).show();
				}

			}
		};
	}

	/*
	 * Called when the Activity is no longer visible at all. Stop updates and
	 * disconnect.
	 */
	@Override
	public void onStop() {

		// If the client is connected
		if (mLocationClient != null && mLocationClient.isConnected()) {
			stopPeriodicUpdates();
		}

		// After disconnect() is called, the client is considered "dead".
		if (mLocationClient != null)
			mLocationClient.disconnect();

		super.onStop();
	}

	/*
	 * Called when the Activity is going into the background. Parts of the UI
	 * may be visible, but the Activity is inactive.
	 */
	@Override
	public void onPause() {
		super.onPause();
	}

	/*
	 * Called when the Activity is restarted, even before it becomes visible.
	 */
	@Override
	public void onStart() {

		super.onStart();

		/*
		 * Connect the client. Don't re-start any requests here; instead, wait
		 * for onResume()
		 */
		if (mLocationClient != null)
			mLocationClient.connect();

	}

	/*
	 * Called when the system detects that this Activity is now visible.
	 */
	@Override
	public void onResume() {
		super.onResume();

		getScreenSize();

		final RelativeLayout listRL = (RelativeLayout) findViewById(R.id.listContainer);
		final RelativeLayout listRLContainer = (RelativeLayout) findViewById(R.id.rlListViewContent);
		final RelativeLayout shadowRL = (RelativeLayout) findViewById(R.id.shadowContainer);

		RelativeLayout.LayoutParams menuListLP = (LayoutParams) listRL
				.getLayoutParams();

		/*
		 * Setting the ListView width as the container width without the shadow
		 * RelativeLayout
		 */
		menuListLP.width = (int) (screenWidth * menuWidth);
		listRL.setLayoutParams(menuListLP);

		RelativeLayout.LayoutParams listP = (LayoutParams) listRLContainer
				.getLayoutParams();
		listP.width = (int) (screenWidth * menuWidth)
				- shadowRL.getLayoutParams().width;
		listRLContainer.setLayoutParams(listP);

	}

	/**
	 * Method to get the size of the screen.
	 */
	private void getScreenSize() {
		final DisplayMetrics metrics = this.getResources().getDisplayMetrics();

		Rect rect = new Rect();
		Window win = getWindow();
		win.getDecorView().getWindowVisibleDisplayFrame(rect);
		int statusHeight = rect.top;
		int contentViewTop = win.findViewById(Window.ID_ANDROID_CONTENT)
				.getTop();
		int titleHeight = contentViewTop - statusHeight;

		/* Getting screen size */
		screenWidth = metrics.widthPixels;
		screenHeight = metrics.heightPixels - titleHeight - statusHeight;

	}

	/**
	 * Verify that Google Play services is available before making a request.
	 * 
	 * @return true if Google Play services is available, otherwise false
	 */
	private boolean servicesConnected() {

		Log.d(NAM4JAndroidActivity.TAG,
				"Checking for Google Play services availability...");

		// Check that Google Play services is available
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);

		// If Google Play services is available
		if (ConnectionResult.SUCCESS == resultCode) {
			// In debug mode, log the status
			Log.d(NAM4JAndroidActivity.TAG,
					this.getString(R.string.play_services_availability));

			// Continue
			return true;
			// Google Play services was not available for some reason
		} else {
			// Display an error dialog
			showErrorDialog(resultCode);
			return false;
		}
	}

	/*
	 * Called by Location Services when the request to connect the client
	 * finishes successfully. At this point, you can request the current
	 * location or start periodic updates
	 */
	@Override
	public void onConnected(Bundle bundle) {
		Log.d(NAM4JAndroidActivity.TAG,
				this.getString(R.string.location_services_connected));

		if (servicesConnected()) {

			// Centering map on last known location, if available
			Location mCurrentLocation = null;
			mCurrentLocation = mLocationClient.getLastLocation();
			if (mCurrentLocation != null) {
				if (firstLocation) {
					firstLocation = false;
					LatLng myLocation = new LatLng(
							mCurrentLocation.getLatitude(),
							mCurrentLocation.getLongitude());

					map.animateCamera(CameraUpdateFactory.newLatLngZoom(
							myLocation, zoom));
				}
			}

			startPeriodicUpdates();
		}
	}

	/*
	 * Called by Location Services if the connection to the location client
	 * drops because of an error.
	 */
	@Override
	public void onDisconnected() {
		Log.d(NAM4JAndroidActivity.TAG,
				this.getString(R.string.disconnected_location_services));
	}

	/*
	 * Called by Location Services if the attempt to Location Services fails.
	 */
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {

		/*
		 * Google Play services can resolve some errors it detects. If the error
		 * has a resolution, try sending an Intent to start a Google Play
		 * services activity that can resolve error.
		 */
		if (connectionResult.hasResolution()) {
			try {

				/* Start an Activity that tries to resolve the error */
				connectionResult.startResolutionForResult(this,
						LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);

				/*
				 * Thrown if Google Play services canceled the original
				 * PendingIntent
				 */

			} catch (IntentSender.SendIntentException e) {

				// Log the error
				e.printStackTrace();
			}
		} else {

			/*
			 * If no resolution is available, display a dialog to the user with
			 * the error.
			 */
			showErrorDialog(connectionResult.getErrorCode());
		}
	}

	/**
	 * Report location updates to the UI.
	 * 
	 * @param location
	 *            The updated location.
	 */
	@Override
	public void onLocationChanged(Location location) {

		if (location != null) {

			LatLng myLocation = new LatLng(location.getLatitude(),
					location.getLongitude());

			currentLocation = myLocation;

			/*
			 * Center map on current location just the first time a location is
			 * available
			 */
			if (firstLocation) {
				firstLocation = false;
				map.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,
						zoom));
			}

		} else
			Log.d(NAM4JAndroidActivity.TAG,
					this.getString(R.string.location_not_available));
	}

	/**
	 * In response to a request to start updates, send a request to Location
	 * Services
	 */
	private void startPeriodicUpdates() {

		if (mLocationClient != null)
			mLocationClient.requestLocationUpdates(mLocationRequest, this);

		Log.d(NAM4JAndroidActivity.TAG,
				this.getString(R.string.updates_started));
	}

	/**
	 * In response to a request to stop updates, send a request to Location
	 * Services
	 */
	private void stopPeriodicUpdates() {

		if (mLocationClient != null)
			mLocationClient.removeLocationUpdates(this);

		Log.d(NAM4JAndroidActivity.TAG,
				this.getString(R.string.updates_stopped));
	}

	/**
	 * Show a dialog returned by Google Play services for the connection error
	 * code
	 * 
	 * @param errorCode
	 *            An error code returned from onConnectionFailed
	 */
	private void showErrorDialog(int errorCode) {

		// Get the error dialog from Google Play services
		Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(errorCode,
				this, LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);

		// If Google Play services can provide an error dialog
		if (errorDialog != null) {

			// Create a new DialogFragment in which to show the error dialog
			ErrorDialogFragment errorFragment = new ErrorDialogFragment();

			// Set the dialog in the DialogFragment
			errorFragment.setDialog(errorDialog);

			// Show the error dialog in the DialogFragment
			errorFragment.show(getSupportFragmentManager(),
					NAM4JAndroidActivity.TAG);
		} else {
			Toast.makeText(context,
					"Incompatible version of Google Play Services",
					Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * Define a DialogFragment to display the error dialog generated in
	 * showErrorDialog.
	 */
	public static class ErrorDialogFragment extends DialogFragment {

		// Global field to contain the error dialog
		private Dialog mDialog;

		/**
		 * Default constructor. Sets the dialog field to null
		 */
		public ErrorDialogFragment() {
			super();
			mDialog = null;
		}

		/**
		 * Set the dialog to display
		 * 
		 * @param dialog
		 *            An error dialog
		 */
		public void setDialog(Dialog dialog) {
			mDialog = dialog;
		}

		/*
		 * This method must return a Dialog to the DialogFragment.
		 */
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return mDialog;
		}
		
		/* Close the app when the user clicks on the dialog button */
		@Override
		public void onDismiss(DialogInterface dialog) {
			super.onDismiss(dialog);
			
			getActivity().finish();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		String message = getResources().getString(
				R.string.exitOnBackButtonPressed);

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(message)
					.setCancelable(false)
					.setPositiveButton(getResources().getString(R.string.yes),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface iDialog,
										int id) {

									if (connected) {

										close = true;

										/*
										 * The progress dialog is not created by
										 * the AsyncTask because the peer just
										 * sends a message to the bootstrap.
										 * This takes very few time and the
										 * dialog would disappear almost
										 * immediately. By showing it before
										 * executing the AsyncTask, it can stay
										 * on the screen until the bootstrap
										 * informs the peer that it is part of
										 * the network. Thus, the dialog is
										 * removed from the function listening
										 * for received messages
										 * onReceivedMessage(String).
										 */
										dialog = new ProgressDialog(
												NAM4JAndroidActivity.this);
										dialog.setMessage(NAM4JAndroidActivity.this
												.getString(R.string.pwLeaving));
										dialog.show();

										new LeaveNetwork(null).execute();

										connected = false;

									} else {

										// finish();

										/*
										 * AFTER LEAVING THE CHORD NETOWRK, THE
										 * APP GETS CLOSED
										 */

										/*
										 * Notify the system to finalize and
										 * collect all objects of the app on
										 * exit so that the virtual machine
										 * running the app can be killed by the
										 * system without causing issues.
										 * 
										 * NOTE: If this is set to true then the
										 * virtual machine will not be killed
										 * until all of its threads have closed.
										 */
										System.runFinalizersOnExit(true);

										/*
										 * Force the system to close the app
										 * down completely instead of retaining
										 * it in the background. The virtual
										 * machine that runs the app will be
										 * killed. The app will be completely
										 * created as a new app in a new virtual
										 * machine running in a new process if
										 * the user starts the app again.
										 */
										System.exit(0);
									}
								}
							})
					.setNegativeButton(getResources().getString(R.string.no),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});

			AlertDialog alert = builder.create();
			alert.show();

			return true;
		}

		else if (keyCode == KeyEvent.KEYCODE_MENU) {
			displaySideMenu();
		}

		return super.onKeyDown(keyCode, event);
		// return true;
	}

	@Override
	public void onReceivedResource(ResourceDescriptor rd, String reason) {

		/*
		 * The if on the "connected" boolean is present because if the user
		 * leaves the network, the node may receive some answers to requests
		 * sent to the network before leaving it.
		 */
		if (connected) {

			if (reason.equalsIgnoreCase(reasonResearched)) {
				Log.d(NAM4JAndroidActivity.TAG,
						"Received the researched resource");

				JSONObject obj;
				try {
					obj = new JSONObject(rd.getAttachment());

					JSONObject locationObj = obj.getJSONObject("location");
					JSONObject locationValue = new JSONObject(
							locationObj.getString("value"));
					JSONObject building = locationValue
							.getJSONObject("building");

					// The address to geocode
					String buildingValue = building.getString("value");

					new GeocodeTask(getBaseContext()).execute(buildingValue);

				} catch (JSONException e) {
					e.printStackTrace();
				}

			} else if (reason.equalsIgnoreCase(reasonAssigned)) {

				Log.d(NAM4JAndroidActivity.TAG,
						"Received a resource for the peer to be its responsible");

				String msgType = rd.getType();

				/*
				 * Managing notifications
				 */
				if (msgType
						.equalsIgnoreCase(NAM4JAndroidActivity.temperatureMessageType)) {

					Log.d(NAM4JAndroidActivity.TAG,
							"The resource is a temperature notification");
				}

				if (msgType
						.equalsIgnoreCase(NAM4JAndroidActivity.buildingMessageType)) {

					Log.d(NAM4JAndroidActivity.TAG,
							"The resource is a building notification");
				}

				ArrayList<ResourceParameter> resDesc = rd.getParameterList();

				for (int i = 0; i < resDesc.size(); i++) {
					ResourceParameter resParam = resDesc.get(i);
					String name = resParam.getName();
					String value = resParam.getValue();

					if (name.equalsIgnoreCase("Location")) {
						try {

							JSONObject obj = new JSONObject(value);

							JSONObject buildingObj = obj
									.getJSONObject("building");
							JSONObject latObj = obj.getJSONObject("latitude");
							JSONObject lgtObj = obj.getJSONObject("longitude");

							final String address = buildingObj
									.getString("value");
							final String lat = latObj.getString("value");
							final String lgt = lgtObj.getString("value");

							LatLng p = new LatLng(Double.parseDouble(lat),
									Double.parseDouble(lgt));

							if (ml.get(address) == null) {

								Log.d(NAM4JAndroidActivity.TAG,
										"Added a new marker on the map for position "
												+ p.latitude
												+ " , "
												+ p.longitude
												+ "\nThe resource has been assigned to the peer.");

								/*
								 * The following Runnable is because the only
								 * way to interact with the GUI is through the
								 * UI thread
								 */
								runOnUiThread(new Runnable() {
									public void run() {

										Toast.makeText(
												context,
												"Added a new marker on the map at address "
														+ address + " (" + lat
														+ ", " + lgt + ")",
												Toast.LENGTH_LONG).show();

										Marker marker = map
												.addMarker(new MarkerOptions()
														.position(
																new LatLng(
																		Double.parseDouble(lat),
																		Double.parseDouble(lgt)))
														.icon(bitmapDescriptorBlue)
														.title(address)
														.snippet(""));

										ml.put(address, marker);

									}
								});

							}

							else {
								System.out.println("Marker is already present");

								/*
								 * Setting the icon as blue since the node is
								 * responsible for it
								 */
								ml.get(address).setIcon(bitmapDescriptorBlue);
							}

							System.out.println("Sensor address: " + address
									+ " ; latitude: " + lat + " ; longitude: "
									+ lgt);

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	private void startBuildingPublishActivity(String addresses) {

		requestPendingForBuilding = false;

		if (connected) {

			Bundle args = new Bundle();
			args.putParcelable("CurrentLocation", currentLocation);

			Intent i = new Intent(this, BuildingPublishActivity.class);
			i.putExtra("Address", addresses);
			i.putExtra("Bundle", args);

			startActivity(i);

		}
	}

	private void startSensorPublishActivity(String addresses) {

		requestPendingForSensor = false;

		if (connected) {

			Bundle args = new Bundle();
			args.putParcelable("CurrentLocation", currentLocation);

			Intent i = new Intent(this, SensorPublishActivity.class);
			i.putExtra("Address", addresses);
			i.putExtra("Bundle", args);

			startActivity(i);

		}
	}

	private void connect() {

		/*
		 * The progress dialog is not created by the AsyncTask because the peer
		 * just sends a message to the bootstrap. This takes very few time and
		 * the dialog would disappear almost immediately. By showing it before
		 * executing the AsyncTask, it can stay on the screen until the
		 * bootstrap informs the peer that it is part of the network. Thus, the
		 * dialog is removed from the function listening for received messages
		 * onReceivedMessage(String).
		 */
		dialog = new ProgressDialog(NAM4JAndroidActivity.this);
		dialog.setMessage(NAM4JAndroidActivity.this
				.getString(R.string.pwJoining));
		dialog.show();

		new JoinNetwork(null).execute();
	}

	private void afterConnected() {

		GamiNode.addResourceListener(this);
		GamiNode.addMessageListener(this);

		amiTask = new UPCPFTaskDescriptor("AmITask", "T1");
		amiTask.setState("UNSTARTED");
	}

	private void showSettings() {
		Intent i = new Intent(this, SettingsActivity.class);
		startActivity(i);
	}

	@Override
	public boolean onMarkerClick(Marker marker) {

		LatLng p = marker.getPosition();

		System.out.println("--------- Clicked " + p.latitude + " ; "
				+ p.longitude);

		if (connected) {
			// Reverse geocoding the tapped location
			new ReverseGeocodingTask(getBaseContext()).execute(p);
		} else {
			Toast.makeText(getApplicationContext(),
					getResources().getString(R.string.not_connected),
					Toast.LENGTH_LONG).show();
		}

		return false;
	}

	private void showAddress(final String addressTouch) {

		Log.d(NAM4JAndroidActivity.TAG, "Tapped address: " + addressTouch);

		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setMessage("Do you want to see information about building located in "
				+ addressTouch + " ?");
		dialog.setCancelable(true);
		dialog.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
				Intent intent = new Intent(NAM4JAndroidActivity.this,
						BuildingLookupActivity.class);

				intent.putExtra("AddressBuilding", addressTouch);
				NAM4JAndroidActivity.this.startActivity(intent);
			}
		});
		dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
			}
		});

		dialog.show();

	}

	private void leftNetwork() {

		if (dialog.isShowing()) {
			dialog.dismiss();
		}

		Toast.makeText(getApplicationContext(),
				getResources().getString(R.string.disconnected_from_network),
				Toast.LENGTH_LONG).show();

		/* Settings menu elements */
		ArrayList<MenuListElement> listElements = new ArrayList<MenuListElement>();

		listElements.add(new MenuListElement(getResources().getString(
				R.string.connectMenuTitle), getResources().getString(
				R.string.connectMenuSubTitle)));
		listElements.add(new MenuListElement(getResources().getString(
				R.string.settingsMenuTitle), getResources().getString(
				R.string.settingsMenuSubTitle)));
		listElements.add(new MenuListElement(getResources().getString(
				R.string.exitMenuTitle), getResources().getString(
				R.string.exitMenuSubTitle)));

		MenuListAdapter adapter = new MenuListAdapter(this,
				R.layout.menu_list_view_row, listElements);
		listView.setAdapter(adapter);

		/*
		 * Cleaning the map and the HashMap containing the known resources
		 */
		runOnUiThread(new Runnable() {
			public void run() {
				map.clear();
				ml.clear();
			}
		});
	}

	private void closeApp() {

		if (dialog.isShowing()) {
			dialog.dismiss();
		}

		// finish();
		System.runFinalizersOnExit(true);
		System.exit(0);
	}

	private void addReceivedResourceMarkerOnMap(final LatLng p,
			final String address) {

		if (p != null) {

			if (ml.get(address) == null) {

				Log.d(NAM4JAndroidActivity.TAG,
						"Added a new marker on the map for position "
								+ p.latitude + " , " + p.longitude
								+ " for the researched resource");

				/*
				 * The following Runnable is because the only way to interact
				 * with the GUI is through the UI thread
				 */
				runOnUiThread(new Runnable() {
					public void run() {

						Toast.makeText(
								context,
								"Added a new marker on the map at address "
										+ address + " (" + p.latitude + ", "
										+ p.longitude
										+ ") for the researched resource",
								Toast.LENGTH_LONG).show();

						Marker marker = map.addMarker(new MarkerOptions()
								.position(p).icon(bitmapDescriptorRed)
								.title(address).snippet(""));

						ml.put(address, marker);

					}
				});

			}

			else {
				System.out.println("Marker is already present");
			}
		}
	}

	@Override
	public void onReceivedMessage(String msg) {

		System.out.println("Received message of type " + msg);

		/* Connected to the Chord network */
		if (msg.equalsIgnoreCase(peer_listMessageType) && !connected) {

			/* Settings menu elements */
			ArrayList<MenuListElement> listElements = new ArrayList<MenuListElement>();

			listElements.add(new MenuListElement(getResources().getString(
					R.string.disconnectMenuTitle), getResources().getString(
					R.string.disconnectMenuSubTitle)));
			listElements.add(new MenuListElement(getResources().getString(
					R.string.publishBuildingMenuTitle), getResources()
					.getString(R.string.publishBuildingMenuSubTitle)));
			listElements.add(new MenuListElement(getResources().getString(
					R.string.publishSensorgMenuTitle), getResources()
					.getString(R.string.publishSensorgMenuSubTitle)));
			listElements.add(new MenuListElement(getResources().getString(
					R.string.exitMenuTitle), getResources().getString(
					R.string.exitMenuSubTitle)));

			final MenuListAdapter adapter = new MenuListAdapter(this,
					R.layout.menu_list_view_row, listElements);

			connected = true;

			runOnUiThread(new Runnable() {
				public void run() {

					if (dialog.isShowing()) {
						dialog.dismiss();
					}

					Toast.makeText(
							context,
							getResources().getString(
									R.string.connection_started),
							Toast.LENGTH_LONG).show();

					listView.setAdapter(adapter);

				}
			});
		}

	}

	private class ReverseGeocodingTask extends AsyncTask<LatLng, Void, String> {
		Context mContext;

		public ReverseGeocodingTask(Context context) {
			super();
			mContext = context;
		}

		// Finding address using reverse geocoding
		@Override
		protected String doInBackground(LatLng... params) {

			Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());

			double latitude = params[0].latitude;
			double longitude = params[0].longitude;

			List<Address> addresses = null;
			String addressTouch = null;

			try {
				addresses = geocoder.getFromLocation(latitude, longitude, 1);
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (addresses != null && addresses.size() > 0) {
				addressTouch = addresses.get(0).getAddressLine(0);
			}

			if (addressTouch == null) {

				Log.d(NAM4JAndroidActivity.TAG,
						"Geocoder service is not available; trying with a http request to Google Maps...");

				String URL = "http://maps.googleapis.com/maps/api/geocode/json?latlng="
						+ latitude + "," + longitude + "&sensor=false";

				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response;
				String responseString = null;
				try {
					response = httpclient.execute(new HttpGet(URL));
					StatusLine statusLine = response.getStatusLine();
					if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						response.getEntity().writeTo(out);
						out.close();
						responseString = out.toString();

						JSONObject obj = new JSONObject(responseString);
						JSONArray resultsArray = obj.getJSONArray("results");
						JSONObject resultsArrayFirstElement = resultsArray
								.getJSONObject(0);

						String formattedAddress = resultsArrayFirstElement.get(
								"formatted_address").toString();
						System.out.println("Formatted address: "
								+ formattedAddress);

						addressTouch = formattedAddress;

					} else {

						// Closing the connection
						response.getEntity().getContent().close();
						throw new IOException(statusLine.getReasonPhrase());
					}
				} catch (ClientProtocolException e) {
				} catch (IOException e) {
				} catch (JSONException e) {
				}

				return addressTouch;
			} else {
				return addressTouch;
			}

		}

		@Override
		protected void onPostExecute(String addresses) {

			if (addresses != null) {
				if (requestPendingForBuilding)
					startBuildingPublishActivity(addresses);
				else if (requestPendingForSensor)
					startSensorPublishActivity(addresses);
				else
					showAddress(addresses);
			} else {
				Toast.makeText(
						getApplicationContext(),
						getResources().getString(
								R.string.geocoder_not_available),
						Toast.LENGTH_LONG).show();
			}
		}
	}

	private class GeocodeTask extends AsyncTask<String, Void, LatLng> {
		Context mContext;
		String strAddress;

		public GeocodeTask(Context context) {
			super();
			mContext = context;
		}

		// Reverse geocoding current location
		@Override
		protected LatLng doInBackground(String... params) {

			Geocoder coder = new Geocoder(mContext);
			List<Address> addresses = null;
			LatLng p1 = null;

			strAddress = params[0];

			try {
				addresses = coder.getFromLocationName(strAddress, 5);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (addresses != null && addresses.size() > 0) {
				Address location = addresses.get(0);

				p1 = new LatLng((float) (location.getLatitude()),
						(float) (location.getLongitude()));

			} else {

				Log.d(NAM4JAndroidActivity.TAG,
						"Geocoder service is not available; trying with a http request to Google Maps...");

				String URL = "http://maps.googleapis.com/maps/api/geocode/json?address="
						+ strAddress.replaceAll("\\s+", "+") + "&sensor=false";

				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response;
				String responseString = null;

				try {
					response = httpclient.execute(new HttpGet(URL));
					StatusLine statusLine = response.getStatusLine();
					if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						response.getEntity().writeTo(out);
						out.close();
						responseString = out.toString();

						JSONObject obj = new JSONObject(responseString);
						JSONArray resultsArray = obj.getJSONArray("results");
						JSONObject resultsArrayFirstElement = resultsArray
								.getJSONObject(0);

						JSONObject geometryObj = resultsArrayFirstElement
								.getJSONObject("geometry");
						JSONObject locationObj = geometryObj
								.getJSONObject("location");

						Float lat = Float.parseFloat(locationObj
								.getString("lat"));
						Float lng = Float.parseFloat(locationObj
								.getString("lng"));

						p1 = new LatLng((double) (lat), (double) (lng));

					} else {
						// Closes the connection.
						response.getEntity().getContent().close();
						throw new IOException(statusLine.getReasonPhrase());
					}
				} catch (ClientProtocolException e) {
				} catch (IOException e) {
				} catch (JSONException e) {
				}

			}

			return p1;

		}

		@Override
		protected void onPostExecute(LatLng p) {

			if (p != null) {
				addReceivedResourceMarkerOnMap(p, strAddress);
			} else {
				Toast.makeText(
						getApplicationContext(),
						getResources().getString(
								R.string.geocoder_not_available),
						Toast.LENGTH_LONG).show();
			}
		}
	}

	private class JoinNetwork extends AsyncTask<Void, Void, Void> {

		public JoinNetwork(Void v) {
			super();
		}

		@Override
		protected Void doInBackground(Void... params) {

			GamiNode.getAndroidGamiNode();

			return null;
		}

		@Override
		protected void onPostExecute(Void v) {

			afterConnected();
		}
	}

	private class LeaveNetwork extends AsyncTask<Void, Void, Void> {

		public LeaveNetwork(Void v) {
			super();
		}

		@Override
		protected Void doInBackground(Void... params) {

			GamiNode.disconnect();

			return null;
		}

		@Override
		protected void onPostExecute(Void v) {
			if (close) {
				closeApp();
			} else {
				leftNetwork();
			}
		}
	}

	class ListItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> clickedList,
				View clickedElement, final int clickedElementPosition,
				long clickedRowId) {

			if (showingMenu) {

				final RelativeLayout mainRL = (RelativeLayout) findViewById(R.id.rlContainer);
				final RelativeLayout listRL = (RelativeLayout) findViewById(R.id.listContainer);

				TranslateAnimation animation = new TranslateAnimation(0,
						-listRL.getMeasuredWidth(), 0, 0);

				animation.setDuration(animationDuration);
				animation.setFillEnabled(true);
				animation.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {
					}

					@Override
					public void onAnimationRepeat(Animation animation) {
					}

					@Override
					public void onAnimationEnd(Animation animation) {

						/*
						 * At the end, set the final position as the current one
						 */
						RelativeLayout.LayoutParams mainContainerLP = (LayoutParams) mainRL
								.getLayoutParams();
						mainContainerLP.setMargins(0, 0, 0, 0);
						mainRL.setLayoutParams(mainContainerLP);

						showingMenu = false;

						if (connected) {
							switch (clickedElementPosition) {
							case 0:

								AlertDialog.Builder bDialog = new AlertDialog.Builder(
										context);
								bDialog.setMessage(getResources().getString(
										R.string.leave));
								bDialog.setCancelable(true);
								bDialog.setPositiveButton(getResources().getString(R.string.yes),
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface iDialog,
													int id) {
												iDialog.dismiss();

												/*
												 * The progress dialog is not
												 * created by the AsyncTask
												 * because the peer just sends a
												 * message to the bootstrap.
												 * This takes very few time and
												 * the dialog would disappear
												 * almost immediately. By
												 * showing it before executing
												 * the AsyncTask, it can stay on
												 * the screen until the
												 * bootstrap informs the peer
												 * that it is part of the
												 * network. Thus, the dialog is
												 * removed from the function
												 * listening for received
												 * messages
												 * onReceivedMessage(String).
												 */
												dialog = new ProgressDialog(
														NAM4JAndroidActivity.this);
												dialog.setMessage(NAM4JAndroidActivity.this
														.getString(R.string.pwLeaving));
												dialog.show();

												new LeaveNetwork(null)
														.execute();

												connected = false;
											}
										});
								bDialog.setNegativeButton("No",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int id) {
												dialog.dismiss();
											}
										});

								bDialog.show();

								break;

							case 1:

								if (currentLocation != null) {

									/*
									 * Specifying that the GeoCoder class must
									 * call the function to start publishing by
									 * setting the boolean to true
									 */
									requestPendingForBuilding = true;

									new ReverseGeocodingTask(getBaseContext())
											.execute(currentLocation);
								} else {
									Bundle args = new Bundle();
									args.putParcelable("CurrentLocation", null);

									Intent i = new Intent(context,
											BuildingPublishActivity.class);
									i.putExtra("Address", "");
									i.putExtra("Bundle", args);

									startActivity(i);
								}

								break;

							case 2:

								if (currentLocation != null) {

									/*
									 * Specifying that the GeoCoder class must
									 * call the function to start publishing by
									 * setting the boolean to true
									 */
									requestPendingForSensor = true;

									new ReverseGeocodingTask(getBaseContext())
											.execute(currentLocation);
								} else {
									Bundle args = new Bundle();
									args.putParcelable("CurrentLocation", null);

									Intent i = new Intent(context,
											SensorPublishActivity.class);
									i.putExtra("Address", "");
									i.putExtra("Bundle", args);

									startActivity(i);
								}

								break;

							case 3:

								AlertDialog.Builder builder = new AlertDialog.Builder(
										context);
								builder.setMessage(
										context.getResources()
												.getString(
														R.string.exitOnBackButtonPressed))
										.setCancelable(false)
										.setPositiveButton(
												getResources().getString(R.string.yes),
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface iDialog,
															int id) {

														if (connected) {

															close = true;

															dialog = new ProgressDialog(
																	NAM4JAndroidActivity.this);
															dialog.setMessage(NAM4JAndroidActivity.this
																	.getString(R.string.pwLeaving));
															dialog.show();

															new LeaveNetwork(
																	null)
																	.execute();

															connected = false;

														} else {
															System.runFinalizersOnExit(true);

															System.exit(0);
														}
													}
												})
										.setNegativeButton(
												getResources().getString(R.string.no),
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int id) {
														dialog.cancel();
													}
												});

								AlertDialog alert = builder.create();
								alert.show();

								break;
							}
						} else {
							switch (clickedElementPosition) {
							case 0:

								connect();
								Log.d(NAM4JAndroidActivity.TAG, context
										.getString(R.string.menu_connect));

								break;

							case 1:

								showSettings();
								Log.d(NAM4JAndroidActivity.TAG, context
										.getString(R.string.menu_settings));

								break;

							case 2:

								AlertDialog.Builder builder = new AlertDialog.Builder(
										context);
								builder.setMessage(
										context.getResources()
												.getString(
														R.string.exitOnBackButtonPressed))
										.setCancelable(false)
										.setPositiveButton(
												getResources().getString(R.string.yes),
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface iDialog,
															int id) {

														if (connected) {

															close = true;

															dialog = new ProgressDialog(
																	NAM4JAndroidActivity.this);
															dialog.setMessage(NAM4JAndroidActivity.this
																	.getString(R.string.pwLeaving));
															dialog.show();

															new LeaveNetwork(
																	null)
																	.execute();

															connected = false;

														} else {
															System.runFinalizersOnExit(true);

															System.exit(0);
														}
													}
												})
										.setNegativeButton(
												getResources().getString(R.string.no),
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int id) {
														dialog.cancel();
													}
												});

								AlertDialog alert = builder.create();
								alert.show();

								break;
							}
						}

					}
				});

				mainRL.startAnimation(animation);

			}
		}
	}

	public class SwipeAndClickListener implements OnTouchListener {

		static final String logTag = "ActivitySwipeDetector";

		int sourceId;

		/*
		 * To distinguish between click and swipe, set the max duration for a
		 * click in milliseconds
		 */
		private static final int MAX_CLICK_DURATION = 200;

		/* Instant the user clicks */
		private long startClickTime;

		boolean isAnimating = false;

		/* Change sensitivity based on screen resolution */
		static final int MIN_DISTANCE = 50;

		private float downX, downY, upX, upY;

		public SwipeAndClickListener() {
		}

		public void onRightToLeftSwipe() {

			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mainRL
					.getLayoutParams();

			TranslateAnimation animation = null;

			final RelativeLayout mainRL = (RelativeLayout) findViewById(R.id.rlContainer);

			if (layoutParams.leftMargin >= listRL.getMeasuredWidth() / 2) {
				animation = new TranslateAnimation(0, listRL.getMeasuredWidth()
						- layoutParams.leftMargin, 0, 0);
			} else {
				animation = new TranslateAnimation(0, -layoutParams.leftMargin,
						0, 0);
			}

			animation.setDuration(animationDuration);
			animation.setFillEnabled(true);
			animation.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {

					/*
					 * At the end, set the final position as the current one
					 */
					RelativeLayout.LayoutParams mainContainerLP = (LayoutParams) mainRL
							.getLayoutParams();

					if (mainContainerLP.leftMargin >= listRL.getMeasuredWidth() / 2) {
						mainContainerLP.setMargins(listRL.getMeasuredWidth(),
								0, -listRL.getMeasuredWidth(), 0);
						showingMenu = true;
					} else {
						mainContainerLP.setMargins(0, 0, 0, 0);
						showingMenu = false;
					}

					mainRL.setLayoutParams(mainContainerLP);

					isAnimating = false;

				}
			});

			mainRL.startAnimation(animation);

		}

		public void onLeftToRightSwipe() {

			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mainRL
					.getLayoutParams();

			TranslateAnimation animation = null;

			if (layoutParams.leftMargin >= listRL.getMeasuredWidth() / 2) {
				animation = new TranslateAnimation(0, listRL.getMeasuredWidth()
						- layoutParams.leftMargin, 0, 0);
			} else {
				animation = new TranslateAnimation(0, -layoutParams.leftMargin,
						0, 0);
			}

			animation.setDuration(animationDuration);
			animation.setFillEnabled(true);
			animation.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {

					/*
					 * At the end, set the final position as the current one
					 */
					RelativeLayout.LayoutParams mainContainerLP = (LayoutParams) mainRL
							.getLayoutParams();

					if (mainContainerLP.leftMargin >= listRL.getMeasuredWidth() / 2) {
						mainContainerLP.setMargins(listRL.getMeasuredWidth(),
								0, -listRL.getMeasuredWidth(), 0);
						showingMenu = true;
					} else {
						mainContainerLP.setMargins(0, 0, 0, 0);
						showingMenu = false;
					}

					mainRL.setLayoutParams(mainContainerLP);

					isAnimating = false;

				}
			});

			mainRL.startAnimation(animation);

		}

		public void onTopToBottomSwipe() {

		}

		public void onBottomToTopSwipe() {

		}

		public boolean onTouch(View v, MotionEvent event) {

			sourceId = v.getId();

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: {

				startClickTime = Calendar.getInstance().getTimeInMillis();

				downX = event.getX();
				downY = event.getY();
				return true;
			}
			case MotionEvent.ACTION_UP: {

				long clickDuration = Calendar.getInstance().getTimeInMillis()
						- startClickTime;

				if (clickDuration < MAX_CLICK_DURATION) {

					/* Click event has occurred */

					/*
					 * Process click event just if the button was clicked. If
					 * the user pressed the bar, ignore it
					 */
					if (sourceId == menuButton.getId()) {
						displaySideMenu();
					}

				} else {

					/* Swipe event has occurred */

					upX = event.getX();
					upY = event.getY();

					float deltaX = downX - upX;
					float deltaY = downY - upY;

					if (!isAnimating) {

						if (!showingMenu) {

							RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mainRL
									.getLayoutParams();

							if (layoutParams.leftMargin > 0) {
								isAnimating = true;
								onRightToLeftSwipe();
							}
						} else {

							RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mainRL
									.getLayoutParams();

							if (layoutParams.leftMargin < listRL
									.getMeasuredWidth()) {
								isAnimating = true;
								onLeftToRightSwipe();
							}
						}
					}

					// swipe horizontal?
					if (Math.abs(deltaX) > MIN_DISTANCE) {

						// left or right
						if (deltaX < 0) {
							// this.onLeftToRightSwipe();
							return true;
						}

						if (deltaX > 0) {
							// this.onRightToLeftSwipe();
							return true;
						}
					} else {
					}

					// swipe vertical?
					if (Math.abs(deltaY) > MIN_DISTANCE) {
						// top or down
						if (deltaY < 0) {
							this.onTopToBottomSwipe();
							return true;
						}
						if (deltaY > 0) {
							this.onBottomToTopSwipe();
							return true;
						}
					} else {
					}

					return false; // no swipe horizontally and no swipe
									// vertically
				}
			}
			case MotionEvent.ACTION_MOVE: {

				upX = event.getX();

				float deltaX = downX - upX;

				RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mainRL
						.getLayoutParams();

				if (!showingMenu) {
					if (!isAnimating
							&& ((layoutParams.leftMargin - deltaX >= 0) && (layoutParams.leftMargin
									- deltaX <= listRL.getMeasuredWidth()))) {

						layoutParams.leftMargin -= deltaX;
						layoutParams.rightMargin += deltaX;

						mainRL.setLayoutParams(layoutParams);
					}
				} else {
					if (!isAnimating
							&& ((layoutParams.leftMargin - deltaX <= listRL
									.getMeasuredWidth()) && (layoutParams.leftMargin
									- deltaX >= 0))) {

						layoutParams.leftMargin -= deltaX;
						layoutParams.rightMargin += deltaX;

						mainRL.setLayoutParams(layoutParams);
					}
				}

				return false;

			}
			}
			return false;
		}

	}

}