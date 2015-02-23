package cs455.overlay.node;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;

import cs455.overlay.Util.InteractiveCommandParser;
import cs455.overlay.Util.Util;
import cs455.overlay.routing.RoutingTable;
import cs455.overlay.transport.TCPConnection;
import cs455.overlay.transport.TCPConnectionCache;
import cs455.overlay.transport.TCPReceiverThread;
import cs455.overlay.transport.TCPSender;
import cs455.overlay.transport.TCPServerThread;
import cs455.overlay.wireformats.*;

public class MessagingNode implements Node {

	//class variables
	static Socket registrySocket;
	TCPSender sender;
	private String ip;
	int messagingPort;
	private String registryName;
	int registryPort;
	int messagingNodeServerPort;
	String hostname;
	TCPReceiverThread receiver;
	TCPServerThread messagingNodeServer;
	TCPConnection registryConnection;
	int listeningPort;
	private int nodeID;
	private ArrayList<Integer> routingIDs;
	RoutingTable t;
	int[] traversedNodes;
	int numOfHops=0;
	
	//hashtable to store the connections
	public Hashtable<String,TCPConnection> connections= new Hashtable<String,TCPConnection>();
	
	//hashtable to store connections with the other nodes in routing table;
	
	private Hashtable<Integer,TCPConnection> IDtoConnections ;
	
	//storing nodes
	private ArrayList<NodeRecord> nodeRecord = new ArrayList<NodeRecord>();
	
	//trackers
	private int sendTracker;
	private int receiveTracker;
	private int relayTracker;
	private long sendSummation;
	private long receiveSummation;
	
	private int payload;
	
	//Constructors
	public MessagingNode(String servername, int port)
	{
		IDtoConnections=new Hashtable<Integer,TCPConnection>(); 
		this.registryPort=port;
		this.registryName=servername;
		try {
			this.ip= InetAddress.getLocalHost().getHostAddress();
			this.hostname=InetAddress.getLocalHost().getHostName();
			
		} catch (UnknownHostException e1) {
			
			e1.printStackTrace();
		}
		
		this.sendTracker=0;
		this.receiveTracker=0;
	    this.sendSummation=0;
	    this.receiveSummation=0;
	    this.relayTracker=0;
	}
	
	  //MAIN
		public static void main(String args[])
		{
			int port= Integer.parseInt(args[1]);
			MessagingNode m1= new MessagingNode(args[0],port);
			m1.registerNode();
			InteractiveCommandParser parser = new InteractiveCommandParser(m1);
			parser.start();
			
		}
		
		//Registration request being sent to registry
		private void registerNode() {
			
			try{
				//	System.out.println("Connecting to server "+servername+" on port "+port);
					Socket socket= new Socket(registryName,registryPort);
					
					this.registrySocket=socket;
					this.messagingPort=socket.getLocalPort();
				//	System.out.println("Just connected to "+ socket.getRemoteSocketAddress());
			    messagingNodeServer= new TCPServerThread(this,0);
			    messagingNodeServer.start();
			    registryConnection= new TCPConnection(this,socket);
			    
				 // receiver= new TCPReceiverThread(this,socket);
				 //  receiver.start();
				//using TCPSender for outbound communication
				//	sender= new TCPSender(socket);
			   if(listeningPort!=0)
			   {
	     		OverlayNodeSendsRegistration register= new OverlayNodeSendsRegistration(ip,listeningPort,registryName);
			    byte[] data= register.getBytes();
//			    System.out.println("Sending data");
	    		registryConnection.getSender().sendData(data);
			    
			   }
	
					
		}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		
		}
		
		//Sending request for deregistration
		public void sendDeregistration() throws IOException
		{
			Socket socket= new Socket(registryName,registryPort);
			TCPConnection c=new TCPConnection(this,socket);
			//System.out.println("Sending deregistration");
			OverlayNodeSendsDeregistration dereg= new OverlayNodeSendsDeregistration(ip,listeningPort,nodeID);
		    byte[] data= dereg.getBytes();
		   c.getSender().sendData(data);
		    // connections.get(TCPConnectionCache.createID(registrySocket)).getSender().sendData(data);
		    
		}
	
		//ON EVENT METHOD IN MESSAGING NODE
	@Override
	public void onEvent(Event e, Socket socket) throws IOException {
		
		int type=e.getType();
		switch(type)
		{
		case Protocol.REGISTRY_REPORTS_REGISTRATION_STATUS : registrationStatus((RegistryReportsRegistrationStatus)e,socket);
		 break;
		case Protocol.REGISTRY_REPORTS_DEREGISTRATION_STATUS :deregistrationStatus((RegistryReportsDeregistrationStatus)e,socket);
		 break;
		case Protocol.REGISTRY_SENDS_NODE_MANIFEST : nodeManifest((RegistrySendsNodeManifest)e,socket);
		 break;
		case Protocol.REGISTRY_REQUESTS_TASK_INITIATE : initiateTask((RegistryRequestsTaskInitiate)e, socket);
		 break;
		case Protocol.REGISTRY_REQUESTS_TRAFFIC_SUMMARY : trafficSummary();
		 break;
		case Protocol.OVERLAY_NODE_SENDS_DATA:receiveData((OverlayNodeSendsData)e ,socket);
		}
	}
	
private void deregistrationStatus(RegistryReportsDeregistrationStatus e, Socket socket) {
		
		System.out.println(e.getInfo());
		
	}
	private void registrationStatus(RegistryReportsRegistrationStatus e, Socket socket) {
		if(e.getSuccessStatus()!=-1)
			this.nodeID=e.getSuccessStatus();
			else
			{
				System.out.println(e.getInfo());
			
			System.exit(0);}		
		System.out.println(e.getInfo());
	}
	
	
	//ROUTING TABLES BEING POPULATED AFTER NODE MANIFEST HAS BEEN SENT BY REGISTRY
	
	private void nodeManifest(RegistrySendsNodeManifest e, Socket socket) {
		boolean success=true;
		//System.out.println("Creating routing table");
		createRoutingTable(e);
		success=createRoutingConnections(this.t);
		
		if(success)
		{
			NodeReportsOverlaySetupStatus n= new NodeReportsOverlaySetupStatus(this.nodeID,"Successfully created connections");
		   try{
			byte[] data= n.getBytes();
		    connections.get(TCPConnectionCache.createID(socket)).getSender().sendData(data);
		   }
		   catch(IOException ioe)
		   {
			   ioe.printStackTrace();
		   }
		   }
		else
		{
			NodeReportsOverlaySetupStatus n= new NodeReportsOverlaySetupStatus(-1,"Overlay setup failed!");
			   try{
				byte[] data= n.getBytes();
			    connections.get(TCPConnectionCache.createID(socket)).getSender().sendData(data);
			   }
			   catch(IOException ioe)
			   {
				   ioe.printStackTrace();
			   }
			
		}
		
	}
	
	//creating connections with nodes in routing table
	private boolean createRoutingConnections(RoutingTable t2) {
		
	boolean success=false;
	int num=t2.getNoOfEntries();
	for(int index=0;index<num;index++)
	{
	int id=t2.getNodeId(index);
	//System.out.println("Neighbour ID:"+id);
	int port= t2.getNodePort(index);
	String ip=t2.getNodeIp(index);
	try {
		Socket socket= new Socket(ip,port);
		TCPConnection con=new TCPConnection(this,socket);
		this.IDtoConnections.put(id, con);
		success=true;
	} catch (IOException e) {
		e.printStackTrace();
	   success=false;
	}
	
	
	}
	 return success;
	}

	//Populating the routing table on messaging node
	private void createRoutingTable(RegistrySendsNodeManifest e) {
		
		this.routingIDs=e.getNids();
		int[] nodeids=e.getNodeID();
		String[] nodeips=e.getNodeIP();
		int[] nodeports=e.getNodePort();
		this.t= new RoutingTable(nodeids,nodeips,nodeports);
		
	}

	
	//INITIATING THE TASK AND SENDING THE REQUIRED NUMBER OF MESSAGES
	private void initiateTask(RegistryRequestsTaskInitiate e, Socket socket) throws IOException {
		//Number of messages to send
		int number=e.getNumDataPackets();
			
		   for(int i=0;i<number;i++)			
		    sendMessage(e,socket);
			
			sendTaskCompletion();
			
			}
			
		
		private void sendMessage(RegistryRequestsTaskInitiate e, Socket socket) throws IOException{
			
			int target;
			Random r= new Random();
			int index=r.nextInt(routingIDs.size());
		    	
		
			//System.out.println("Request for initiating tasks:"+index);
			
			target=routingIDs.get(index);
			
			while(target==this.nodeID)
			{
				index=r.nextInt(routingIDs.size());
				target=routingIDs.get(index);
			}
	//		System.out.println("Request for Initiating Task : Target NodeID:"+target);
			int contains=t.contains(target);
			payload=Util.createPayload();
			numOfHops=0;
			int[] traversedNodes=new int[numOfHops];
			
			
			//IF THE TARGET IS PRESENT IN THE ROUTING TABLE
			if(contains==1)
			{
		//		System.out.println("The id "+target+" is present in the routing table" );
				OverlayNodeSendsData o= new OverlayNodeSendsData(target,this.nodeID,payload,numOfHops,traversedNodes);
				byte[] data= o.getBytes();
				this.IDtoConnections.get(target).getSender().sendData(data);
				incrementSender(payload);
				
			}
			else
			{
		//		System.out.println("The id "+target+" is not present in the routing table" );
					int flag=-1;
				    int hopID=-1;
					for(int i=0;i<t.getNoOfEntries();i++)
					{
						if(target>t.getNodeId(i))
						{
							flag=1;
							hopID=t.getNodeId(i);					
						}	
					}
					
					
					if((flag==1)&&(t.getMaxID()!=t.lastID())&&(t.lastID()<target))
					{
				if((t.isNotLower(target))||(target>t.getMaxID()))
				hopID=t.getMaxID();
					}
					
					
					if(flag!=1)
					{
			
					    if(target<t.lastID())
					    hopID=t.getMaxID();
			
					}
				OverlayNodeSendsData o= new OverlayNodeSendsData(target,this.nodeID,payload,numOfHops,traversedNodes);
				byte[] data= o.getBytes();
		//		System.out.println("Hop ID to send payload to:"+hopID);
				this.IDtoConnections.get(hopID).getSender().sendData(data);
				incrementSender(payload);
				}
			
		}
		

	//Sending task completion
	private void sendTaskCompletion() throws IOException {
		
			OverlayNodeReportsTaskFinished o= new OverlayNodeReportsTaskFinished(this.ip,this.listeningPort,this.nodeID);
			byte[] data= o.getBytes();
			registryConnection.getSender().sendData(data);
		
	}
	
	

	//Receiving message from overlay node
	private synchronized void receiveData(OverlayNodeSendsData e, Socket socket) throws IOException {
		
		
		if(e.getDestinationID()==this.nodeID)
		{
		acceptMessage(e,socket);
		}
		else
		{
	    relayMessage(e,socket);
	    }
		}
	
	//IF THIS NODE IS DESTINATION
	public void acceptMessage(OverlayNodeSendsData e, Socket socket)
	{
	//	System.out.println("DESTINATION REACHED");
		//receiveTracker++;
		incrementReceived(e);
		
	}
	
	//IF node needs to relay
	private synchronized void relayMessage(OverlayNodeSendsData e, Socket socket) throws IOException {
		
		int contains=t.contains(e.getDestinationID());
	
		
		
		int hops=e.getNumOfHops();
		hops++;
		int[] traversednodes=new int[hops];
		
		int n=0;
		
		if(e.getNumOfHops()!=0)
		for(n=0;n<e.getNumOfHops();n++)
		{
		traversednodes[n]=e.getTraversedNodeID(n);	
		}
		
		traversednodes[n]=this.nodeID;
		
		incrementRelay();
		
		//IF THE TARGET IS PRESENT IN THE ROUTING TABLE
		if(contains==1)
		{
		//	System.out.println("RelayMessageContains");
		//	System.out.println("The id "+e.getDestinationID()+" is present in the routing table" );
			OverlayNodeSendsData o= new OverlayNodeSendsData(e.getDestinationID(),e.getSourceID(),e.getPayload(),hops,traversednodes);
			byte[] data= o.getBytes();
			IDtoConnections.get(e.getDestinationID()).getSender().sendData(data);
		}
		//IF THE TARGET IS NOT PRESENT IN THE ROUTING TABLE
		else
		{
	//		System.out.println("RelayMessage does not contain");
			//numOfHops++;
			//int[] traversedNodes=new int[numOfHops];
			
	//		System.out.println("current node:"+this.nodeID);
	//		System.out.println("The id "+e.getDestinationID()+" is not present in the routing table" );
			
			int flag=-1;
			int hopID=-1;
			
				for(int i=0;i<t.getNoOfEntries();i++)
				{
					if(e.getDestinationID()>t.getNodeId(i))
					{
					
						flag=1;
						hopID=t.getNodeId(i);
					}
				}	
				if((flag==1)&&(t.getMaxID()!=t.lastID())&&(t.lastID()<e.getDestinationID()))
						{
					if((t.isNotLower(e.getDestinationID()))||(e.getDestinationID()>t.getMaxID()))
					hopID=t.getMaxID();
						}
				if(flag!=1)
				{
				        	
					      if(e.getDestinationID()<t.lastID())
						    hopID=t.getMaxID();
				}
				
				OverlayNodeSendsData o= new OverlayNodeSendsData(e.getDestinationID(),e.getSourceID(),e.getPayload(),hops,traversednodes);
				byte[] data= o.getBytes();
		//		System.out.println("Hop ID forwarding to:"+hopID);
				IDtoConnections.get(hopID).getSender().sendData(data);	
				
			}
			
			}

		
	
	//INCREMENTING TRACKERS	
	private synchronized void incrementSender(int payload2) {
		
		sendTracker++;
		sendSummation+=payload2;
			
		}
	
	private synchronized void incrementRelay() {
		
		relayTracker++;
		
	}

	public synchronized void incrementReceived(OverlayNodeSendsData e)
	{
		receiveTracker++;
		receiveSummation+=e.getPayload();
	}

	

	//Reporting traffic summary
	private void trafficSummary() throws IOException {
		
	//	System.out.println("Got traffic summary request");
		OverlayNodeReportsTrafficSummary o= new OverlayNodeReportsTrafficSummary(this.nodeID,sendTracker,relayTracker,sendSummation,receiveTracker,receiveSummation);
		byte[] data= o.getBytes();
		registryConnection.getSender().sendData(data);
		resetCounters();
		
	}
	
	private void resetCounters() {
		
		this.sendTracker=0;
		this.receiveTracker=0;
	    this.sendSummation=0;
	    this.receiveSummation=0;
	    this.relayTracker=0;
		
	}

	//Used for storing connections on the node
	@Override
	public void storeConnection(TCPConnection connection) {
		
	//System.out.println("storing connection in MN");
		try {
			connections.put(connection.getConnectionName(),  connection);
		} catch (Exception e) {
			e.printStackTrace();
		}
	//	System.out.println(connection.getConnectionName());
	//for(String name:connections.keySet())
	//	System.out.println(connections.get(name).getConnectionName());
	}

	
	public void printCounters() {
		System.out.println("Packets Sent:"+ sendTracker);
		System.out.println("Packets Relayed:"+ relayTracker);
		System.out.println("Packets Received:"+ receiveTracker);
		System.out.println("Packets Data Sent:"+ sendSummation);
		System.out.println("Packets Data Received:"+ receiveSummation);
	}
	
	//GETTERS AND SETTERS
	@Override
	public void setSender(TCPSender sender) {
		this.sender=sender;
		
	}

	

	@Override
	public  void setListeningPort(int port) {
		
		this.listeningPort=port;
		
	}
	
	

	@Override
	public ArrayList<Integer> getNodeIds() {
		return null;
	}

	public Socket getRegistrySocket() {
		return registrySocket;
	}

	public void setRegistrySocket(Socket registrySocket) {
		this.registrySocket = registrySocket;
	}

	@Override
	public String getListeningPort() {
		return ""+listeningPort;
	}

	public Hashtable<Integer, TCPConnection> getIDtoConnections() {
		return IDtoConnections;
	}

	public void setIDtoConnections(Hashtable<Integer, TCPConnection> iDtoConnections) {
		IDtoConnections = iDtoConnections;
	}

	
	
	
	//Was used for testing routing table on messaging node side. This is not reachable now
	public void getRoutingTable() {
       System.out.println("ROUTING TABLE FOR NODE :"+this.nodeID);
		RoutingTable r=this.t;
		int[] nodeids=r.getNodeIds();
		String[] nodeips=r.getNodeIps();
		int[] ports=r.getNodePorts();
        for(int i=0;i<r.getNoOfEntries();i++)
		{
        	System.out.println();
        	System.out.print(nodeids[i]+" "+nodeips[i]+" "+ports[i]);
		}
		
		
	}

	

	
}
