package it.unipr.ce.dsg.nam4j.impl.messages;

import com.google.gson.Gson;

/**
 * <p>
 * This class represents a message to request the file which describes the item
 * to be migrated.
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
 * @author Alessandro Grazioli (grazioli@ce.unipr.it)
 * 
 */

public class InfoFileIsAvailableMessage {

	public static final String MSG_KEY = "INFO_FILE_IS_AVAILABLE";
	private String type;
	private String conversationKey;
	
	public InfoFileIsAvailableMessage(String conversationKey) {
		setType(MSG_KEY);
		setConversationKey(conversationKey);
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getConversationKey() {
		return conversationKey;
	}

	public void setConversationKey(String conversationKey) {
		this.conversationKey = conversationKey;
	}
	
	/**
	 * Method to obtain a JSON representation of this {@link CopyItemMessage}.
	 * 
	 * @return a JSON representation of this {@link CopyItemMessage}
	 */
	public String getJSONString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
			
}
