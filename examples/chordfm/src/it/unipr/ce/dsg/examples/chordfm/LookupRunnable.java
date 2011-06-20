package it.unipr.ce.dsg.examples.chordfm;

import it.unipr.ce.dsg.s2pchord.ChordPeer;
import it.unipr.dsg.s2pchord.resource.ResourceDescriptor;
import it.unipr.dsg.s2pchord.resource.ResourceParameter;

public class LookupRunnable implements Runnable {

	private String item = null;
	private ChordPeer cp = null;
	
	public LookupRunnable(String item, ChordPeer cp) {
		this.item = item;
		this.cp = cp;
	}
	
	public void run() {
		
		System.out.println("Service: Lookup " + item);
		
		// TODO extract resource info from item
		
		ResourceDescriptor rd = new ResourceDescriptor();
		rd.setType("type"); // type of resource
		rd.setResourceOwner(cp.getMyNetPeerInfo());
		rd.addParameter(new ResourceParameter("parName", "parValue")); //name and value to be published
		rd.generateResourceKey();	
		String resourceKey = rd.getKey();
		System.out.println("Generated Resource String: " + resourceKey);
		System.out.println("Generated Resource Descriptor: " +  rd.resourceDescriptorString());
		cp.searchResource(resourceKey);
		
	}

}
