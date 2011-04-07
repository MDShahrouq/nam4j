package it.unipr.ce.dsg.s2p.nam4j.impl;

import it.unipr.ce.dsg.s2p.org.json.JSONObject;
import it.unipr.ce.dsg.s2p.peer.Peer;
import it.unipr.ce.dsg.s2p.sip.Address;

public class NAMCommManager extends Peer{

	public NAMCommManager(String pathConfig, String key) {
		super(pathConfig, key);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onReceivedJSONMsg(JSONObject jsonMsg, Address sender) {
		// TODO Auto-generated method stub
		super.onReceivedJSONMsg(jsonMsg, sender);
		System.out.println("onReceivedJSONMsg: " + jsonMsg);
		System.out.println("Address: " + sender);
	}
	
	@Override
	protected void onDeliveryMsgFailure(String peerMsgSended, Address receiver,
			String contentType) {
		System.out.println("onDeliveryMsgFailure: " + peerMsgSended);
		
	}

	@Override
	protected void onDeliveryMsgSuccess(String peerMsgSended, Address receiver,
			String contentType) {
		System.out.println("onDeliveryMsgSuccess: " + peerMsgSended);
	}

}
