package it.unipr.ce.dsg.s2p.example.peer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.sql.Timestamp;

import it.unipr.ce.dsg.s2p.example.msg.AckMessage;
import it.unipr.ce.dsg.s2p.org.json.JSONObject;
import it.unipr.ce.dsg.s2p.peer.Peer;
import it.unipr.ce.dsg.s2p.sip.Address;

public class EchoServer extends Peer{

	public EchoServer(String pathConfig) {
		super(pathConfig, "a5ds465a465a35d4s64d6a");

	}
	
	public void ackToPeer(String toAddress, String contactAddress){

		//JoinMessage peerMsg = new JoinMessage(peerDescriptor);
		AckMessage ack_msg = new AckMessage("1", "Test Ack Message","Prova di messaggio");
		
		send(new Address(toAddress), new Address(contactAddress), ack_msg);

	}
	
	public static void main(String[] args) throws SocketException {
		
		System.out.println("EchoServer starting ...");
		
		EchoServer es = new EchoServer("config/"+args[0]);
		System.out.println("Peer Descriptor: " + es.peerDescriptor);
	    //  open up standard input
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 
	    //Message Exchange cycle
	    String destIp = "";
	    int destPort =0;

	    while(true)
	    {
	    	try {
	    		System.out.println("Select your message type:\n");
		    	System.out.println("1 --> ACK\n");
		    	System.out.println("2 --> EVENT\n");
		    	String input = br.readLine();
		    	int value = Integer.parseInt(input);
		    	
		    	System.out.println("Enter Destination Ip ["+destIp+"] --->" );
		    	input = br.readLine(); 
		    	if(input.length() > 0)
		    		destIp = input;
		    	System.out.println("Destination Ip:" + destIp);
		           
		        System.out.println("Enter Destination Port ["+destPort+"] --->" );
		        input = br.readLine(); 
		    	if(input.length() > 0)
		    		destPort = Integer.parseInt(input);
		    	System.out.println("Destination Port:" + destPort);
		    	String address = "";
		    	
				switch (value) {
				case 1:
					System.out.println("Sending ACK to ---> " + destIp+":"+destPort);
					es.ackToPeer(destIp+":"+destPort ,destIp+":"+destPort);
					break;
				case 2:
					System.out.println("Sending EVENT to ---> " + destIp+":"+destPort);
					es.ackToPeer(destIp+":"+destPort,destIp+":"+destPort);
					break;

				default:
					break;
				}
		    	
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	
	}
	
	@Override
	protected void onReceivedJSONMsg(JSONObject jsonMsg, Address sender) {
		// TODO Auto-generated method stub
		super.onReceivedJSONMsg(jsonMsg, sender);
		System.out.println("***********************************************");
		System.out.println("\n"+new Timestamp(System.currentTimeMillis())+"-->RECEIVED MSG:\n"+jsonMsg);
		System.out.println("***********************************************");
	}
	
	@Override
	protected void onDeliveryMsgFailure(String peerMsgSended, Address receiver,
			String contentType) {
		System.out.println("onDeliveryMsgFailure: " + peerMsgSended);
		
	}

	@Override
	protected void onDeliveryMsgSuccess(String peerMsg, Address receiver, String contentType) {
		System.out.println("onDeliveryMsgSuccess: " + peerMsg);
	}
	
	
}
