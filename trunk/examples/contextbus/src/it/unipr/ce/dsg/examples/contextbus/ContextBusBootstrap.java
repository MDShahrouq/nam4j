package it.unipr.ce.dsg.examples.contextbus;

import it.unipr.ce.dsg.nam4j.impl.context.Utils;
import it.unipr.ce.dsg.nam4j.impl.messages.JoinRequestMessage;
import it.unipr.ce.dsg.nam4j.impl.messages.LeaveRequestMessage;
import it.unipr.ce.dsg.nam4j.impl.messages.PeerListMessage;
import it.unipr.ce.dsg.nam4j.impl.messages.PeerListRequestMessage;
import it.unipr.ce.dsg.nam4j.impl.peer.NamPeer;
import it.unipr.ce.dsg.s2p.peer.PeerDescriptor;
import it.unipr.ce.dsg.s2p.sip.Address;
import it.unipr.ce.dsg.s2p.util.FileHandler;

import java.util.HashSet;
import java.util.Set;

import org.zoolu.tools.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ContextBusBootstrap extends NamPeer {
	
	private Log log;
	private FileHandler fileHandler;
	
	private String networkStructure;

	private PeerDescriptor currentPeerDescriptor;
	
	public ContextBusBootstrap(String pathConfig, String key, String networkStructure){
		super(pathConfig, key);
		this.networkStructure = networkStructure;
		init();		
	}
	
	/** Method to perform the initial setup of the peer. */
	private void init() {
		
		currentPeerDescriptor = this.peerDescriptor;
		
		if(nodeConfig.log_path != null) {

			fileHandler = new FileHandler();
			
			if(!fileHandler.isDirectoryExists(nodeConfig.log_path))
				fileHandler.createDirectory(nodeConfig.log_path);

			log = new Log(nodeConfig.log_path + "info_" + peerDescriptor.getAddress()+".log", Log.LEVEL_MEDIUM);
		}
	}
	
	/**
	 * Method to manage received JSON messages represented as {@link JsonObject}
	 * objects.
	 * 
	 * @param peerMsg
	 *            The received message
	 * 
	 * @param sender
	 *            The {@link Address} of the sender
	 */
	protected void onReceivedJSONMsg(JsonObject peerMsg, Address sender) {
		
		String messageType = peerMsg.get("type").getAsString();
		
		Gson gson = new Gson();
		PeerDescriptor neighborPeerDesc = gson.fromJson(peerMsg.get("peer").toString(), PeerDescriptor.class);
		
		System.out.println("Received message of type " + messageType + " from peer " + neighborPeerDesc.getContactAddress());
		
		// Logging
		if (nodeConfig.log_path != null) {

			int lengthMsg = messageType.length();

			JsonObject info = new JsonObject();
			info.addProperty("timestamp", System.currentTimeMillis());
			info.addProperty("type", "recv");
			info.addProperty("typeMessage", messageType);
			info.addProperty("byte", lengthMsg);
			info.addProperty("sender", sender.getURL());

			printJSONLog(info, log, false);
		}
		
		if (messageType.equals(JoinRequestMessage.MSG_KEY)) {
			
			// A node asked to join the network
			
			if ((!(this.getPeerList().contains(neighborPeerDesc))) && (!neighborPeerDesc.getName().equals("bootstrap"))) {

				System.out.println("--- Adding a new peer to the peers list: " + neighborPeerDesc.getContactAddress());

				this.getPeerList().add(neighborPeerDesc);
				
				if(networkStructure.equalsIgnoreCase(Utils.FULL_MESH)) {
					
					// Send the whole updated peer list to all peers
					
					for(PeerDescriptor pd : this.getPeerList()) {
						
						Set<PeerDescriptor> peerDescriptors = new HashSet<PeerDescriptor>(this.getPeerList().getPeerDescriptors());
						peerDescriptors.remove(pd);
						peerDescriptors.add(currentPeerDescriptor);
					
						PeerListMessage newPLMsg = new PeerListMessage(peerDescriptors);
						sendMessage(new Address(pd.getAddress()), new Address(pd.getContactAddress()), this.getAddress(), newPLMsg.getJSONString(), Utils.JSON_MESSAGE_FORMAT);
					}
				} else if(networkStructure.equalsIgnoreCase(Utils.RANDOM_GRAPH)) {
					
					// Send a list of peers to the new peer - Utils.PEER_LIST_SIZE random nodes are chosen and sent
					
					Set<PeerDescriptor> peerDescriptors = this.getPeerList().getRandomPeerDescriptors(Utils.PEER_LIST_ANSWER_SIZE, neighborPeerDesc);

					PeerListMessage newPLMsg = new PeerListMessage(peerDescriptors);
					sendMessage(new Address(neighborPeerDesc.getAddress()), new Address(neighborPeerDesc.getContactAddress()), this.getAddress(), newPLMsg.getJSONString(), Utils.JSON_MESSAGE_FORMAT);
				}
				
				printPeerList();
				
				int numPeer = this.getPeerList().size();
				
				if(numPeer >= 0) {
					if(nodeConfig.list_path!=null){
						if(!fileHandler.isDirectoryExists(nodeConfig.list_path)) {
							fileHandler.createDirectory(nodeConfig.list_path);
						}
						peerList.writeList(fileHandler.openFileToWrite(nodeConfig.list_path + peerDescriptor.getAddress() + ".json"));
					}
				}
			}
			
		} else if(messageType.equalsIgnoreCase(PeerListRequestMessage.MSG_KEY)) {
			
			// A node requested a list of peers
			
			// Send a list of peers to the new peer - Utils.PEER_LIST_SIZE random nodes are chosen and sent
			Set<PeerDescriptor> peerDescriptors = this.getPeerList().getRandomPeerDescriptors(Utils.PEER_LIST_ANSWER_SIZE, neighborPeerDesc);

			PeerListMessage newPLMsg = new PeerListMessage(peerDescriptors);
			sendMessage(new Address(neighborPeerDesc.getAddress()), new Address(neighborPeerDesc.getContactAddress()), this.getAddress(), newPLMsg.getJSONString(), Utils.JSON_MESSAGE_FORMAT);
			
		} else if(messageType.equalsIgnoreCase(LeaveRequestMessage.MSG_KEY)) {
			
			if(this.getPeerList().contains(neighborPeerDesc)) {
				
				System.out.println("--- Peer " + neighborPeerDesc.getContactAddress() + " left the network");
				
				this.getPeerList().remove(neighborPeerDesc);
			}
		}
	}

	/**
	 * Method invoked when a JSON message could not get delivered to the
	 * addressee.
	 * 
	 * @param peerMsg
	 *            The message that could not get delivered
	 * 
	 * @param receiver
	 *            The {@link Address} of the addressee peer
	 * 
	 * @param contentType
	 *            The format of the message (i.e. "application/json")
	 */
	protected void onDeliveryMsgFailure(String sentMessage, Address receiver,	String contentType) {
		System.out.println("Could not deliver message to " + receiver);
		
		for(PeerDescriptor pd : this.getPeerList()) {
			if(pd.getContactAddress().equalsIgnoreCase(receiver.getURL())) {
				
				System.out.println("Removing the peer from the peer list.");
				
				this.getPeerList().remove(pd);
				printPeerList();
				
				break;
			}
		}
	}

	/**
	 * Method invoked when a JSON message got successfully delivered to the
	 * addressee.
	 * 
	 * @param peerMsg
	 *            The message that got delivered
	 * 
	 * @param receiver
	 *            The {@link Address} of the addressee peer
	 * 
	 * @param contentType
	 *            The format of the message (i.e. "application/json")
	 */
	protected void onDeliveryMsgSuccess(String sentMessage, Address receiver,	String contentType) {}
	
	/**
	 * Method to print the list of known peers.
	 * 
	 * @param list
	 *            The list of peers to be printed
	 */
	public void printPeerList() {
		
		if(!this.getPeerList().isEmpty()) {

			System.out.println("********** Known peers **********");

			int i = 1;

			for(PeerDescriptor pd : this.getPeerList()) {
				System.out.println(i++ + ") " +  pd.getContactAddress());
			}

			System.out.println("*********************************");

		} else {
			System.out.println("No known peer");
		}
	}

	/*
	 * args[0]: path to the config file
	 * 
	 * args[1]: the name of the node (bootstrap)
	 * 
	 * args[2]: a string indicating the network structure
	 */
	public static void main(String[] args) {
		
		if(args.length == 3) {
			new ContextBusBootstrap(args[0], args[1], args[2]);			
		}
		else {
			System.out
					.println("Specify three parameters:\n- the path to the config file\n- the name of the node\n- a string describing the network structure (e.g. full_mesh or random_graph)");
		}
	}

}
