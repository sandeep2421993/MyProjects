package cs455.overlay.node;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Scanner;

import cs455.overlay.Util.InteractiveCommandParser;
import cs455.overlay.Util.Util;
import cs455.overlay.routing.RoutingEntry;
import cs455.overlay.transport.TCPConnection;
import cs455.overlay.transport.TCPConnectionCache;
import cs455.overlay.transport.TCPSender;
import cs455.overlay.transport.TCPServerThread;
import cs455.overlay.wireformats.Event;
import cs455.overlay.wireformats.NodeReportsOverlaySetupStatus;
import cs455.overlay.wireformats.OverlayNodeReportsTaskFinished;
import cs455.overlay.wireformats.OverlayNodeReportsTrafficSummary;
import cs455.overlay.wireformats.OverlayNodeSendsDeregistration;
import cs455.overlay.wireformats.OverlayNodeSendsRegistration;
import cs455.overlay.wireformats.Protocol;
import cs455.overlay.wireformats.RegistryReportsDeregistrationStatus;
import cs455.overlay.wireformats.RegistryReportsRegistrationStatus;
import cs455.overlay.wireformats.RegistryRequestsTaskInitiate;
import cs455.overlay.wireformats.RegistryRequestsTrafficSummary;
import cs455.overlay.wireformats.RegistrySendsNodeManifest;

public class Registry implements Node {

private static int nodefinishcount=0;
private int registryPort;
DataInputStream din;
TCPSender sender;
TCPServerThread registryServer;
private int listeningPort;
//storing nodes
private ArrayList<Integer> nodeIds= new ArrayList<Integer>();
private ArrayList<NodeRecord> nodeRecord = new ArrayList<NodeRecord>();
private Hashtable<Integer,NodeRecord> IDtoRecord ;
//storing connections in hashtable
public Hashtable<String,TCPConnection> connections;
public Hashtable<Integer,Socket> IDtoSocket ;
public Hashtable<Integer,TCPConnection> IDtoConnection;
public int overlaySuccess;
//hashtable for routing purpose
private Hashtable<Integer,ArrayList<NodeRecord>> IDtoTable = new Hashtable<Integer,ArrayList<NodeRecord>>();
private Hashtable<Integer,RoutingEntry> IDtoRoutingEntry = new  Hashtable<Integer,RoutingEntry>();

//trackers
private ArrayList<Integer> senttrackers;
private ArrayList<Integer> relaytrackers;
private ArrayList<Integer> receivetrackers;
private ArrayList<Long> sentSum;
private ArrayList<Long> receivedSum;
private ArrayList<Integer> nodeIDlist;
//Constructors 

	public Registry(int port)
	{
	this.registryPort=port;
	connections= new Hashtable<String,TCPConnection>();
	IDtoRecord = new Hashtable<Integer,NodeRecord>();
	IDtoSocket = new Hashtable<Integer,Socket>();
	IDtoConnection = new Hashtable<Integer,TCPConnection>();
	
	senttrackers=new ArrayList<Integer>();
	relaytrackers=new ArrayList<Integer>();
	receivetrackers=new ArrayList<Integer>();
	nodeIDlist=new ArrayList<Integer>();
	sentSum= new ArrayList<Long>();
	receivedSum=new ArrayList<Long>();
	startRegistry();
	}
	
	//using method to start the registry
	private void startRegistry() {
		
		try {
		   System.out.println("Registry started at port :"+ registryPort);
		   registryServer = new TCPServerThread(this,registryPort);
		   registryServer.start();
		   //System.out.println("Registry started on another thread");
			
		}
	             
			
		 catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	//MAIN
	public static void main(String args[]) throws UnknownHostException
	{
		System.out.println(InetAddress.getLocalHost().getHostAddress());
		int port=Integer.parseInt(args[0]);
		Registry r= new Registry(port);
		//Registry r= new Registry(6060);
		InteractiveCommandParser parser= new InteractiveCommandParser(r);
		parser.start();
		
	}
	
	//ON EVENT METHOD IN THE REGISTRY
@Override
public void onEvent(Event e, Socket receiverSocket) throws IOException {
	
int type=e.getType();
switch(type)
{
case Protocol.OVERLAY_NODE_SENDS_REGISTRATION : registerMessagingNode((OverlayNodeSendsRegistration)e,receiverSocket);
break;
case Protocol.OVERLAY_NODE_SENDS_DEREGISTRATION :deregisterMessagingNode((OverlayNodeSendsDeregistration)e,receiverSocket);
break;
case Protocol.NODE_REPORTS_OVERLAY_SETUP_STATUS: overlayStatus((NodeReportsOverlaySetupStatus)e,receiverSocket);
break;
case Protocol.OVERLAY_NODE_REPORTS_TRAFFIC_SUMMARY :documentTraffic((OverlayNodeReportsTrafficSummary)e,receiverSocket);
break;
case Protocol.OVERLAY_NODE_REPORTS_TASK_FINISHED :taskComplete((OverlayNodeReportsTaskFinished)e,receiverSocket);
}
}

//registering messaging node
private void registerMessagingNode(OverlayNodeSendsRegistration e, Socket receiverSocket)  {
//	System.out.println("Reached registerMessaginNode");
//	System.out.println(TCPConnectionCache.createID(receiverSocket));
	Boolean exists= false;
	int identifier=Util.createNodeID(nodeIds);
	int numOfNodes=(nodeIds.size())+1;
	String successMessage="Registration request successful. The number of messaging nodes constituting the overlay is ("+numOfNodes+")";
	String failureMessage=null;
	//String host=receiverSocket.getInetAddress().getLocalHost().getHostName();
	String host = receiverSocket.getInetAddress().getHostName();
//	System.out.println(receiverSocket.getInetAddress().getHostName());
	NodeRecord record= new NodeRecord(e.getIPaddress(),e.getListeningPort(),host,identifier);
	
	
	if(!(e.getIPaddress().equals(receiverSocket.getInetAddress().getHostAddress())))
	{
		
		failureMessage="Registration failed as the request was sent from IP address :"+receiverSocket.getInetAddress().getHostAddress()+" different from what was stated :"+e.getIPaddress();
        exists=true;
	}
	else{
	if(nodeRecord.isEmpty())
	{
	exists=false;
	//nodeRecord.add(record);
	}
	else
	{
	//NodeRecord record= new NodeRecord(e.getIPaddress(),e.getListeningPort());
	for(NodeRecord i:nodeRecord)
	{
		if(i.equals(record))
		{
			exists=true;
			failureMessage="The node with IP Address "+e.getIPaddress()+" and port number "+e.getListeningPort()+"exists";
			break;
		}
	}
	}
	}
	if(exists)
	{
		RegistryReportsRegistrationStatus r= new RegistryReportsRegistrationStatus(-1,failureMessage);
		try{
		byte[] data=r.getBytes();
		connections.get(TCPConnectionCache.createID(receiverSocket)).getSender().sendData(data);
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	else
	{
		//System.out.println("reached else block");
		nodeRecord.add(record);
		RegistryReportsRegistrationStatus r= new RegistryReportsRegistrationStatus(identifier,successMessage);
		try{
		byte[] data=r.getBytes();
		connections.get(TCPConnectionCache.createID(receiverSocket)).getSender().sendData(data);
		}//System.out.println(identifier);
	    catch(IOException ioe){
	    	ioe.printStackTrace();
	    }
		this.nodeIds.add(identifier);
	    Collections.sort(this.nodeIds);
	    IDtoRecord.put(identifier,record);
	    IDtoSocket.put(identifier, receiverSocket);
	  //  System.out.println("No. of Connections:"+ this.connections.size());
	    if(nodeIds.size()>8)
	    System.out.println("The number of nodes is currently greater than 8. You can setup overlay now");
	}
	}

//deregistering messaging nodes
private void deregisterMessagingNode(OverlayNodeSendsDeregistration e,Socket receiverSocket) throws IOException {
    
	//nodeIds.add(24);
	RegistryReportsDeregistrationStatus r;
	//System.out.println("Reached deregistermessaging node");
	//System.out.println(nodeIds.indexOf(24));
	if(("/"+e.getIPaddress()).equals(receiverSocket.getInetAddress().toString()))
	{	
	if(nodeIds.contains(e.getIdentifier()))
	{
	  r	= new RegistryReportsDeregistrationStatus(e.getIdentifier(),"Deregistered!");
		nodeIds.remove(nodeIds.indexOf(e.getIdentifier()));
    }
	else
	{
	r= new RegistryReportsDeregistrationStatus(-1,"Node has not been previously registered");
	}
	}
	else
	{
		System.out.println("/"+e.getIPaddress());
		System.out.println(receiverSocket.getInetAddress().toString());
	//.getInetAddress().getLocalHost().getHostAddress());
		r= new RegistryReportsDeregistrationStatus(-1,"IP address detected and sent do not match!");	
	}
	byte[] data=r.getBytes();
	connections.get(TCPConnectionCache.createID(receiverSocket)).getSender().sendData(data);
    }

private void overlayStatus(NodeReportsOverlaySetupStatus e,
		Socket receiverSocket) {
//	System.out.println(e.getInfo());
	overlaySuccess++;
	
	if(overlaySuccess==nodeIds.size())
	{
		System.out.println("Registry now ready to initiate tasks");
	}
	
}

private void taskComplete(OverlayNodeReportsTaskFinished e,
		Socket receiverSocket) throws IOException {
//	System.out.println("The node: "+e.getID()+" has reported task finish");
	nodefinishcount++;
	if(nodefinishcount==nodeIds.size())
	{
	System.out.println("All nodes have reported finish. Task summaries coming up in 20 seconds!");
    try {
		Thread.sleep(20000);
	} catch (InterruptedException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	RegistryRequestsTrafficSummary r= new RegistryRequestsTrafficSummary();
    byte[] data=r.getBytes();
    
    for(int i:nodeIds)
    IDtoConnection.get(i).getSender().sendData(data);
	
	}

}

// for receiving traffic summaries from node
private synchronized void documentTraffic(OverlayNodeReportsTrafficSummary e,
		Socket receiverSocket) {
//	System.out.println("Here");
//	System.out.println(e.getNodeID());
	nodeIDlist.add(e.getNodeID());
	senttrackers.add(e.getSendTracker());
	relaytrackers.add(e.getRelayTracker());
	receivetrackers.add(e.getReceiveTracker());
	sentSum.add(e.getSendSummation());
	receivedSum.add(e.getReceiveSummation());
	
	if(nodeIDlist.size()==nodeIds.size())
	{
		printTraffic(nodeIDlist,senttrackers,relaytrackers,receivetrackers,sentSum,receivedSum);
	}
}

private void printTraffic(ArrayList<Integer> nodeIDlist2,
		ArrayList<Integer> senttrackers2, ArrayList<Integer> relaytrackers2,
		ArrayList<Integer> receivetrackers2, ArrayList<Long> sentSum2,
		ArrayList<Long> receivedSum2) {
	
	System.out.format("%7s%11s%11s%11s%16s%16s","Node","Sent","Received","Relayed","Sent Sum","Received Sum");
	for(int i=0;i<nodeIDlist2.size();i++)
	{
		System.out.println();
		System.out.format("%7d%11d%11d%11d%16d%16d",nodeIDlist2.get(i),senttrackers2.get(i),receivetrackers.get(i),relaytrackers2.get(i),sentSum2.get(i),receivedSum2.get(i));
	//	System.out.print(nodeIDlist2.get(i)+" "+senttrackers2.get(i)+" "+receivetrackers.get(i)+" "+relaytrackers2.get(i)+" "+sentSum2.get(i)+" "+receivedSum2.get(i));
	}
	
	System.out.println();
	int send=0,received=0,relayed=0;
	long sentsum=0,receivedsum=0;
	for(int i:senttrackers2)
		send+=i;
	for(int i:receivetrackers2)
		received+=i;
	for(int i:relaytrackers2)
		relayed+=i;
	for(long i:sentSum2)
		sentsum+=i;
    for(long i:receivedSum2)
    	receivedsum+=i;
	//System.out.print("Sum"+" "+send+" "+received+" "+relayed+" "+sentsum+" "+receivedsum);
    System.out.format("%7s%11d%11d%11d%16d%16d","Sum",send,received,relayed,sentsum,receivedsum);
}




	
//SENDING REQUEST TO INITIATE MESSAGE SENDING TASK
public  void startMessaging(String string) throws IOException {
	
	int numOfMessages=Integer.parseInt(string);
	
//System.out.println("Registry says :Start messaging with number:"+numOfMessages);
RegistryRequestsTaskInitiate r= new RegistryRequestsTaskInitiate(numOfMessages);
byte[] data=r.getBytes();
for(int i:nodeIds)
{
	createConnectionAndSendData(i,data);
	//Socket s=IDtoSocket.get(i);
	//connections.get(TCPConnectionCache.createID(s)).getSender().sendData(data);
}
	
	
	
}

public void listRoutingTables() {
	System.out.println("****ROUTING TABLES****");

	ArrayList<Integer> nodeids= this.getNodeIds();
	RoutingEntry entry;
	for(int i:nodeids)
	{
	System.out.println("Routing Table for node :"+i);
	entry= IDtoRoutingEntry.get(i);
	entry.display();
	}
	
}
//SETTING UP OVERLAY
public void setupOverlay(String string) throws IOException {
	
	this.overlaySuccess=0;
//System.out.println("Registry received setupOverlay request");
	int NR= Integer.parseInt(string);
	Collections.sort(this.nodeIds);
	ArrayList<Integer> nodeids= this.nodeIds;
	Hashtable<Integer,NodeRecord> nodeRecords = this.getIDtoRecord();
	ArrayList<NodeRecord> table;
	NodeRecord record;
	for(int i:nodeids)
	{ 
	//	System .out.println("NodeID in setupOverlay() :"+i);
		table = new ArrayList<NodeRecord>();
		int in;
		int size= nodeids.size();
		int index= nodeids.indexOf(i);
	//	System.out.println("NodeID index:"+index);
	   // NodeRecord record;
	    int temp;
	    for(int j=0;j<NR;j++)
	    {
	    temp=0;
	    temp=(int) Math.pow(2,j);
	    in=index+temp;
     
	    if(in>=size)
	    in=in-size;
	    
	 //   System.out.println("In value"+in);
		
	    record=nodeRecords.get(nodeids.get(in));
	    table.add(record);
	    }
	    IDtoTable.put(i,table);
	    RoutingEntry re= new RoutingEntry(table);
	    IDtoRoutingEntry.put(i, re);
	   // System.out.println("Routing Table of "+i);
	    RegistrySendsNodeManifest r= new RegistrySendsNodeManifest(re,nodeIds);
	    byte[] data=r.getBytes();
	 //   Socket s= IDtoSocket.get(i);
	    
	    
	    createConnectionAndSendData(i,data);

	    }
	
   	
	
}



@Override
public void storeConnection(TCPConnection connection) {
//	System.out.println("storing connection");
	try {
		connections.put(connection.getConnectionName(),  connection);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//System.out.println(connection.getConnectionName());
}


public void createConnectionAndSendData(int i,byte[] data) throws UnknownHostException, IOException
{
	String ip=this.IDtoRecord.get(i).getIPAddress();
	int port=this.IDtoRecord.get(i).getPortnumber();
	Socket s= new Socket(ip,port);
	TCPConnection con=new TCPConnection(this,s);
	IDtoConnection.put(i,con);
	con.getSender().sendData(data);
}



//GETTERS AND SETTERS
@Override
public void setListeningPort(int port) {
	
	this.listeningPort=port;
	
}


public void setSender(TCPSender sender)
{
	this.sender=sender;
}
public int getRegistryPort() {
	return registryPort;
}

public void setRegistryPort(int registryPort) {
	this.registryPort = registryPort;
}

//public static ArrayList<Integer> getNodeIds() {
//	return nodeIds;
//}

public void setNodeIds(ArrayList<Integer> nodeIds) {
	this.nodeIds = nodeIds;
}

public ArrayList<NodeRecord> getNodeRecord() {
	return nodeRecord;
}

public void setNodeRecord(ArrayList<NodeRecord> nodeRecord) {
	this.nodeRecord = nodeRecord;
}

public Hashtable<Integer, NodeRecord> getIDtoRecord() {
	return IDtoRecord;
}

public void setIDtoRecord(Hashtable<Integer, NodeRecord> iDtoRecord) {
	IDtoRecord = iDtoRecord;
}

public Hashtable<String, TCPConnection> getConnections() {
	return connections;
}

public void setConnections(Hashtable<String, TCPConnection> connections) {
	this.connections = connections;
}

@Override
public ArrayList<Integer> getNodeIds() {
	
	return nodeIds;
}

@Override
public String getListeningPort() {
	// TODO Auto-generated method stub
	return ""+registryPort;
}


}

