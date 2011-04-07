package it.unipr.ce.dsg.s2p.nam4j.impl;

import it.unipr.ce.dsg.nam4j.impl.NetworkedAutonomicMachine;
import it.unipr.ce.dsg.s2p.example.msg.AckMessage;

public class BootstrapNAM extends NetworkedAutonomicMachine {
	
	public S2P_FunctionalModule s2p_functionalModule;
	
	public BootstrapNAM(){
		s2p_functionalModule = new S2P_FunctionalModule();
		s2p_functionalModule.setId("sip2peer FM");
		this.addFunctionalModule(s2p_functionalModule);
	}
	
	public static void main(String[] args) {
		
		BootstrapNAM bootstrapNAM = new BootstrapNAM();

		Object functionalModule  = bootstrapNAM.getFunctionalModule("sip2peer FM");
		
		String config = "config/NAMpeer.cfg";
		String key = "a5ds465a465a45d4s64d6a";
		AckMessage ackMsg = new AckMessage("0","Ciao NAM!","test");
		String toAddress = "160.78.27.184:4060";

		if(functionalModule instanceof S2P_FunctionalModule){
			((S2P_FunctionalModule)functionalModule).start(config,key);
			((S2P_FunctionalModule)functionalModule).sendMessageToPeer(toAddress,ackMsg);
		}
		
	}

}
