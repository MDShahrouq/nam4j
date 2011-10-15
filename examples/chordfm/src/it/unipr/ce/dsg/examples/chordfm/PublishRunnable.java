package it.unipr.ce.dsg.examples.chordfm;

import com.google.gson.Gson;
import it.unipr.ce.dsg.nam4j.impl.context.ContextEvent;
import it.unipr.ce.dsg.s2pchord.ChordPeer;
import it.unipr.dsg.s2pchord.resource.Resource;
import it.unipr.dsg.s2pchord.resource.ResourceDescriptor;
import it.unipr.dsg.s2pchord.resource.ResourceParameter;

public class PublishRunnable implements Runnable {

	ChordFunctionalModule cfm = null;
	private String item = null;
	private ChordPeer cp = null;
	
	public PublishRunnable(ChordFunctionalModule cfm, String item, ChordPeer cp) {
		this.cfm = cfm;
		this.item = item;
		this.cp = cp;
	}
	
	public void run() {
		
		cfm.getLogger().log("Service: Publish " + item);
		
		Gson gson = new Gson();
		ContextEvent ce = gson.fromJson(item, ContextEvent.class);
		
		ResourceDescriptor rd = new ResourceDescriptor();
		rd.setAttachment(item);
		if (ce.getName() != null)
			rd.setType(ce.getName()); // type of resource
		rd.setResourceOwner(cp.getMyNetPeerInfo());
		if (ce.getSubject() != null)
			rd.addParameter(new ResourceParameter("Subject", ce.getSubject().getName()));
		if (ce.getAction() != null)
			rd.addParameter(new ResourceParameter("Action", ce.getAction().getName()));
		if (ce.getObject() != null)
			rd.addParameter(new ResourceParameter("Object", ce.getObject().getName()));
		if (ce.getLocation() != null)
			rd.addParameter(new ResourceParameter("Location", ce.getLocation().getValue()));
		/*
		if (ce.getTimestamp() != null)
			rd.addParameter(new ResourceParameter("Timestamp", ce.getTimestamp()));
		*/
		rd.generateResourceKey();	
		String resourceKey = rd.getKey();
		cfm.getLogger().log("Generated Resource String: " + resourceKey);
		cfm.getLogger().log("Generated Resource Descriptor: " +  rd.resourceDescriptorString());
		Resource res = new Resource(rd, null, System.currentTimeMillis());
		
		cp.publishResource(res);
	}

}