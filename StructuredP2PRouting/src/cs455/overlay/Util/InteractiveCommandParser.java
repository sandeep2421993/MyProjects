package cs455.overlay.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

import cs455.overlay.node.MessagingNode;
import cs455.overlay.node.Node;
import cs455.overlay.node.NodeRecord;
import cs455.overlay.node.Registry;
import cs455.overlay.transport.TCPConnection;

public class InteractiveCommandParser extends Thread {

	MessagingNode messagingNode;
	Registry registry;
	
	public InteractiveCommandParser(Registry node)
	{
	this.registry=node;	
	}
	
	public InteractiveCommandParser(MessagingNode node)
	{
		this.messagingNode=node;
	}
	public void run()
	{
		
		Scanner in = new Scanner(System.in);
		String line= in.nextLine();
		while(!(line.equals("exit")))
		{
		try {
			int success=commandParser(line);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	  
	    line=in.nextLine();
		
		}
	}
	
	private int commandParser(String line) throws IOException {
		
	
		String[] command= line.trim().split("\\s");
		String choice= command[0].toLowerCase();
		
		switch(choice)
		{
		case "list-messaging-nodes" : 
			                         listMessagingNodes();
		                             break;
		case "setup-overlay":   
			                   if(command.length==2)
		                       setupOverlay(command[1]);
			                   else
			                   setupOverlay(""+3);
		                        break;
		case "list-routing-tables" :listRoutingTables();
		                              break;
		case "start" : startMessaging(command[1]);
		               break;
		case "print-counters-and-diagnostics" : printCounters();
		                                        break;
		case "exit-overlay" : exitOverlay();
		                      break;
		
		default : System.out.println("Wrong command. Enter ");
		}
		return 1;
	}



	private void exitOverlay() throws IOException {
		messagingNode.sendDeregistration();
	}

	private void printCounters() {
		
		messagingNode.printCounters();
		
	}

	private void startMessaging(String string) throws IOException {
		
		registry.startMessaging(string);
		
	}

	private void listRoutingTables() {

		registry.listRoutingTables();
	}

	private void setupOverlay(String string) throws IOException {
		
		registry.setupOverlay(string);
	}

	private void listMessagingNodes() {
		ArrayList<Integer> nodes= registry.getNodeIds();
	
		Hashtable<Integer,NodeRecord> nodeRecords = registry.getIDtoRecord();
		System.out.format("%28s%8s%5s","HOSTNAME","PORT","ID");
		for(int i:nodes)
		{
		System.out.println();
		NodeRecord r= nodeRecords.get(i);
		
		
		System.out.format("%28s%8d%5d",r.getHostname(),r.getPortnumber(),r.getNodeId());
		
	
		}
		
	}
	

	
}
