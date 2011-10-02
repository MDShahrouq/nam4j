package it.unipr.ce.dsg.examples.chordfm;

import java.math.BigInteger;
import java.util.Random;

import it.unipr.ce.dsg.examples.ontology.Lookup;
import it.unipr.ce.dsg.examples.ontology.Notify;
import it.unipr.ce.dsg.examples.ontology.Publish;
import it.unipr.ce.dsg.examples.ontology.Subscribe;
import it.unipr.ce.dsg.nam4j.impl.FunctionalModule;
import it.unipr.ce.dsg.nam4j.impl.NetworkedAutonomicMachine;
import it.unipr.ce.dsg.s2p.peer.PeerDescriptor;
import it.unipr.ce.dsg.s2pchord.ChordPeer;
import it.unipr.ce.dsg.s2pchord.eventlistener.ChordEventListener;
import it.unipr.ce.dsg.s2pchord.util.SHA1;
import it.unipr.dsg.s2pchord.resource.ResourceDescriptor;


public class ChordFunctionalModule extends FunctionalModule implements ChordEventListener {

	private ChordPeer chordPeer = null;
	
	public ChordFunctionalModule(NetworkedAutonomicMachine nam) {
		super(nam);
		this.setId("cfm");
		this.setName("ChordFunctionalModule");
		System.out.println("I am " + this.getId() + " and I own to " + nam.getId());
		
		// create Service objects and add to providedServices hashmap
		
		Lookup lookupService = new Lookup();
		lookupService.setId("s1");
		this.addProvidedService(lookupService.getId(), lookupService);
		
		Notify notifyService = new Notify();
		notifyService.setId("s2");
		this.addProvidedService(notifyService.getId(), notifyService);
		
		Publish publishService = new Publish();
		publishService.setId("s3");
		this.addProvidedService(publishService.getId(), publishService);
		
		Subscribe subscribeService = new Subscribe();
		subscribeService.setId("s4");
		this.addProvidedService(subscribeService.getId(), subscribeService);
		
		// create and start ChordPeer
		Random ran = new Random();
		int port = 1024 + ran.nextInt(9999-1024);
		int bitNumber = 160;
		try {
			int unL = ran.nextInt(bitNumber);
			String key = SHA1.convertToHex(SHA1.calculateSHA1(BigInteger.valueOf(unL + System.currentTimeMillis()).toString(16)));
			chordPeer = new ChordPeer("config/chordPeer.cfg", key, key, port, bitNumber, false);
			chordPeer.startPeer();
			chordPeer.setChordEventListener(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void join() {
		System.out.println("JOIN");
	}
	
	private void leave() {
		System.out.println("LEAVE");
	}
	
	private void lookup(String item) {
		Thread t = new Thread(new LookupRunnable(item, chordPeer), "Lookup thread");
		//System.out.println("Child thread: " + t);
		t.start();
	}
	
	private void publish(String item) {
		Thread t = new Thread(new PublishRunnable(item, chordPeer), "Publish thread");
		//System.out.println("Child thread: " + t);
		t.start();
	}
	
	private void subscribe(String fmId, String item) {
		System.out.println(fmId + " SUBSCRIBE to " + item);
	}
	
	public void execute(String requestorId, String requestedService, String parameters) {
		if (requestedService.equals("Join")) {
			this.join();
		}
		if (requestedService.equals("Leave")) {
			this.leave();
		}
		if (requestedService.equals("Lookup")) {
			this.lookup(parameters);
		}
		if (requestedService.equals("Publish")) {
			this.publish(parameters);
		}
		if (requestedService.equals("Subscribe")) {
			this.subscribe(requestorId, parameters);
		}
	}

	@Override
	public void searchResultEvent(String resourceKey, ResourceDescriptor rd, PeerDescriptor responsiblePeer, PeerDescriptor ownerPeer) {
		System.out.println("Received a Search Result Event Notification for resource: " + resourceKey + " Resource Descriptor Key: " + rd.getKey() +" with responsible: " + responsiblePeer.getKey() + " and owner: " + ownerPeer.getKey());
	}

	@Override
	public void publishResultEvent(ResourceDescriptor rd, PeerDescriptor responsiblePeer, PeerDescriptor ownerPeer) {
		System.out.println("Received a Publish Result Event Notification for resource: " + rd.getKey() + " with responsible: " + responsiblePeer.getKey() + " and owner: " + ownerPeer.getKey());	
	}

}
