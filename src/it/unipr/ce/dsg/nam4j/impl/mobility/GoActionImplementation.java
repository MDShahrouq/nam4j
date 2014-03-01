package it.unipr.ce.dsg.nam4j.impl.mobility;

import it.unipr.ce.dsg.nam4j.impl.BundleDescriptor;
import it.unipr.ce.dsg.nam4j.impl.NetworkedAutonomicMachine;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.net.Socket;

public class GoActionImplementation extends GoActionHandler {
	
	NetworkedAutonomicMachine nam = null;
	BufferedReader is;
	OutputStream os;
	
	/**
	 * The descriptor of the object to be migrated.
	 */
	BundleDescriptor bundleDescriptor;
	
	public GoActionImplementation(NetworkedAutonomicMachine nam, BufferedReader is, OutputStream os) {
		this.nam = nam;
		this.is = is;
		this.os = os;
		
		System.out.println("SERVER: starting GO action...");
	}
	
	private void fmMobility(String line, Socket cs) {
		
	}
	
	private void serviceMobility(String line, Socket cs) {
		
	}
	
	public void run() {
		
	}

}
