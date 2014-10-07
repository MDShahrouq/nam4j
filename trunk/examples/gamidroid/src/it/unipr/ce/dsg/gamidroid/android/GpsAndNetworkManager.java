package it.unipr.ce.dsg.gamidroid.android;

import java.math.BigDecimal;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

/**
 * Classe per la gestione della rete internet e del gps.
 * 
 * @author Francesca Bassi, Laura Belli
 * 
 */
public class GpsAndNetworkManager {

	private LocationManager locationManager;
	private Location userLocation;

	private static final String TAG = "GpsAndNetworkManager";

	private Context context;

	public GpsAndNetworkManager(Context context) {
		this.context = context;
	}

	public Location getUserLocation() {
		return userLocation;
	}

	public void setUserLocation(Location userLocation) {
		this.userLocation = userLocation;
	}

	public LocationManager getLocationManager() {
		return locationManager;
	}

	public void setLocationManager(LocationManager locationManager) {
		this.locationManager = locationManager;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	/**
	 * Method to start the user geo-localization from the Internet and GPS.
	 * 
	 * @param l 
	 * location listener
	 */
	public void startLocalizationService(LocationListener l) {
		if (locationManager == null) {
			Log.d(TAG, "Initiliazing LocationManager ...");
			locationManager = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);
		}

		Log
				.d(TAG,
						"Requesting Location Updates (NETWORK_PROVIDER,GPS_PROVIDER)...");

		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 0, 0, l);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, l);

	}

	/**
	 * Method to stop the listener of the location manager.
	 * 
	 * @param l 
	 * location listener
	 */
	public void stopLocalizationService(LocationListener l) {
		if (locationManager != null) {
			Log.d(TAG, "Stopping LocationManager ...");
			locationManager.removeUpdates(l);

		}
	}

	/**
	 * Method that initializes the user location based on the active provider
	 * (Internet or GPS).
	 */
	public void initUserLocation() {

		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			this.userLocation = new Location(LocationManager.GPS_PROVIDER);
		} else if (locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			this.userLocation = new Location(LocationManager.NETWORK_PROVIDER);
		} else
			this.userLocation = new Location(LocationManager.NETWORK_PROVIDER);

	}

	/**
	 * Method that initializes the custom location chosen by the user.
	 */
	public void initUserLocationFromPick() {

		if (locationManager == null) {
			Log.d(TAG, "Initiliazing LocationManager ...");
			locationManager = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);
		}

		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			this.userLocation = new Location(LocationManager.GPS_PROVIDER);
		} else if (locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			this.userLocation = new Location(LocationManager.NETWORK_PROVIDER);
		} else
			this.userLocation = new Location(LocationManager.NETWORK_PROVIDER);

	}

	/**
	 * Method that rounds the location to 2 decimals
	 * 
	 * @param location
	 *            latitudine and longitude
	 * @return rounded location 
	 */
	public static Location roundLocation(Location location) {

		BigDecimal bgLat = new BigDecimal(location.getLatitude());
		bgLat = bgLat.setScale(2, BigDecimal.ROUND_HALF_UP);
		double newLat = bgLat.doubleValue();

		BigDecimal bgLong = new BigDecimal(location.getLongitude());
		bgLong = bgLong.setScale(2, BigDecimal.ROUND_HALF_UP);
		double newLong = bgLong.doubleValue();

		Location roundedL = new Location(LocationManager.NETWORK_PROVIDER);
		roundedL.setLatitude(newLat);
		roundedL.setLongitude(newLong);

		return roundedL;

	}

	/**
	 * Method that checks if the new sensed location has changed
	 * with respect to the user's one.
	 * 
	 * @param location
	 * @return true if the current location is substantially different 
	 *         false otherwise.
	 */
	public boolean filterLocationChanges(Location location) {

		Location roundedLocation = roundLocation(location);

		if (roundedLocation.getLatitude() != userLocation.getLatitude()
				|| roundedLocation.getLongitude() != userLocation
						.getLongitude()) {
			userLocation = new Location(roundedLocation);
			Log.v(TAG, "Location changed: " + userLocation.getLatitude() + " "
					+ userLocation.getLongitude());
			return true;
		} else
			return false;
	}

}
