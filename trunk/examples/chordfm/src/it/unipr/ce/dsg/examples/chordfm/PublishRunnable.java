package it.unipr.ce.dsg.examples.chordfm;

import java.util.Random;

import it.unipr.ce.dsg.s2pchord.ChordPeer;
import it.unipr.dsg.s2pchord.resource.Resource;
import it.unipr.dsg.s2pchord.resource.ResourceDescriptor;
import it.unipr.dsg.s2pchord.resource.ResourceParameter;

public class PublishRunnable implements Runnable {

	private String item = null;
	private ChordPeer cp = null;
	
	public PublishRunnable(String item, ChordPeer cp) {
		this.item = item;
		this.cp = cp;
	}
	
	public void run() {
		
		System.out.println("Service: Publish " + item);
		
		// TODO we should extract resource info from item, but now for simplicity we use a trick
		
		String location = null;
		Random ran = new Random(System.currentTimeMillis());
		int i = ran.nextInt(4);
		if (i == 0)
			location = "kitchen";
		else if (i == 1)
			location = "livingroom";
		else if (i == 2)
			location = "bathroom";
		else if (i == 3)
			location = "bedroom";
		
		ResourceDescriptor rd = new ResourceDescriptor();
		rd.setType("TemperatureNotification"); // type of resource
		rd.setResourceOwner(cp.getMyNetPeerInfo());
		rd.addParameter(new ResourceParameter("Subject", "Temperature")); 
		rd.addParameter(new ResourceParameter("Action", ""));
		rd.addParameter(new ResourceParameter("Object", ""));
		rd.addParameter(new ResourceParameter("Location", location));
		rd.generateResourceKey();	
		String resourceKey = rd.getKey();
		System.out.println("Generated Resource String: " + resourceKey);
		System.out.println("Generated Resource Descriptor: " +  rd.resourceDescriptorString());
		Resource res = new Resource(rd, null, System.currentTimeMillis());
		
		cp.publishResource(res);
		
	}

}
