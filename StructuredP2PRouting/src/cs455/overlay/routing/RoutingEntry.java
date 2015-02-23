package cs455.overlay.routing;

import java.util.ArrayList;

import cs455.overlay.node.NodeRecord;

public class RoutingEntry {
	
	int ownerNodeID;
	int nodeID[];
	
	
	String nodeIP[];
	
	
	int nodePort[];
	
	int size;
	ArrayList<NodeRecord> nodeRecord = new ArrayList<NodeRecord>();
	
	//creating list of ids,ips and ports from the NodeRecords
	public RoutingEntry(ArrayList<NodeRecord> record)
	{
	//	System.out.println("Routing Entry");
		nodeRecord=record;
	    this.size=record.size();
	    nodeID= new int[this.size];
	    nodeIP= new String[this.size];
	    nodePort= new int[this.size];
	 //   System.out.println("ROuting Entry Size : "+this.size);
		unpack(nodeRecord);
	
	}
	private void unpack(ArrayList<NodeRecord> record) {
		
	//	System.out.println("Unpacking routing entry");
			for(int i=0;i<record.size();i++)
			{
				//System.out.println(record.get(i).getNodeId());
				int id= record.get(i).getNodeId();
	//			System.out.println(id);
				nodeID[i]=id;
				nodeIP[i]=record.get(i).getIPAddress();
			    nodePort[i]=record.get(i).getPortnumber();	
	//		    System.out.println("nodeID:"+nodeID[i]);
			}
		
	}
	public int[] getNodeID() {
		return nodeID;
	}
	public void setNodeID(int[] nodeID) {
		this.nodeID = nodeID;
	}
	public String[] getNodeIP() {
		return nodeIP;
	}
	public void setNodeIP(String[] nodeIP) {
		this.nodeIP = nodeIP;
	}
	public int[] getNodePort() {
		return nodePort;
	}
	public void setNodePort(int[] nodePort) {
		this.nodePort = nodePort;
	}
	
	public int getSize()
	{
		return this.size;
	}
	
	public void display()
	{
		System.out.println();
		
		for(int i=0;i<this.size;i++)
		{
//		  System.out.println();
          System.out.print(nodeIP[i]);
          System.out.print(" "+nodePort[i]);
          System.out.print(" "+nodeID[i]);
          System.out.println();
		}
		System.out.println();System.out.println();System.out.println();

	}

}
