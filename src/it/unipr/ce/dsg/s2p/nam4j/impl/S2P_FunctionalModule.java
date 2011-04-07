package it.unipr.ce.dsg.s2p.nam4j.impl;

import it.unipr.ce.dsg.nam4j.impl.FunctionalModule;
import it.unipr.ce.dsg.s2p.message.BasicMessage;
import it.unipr.ce.dsg.s2p.org.json.JSONObject;
import it.unipr.ce.dsg.s2p.sip.Address;

public class S2P_FunctionalModule extends FunctionalModule{

	NAMCommManager namCommManager;
	
	public void start(String config,String key){
		System.out.println("Start FM sip2peer with key:"+key);
		namCommManager = new NAMCommManager(config, key);
	}
	
	public void start(String config){
		System.out.println("Start FM sip2peer");
		//automatic definition for peer's keys
		namCommManager = new NAMCommManager(config, "a5ds465a465a45d4s64d6a");
	}
	
	public void sendMessageToPeer(String toAddress, BasicMessage msg){
		namCommManager.send(new Address(toAddress),new Address(toAddress),msg);
	}
	
	public void receiveMessageToPeer(JSONObject jsonMsg, Address sender){
		namCommManager.onReceivedJSONMsg(jsonMsg, sender);
	}
}
