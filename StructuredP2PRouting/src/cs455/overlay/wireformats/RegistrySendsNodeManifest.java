package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import cs455.overlay.routing.RoutingEntry;

public class RegistrySendsNodeManifest implements Event {
    
	int routingTableSize;
	int[] nodeID;
	String[] nodeIP;
	int[] nodePort;
	private int type= Protocol.REGISTRY_SENDS_NODE_MANIFEST;
	ArrayList<Integer> nids= new ArrayList<Integer>();
	public RegistrySendsNodeManifest(RoutingEntry re,ArrayList<Integer> nodeids)
	{
		this.nids=nodeids;
	
		this.nodeID=re.getNodeID();
		this.nodeIP=re.getNodeIP();
		this.nodePort=re.getNodePort();
	    this.routingTableSize=re.getSize();
	
		
	}
	
	
	public RegistrySendsNodeManifest(byte[] marshalledBytes) throws IOException
	{
		
		ByteArrayInputStream baInputStream =new ByteArrayInputStream(marshalledBytes);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));
		this.type = din.readByte();
        this.routingTableSize= din.readByte();
        
        this.nodeID=new int[this.routingTableSize];
        this.nodeIP=new String[this.routingTableSize];
        this.nodePort= new int[this.routingTableSize];
  
     
     for(int i=0;i<routingTableSize;i++)
     {
    	 this.nodeID[i]=din.readInt();
    	 int length=din.readByte();
    	 byte[] ip=new byte[length];
    	 din.readFully(ip, 0, length);
    	 this.nodeIP[i]=new String(ip);
    	 this.nodePort[i]=din.readInt();
    	 
     }
         
        int numOfIDs=din.readByte();
        for(int j=0;j<numOfIDs;j++)
        {
        	int id=din.readInt();
        	this.nids.add(id);
        }
        baInputStream.close();
        din.close();
    
	}
	@Override
	public int getType() {
		return type;
	}

	@Override
	public byte[] getBytes() throws IOException {
		
	
		byte[] marshalledBytes= null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout =	new DataOutputStream(new BufferedOutputStream(baOutputStream));
		
		//including the messagetype as a byte
		dout.writeByte(type);
		dout.writeByte(routingTableSize);
		//System.out.println("Routing Table Size "+routingTableSize);
		for(int i=0;i<routingTableSize;i++)
		{
		dout.writeInt(nodeID[i]);
		//System.out.println(nodeID[i]);
		byte[] ip=nodeIP[i].getBytes();
		int length=ip.length;
		dout.writeByte(length);
	
		
		dout.write(ip);
	
		dout.writeInt(nodePort[i]);
	
		}
		
		dout.writeByte(this.nids.size());
	
		for(int j:this.nids)
		 dout.writeInt(j);
			
		  
		dout.flush();
		
		marshalledBytes= baOutputStream.toByteArray();
		return marshalledBytes;
		
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


	public int getRoutingTableSize() {
		return routingTableSize;
	}


	public void setRoutingTableSize(int routingTableSize) {
		this.routingTableSize = routingTableSize;
	}


	public ArrayList<Integer> getNids() {
		return nids;
	}


	public void setNids(ArrayList<Integer> nids) {
		this.nids = nids;
	}

}
