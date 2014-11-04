package it.unipr.ce.dsg.nam4j.impl.messages;

import it.unipr.ce.dsg.s2p.peer.PeerDescriptor;

import com.google.gson.Gson;

/**
 * <p>
 * This class represents a message sent as an answer to a {@link PingMessage}.
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
 * @author Giacomo Brambilla (giacomo.brambilla@studenti.unipr.it)
 * @author Alessandro Grazioli (grazioli@ce.unipr.it)
 * 
 */

public class PongMessage {
	
	public static final String MSG_KEY = "PONG";
	
	private String type;
	private PeerDescriptor peer;

	public PongMessage(PeerDescriptor peer) {
		setType(MSG_KEY);
		setPeer(peer);
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public PeerDescriptor getPeer() {
		return peer;
	}

	public void setPeer(PeerDescriptor peer) {
		this.peer = peer;
	}
	
	/**
	 * Method to obtain a JSON representation of this {@link PongMessage}.
	 * 
	 * @return a JSON representation of this {@link PongMessage}
	 */
	public String getJSONString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

}
