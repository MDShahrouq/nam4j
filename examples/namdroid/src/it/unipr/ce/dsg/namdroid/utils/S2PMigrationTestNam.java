package it.unipr.ce.dsg.namdroid.utils;

import it.unipr.ce.dsg.nam4j.impl.NetworkedAutonomicMachine;
import it.unipr.ce.dsg.nam4j.impl.mobility.peer.MccNamPeer;
import it.unipr.ce.dsg.nam4j.interfaces.IMobilityItemAvailability;
import it.unipr.ce.dsg.namdroid.interfaces.IMobilityItemIsAvailableObserver;
import it.unipr.ce.dsg.s2p.centralized.utils.Key;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.os.Environment;

public class S2PMigrationTestNam extends NetworkedAutonomicMachine implements IMobilityItemAvailability {
	
	private static S2PMigrationTestNam s2PMigrationTestNam = null;
	private static String pathToSaveFile = Environment.getExternalStorageDirectory().toString() + "/";
	private MccNamPeer peer;
	private ArrayList<IMobilityItemIsAvailableObserver> listeners;

	private S2PMigrationTestNam(Context mContext, String confFile) {
		super(10, pathToSaveFile, 3);
		
		this.listeners = new ArrayList<IMobilityItemIsAvailableObserver>();

		Key key = new Key((new Random().nextInt()) + "");

		// Random key
		Random ran = new Random();
		
		// Random port
		int port = 1024 + ran.nextInt(9999 - 1024);
		
		this.setId("S2PMigrationNam");
		this.setClientPlatform(Platform.ANDROID, 0);
		
		// peer = new MccNamPeer(pathConfig, key.toString(), key.toString(), port, this);
		peer = new MccNamPeer(null, key.toString(), key.toString(), port, this);
		peer.addMobilityItemAvailabilityListener(this);
		System.out.println("Contact address: " + peer.getPeerDescriptor());
	}
	
	public void performTest() {
		String peerToBeContactedAddress = "0011101000100101101011110011000001010011001010001011100111100011001111101000111101100001000110100111000111111101101001000010110010111110110110111000111001000110@192.168.1.146:8300";
		peer.requestService(peerToBeContactedAddress, "TestService", Platform.ANDROID, Action.COPY, "1.0");
	}
	
	public void addIMobilityItemIsAvailableObserver(IMobilityItemIsAvailableObserver iMobilityItemIsAvailableObserver) {
		this.listeners.add(iMobilityItemIsAvailableObserver);
	}
	
	public static S2PMigrationTestNam getInstance(Context mContext) {
		if (s2PMigrationTestNam == null)
			s2PMigrationTestNam = new S2PMigrationTestNam(mContext, null);
		return s2PMigrationTestNam;
	}
	
	public MccNamPeer getPeer() {
		return peer;
	}

	@Override
	public void onItemIsAvailable(String fileFullPath, String mainClassName, MigrationSubject role, Action action, Object state) {
		for(IMobilityItemIsAvailableObserver listener : listeners) {
			listener.onItemIsAvailable(fileFullPath, mainClassName, role, action, state);
		}
	}

}
