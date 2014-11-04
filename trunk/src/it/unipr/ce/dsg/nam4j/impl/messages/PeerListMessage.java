package it.unipr.ce.dsg.nam4j.impl.messages;

import it.unipr.ce.dsg.s2p.peer.PeerDescriptor;

import java.util.Set;

import com.google.gson.Gson;

/**
 * <p>
 * This class represents a message including a list of peer descriptors.
 * </p>
 * 
 * <p>
 * Copyright (c) 2014, Distributed Systems Group, University of Parma, Italy.
 * Permission is granted to copy, distribute and/or modify this document under
 * the terms of the GNU Free Documentation License, Version 1.3 or any later
 * version published by the Free Software Foundation; with no Invariant
 * Sections, no Front-Cover Texts, and no Back-Cover Texts. A copy of the
 * license is included in the section entitled "GNU Free Documentation License".
 * </p>
 * 
 * @author Michele Amoretti (michele.amoretti@unipr.it)
 * @author Alessandro Grazioli (grazioli@ce.unipr.it)
 * 
 */

public class PeerListMessage {
	
	public static final String MSG_KEY = "PEER_LIST"; 
	
	private Set<PeerDescriptor> peers;
	private String type;
	
	public PeerListMessage(Set<PeerDescriptor> peers) {
		setType(MSG_KEY);
		setPeers(peers);
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<PeerDescriptor> getPeers() {
		return peers;
	}

	public void setPeers(Set<PeerDescriptor> peers) {
		this.peers = peers;
	}
	
	/**
	 * Method to obtain a JSON representation of this {@link PeerListMessage}.
	 * 
	 * @return a JSON representation of this {@link PeerListMessage}
	 */
	public String getJSONString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
