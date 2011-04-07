package it.unipr.ce.dsg.s2p.example.msg;

import it.unipr.ce.dsg.s2p.message.BasicMessage;
import it.unipr.ce.dsg.s2p.message.Payload;

public class AckMessage extends BasicMessage{
	public static final String ACK_MSG = "ack";
	
	public AckMessage(String status, String msg, String detail){
		super();
		   Payload payload = new Payload();
		   payload.addParam("status", status);
		   payload.addParam("msg", msg);
		   payload.addParam("detail", detail);
		   this.setType(ACK_MSG);
		   this.setPayload(payload);
	}
}