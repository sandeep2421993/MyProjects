package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class OverlayNodeSendsData implements Event{
	
	private int type= Protocol.OVERLAY_NODE_SENDS_DATA;
	private int DestinationID;
	private int SourceID;
	private int payload;
	private int numOfHops; // length of the traversed node ids array below
	private int[] traversedNodeIDs;
	
	public OverlayNodeSendsData(int destination,int source, int payload, int hops, int[] traversednodes)
	{
	this.DestinationID=destination;
	this.SourceID=source;
	this.payload=payload;
	this.numOfHops=hops;
	this.traversedNodeIDs=traversednodes;
	
		
	}
	
	public OverlayNodeSendsData(byte[] marshalledBytes) throws IOException
	{
		ByteArrayInputStream baInputStream =new ByteArrayInputStream(marshalledBytes);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));
		this.type = din.readByte();
		this.DestinationID=din.readInt();
		this.SourceID=din.readInt();
		this.payload=din.readInt();
		this.numOfHops=din.readInt();
		if(numOfHops!=0)
		{
		this.traversedNodeIDs=new int[numOfHops];
		for(int i=0;i<numOfHops;i++)
		this.traversedNodeIDs[i]=din.readInt();
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
		dout.writeInt(DestinationID);
		dout.writeInt(SourceID);
		dout.writeInt(payload);
		dout.writeInt(numOfHops);
		
		if(numOfHops!=0)
		{
		for(int i=0;i<numOfHops;i++)
		dout.writeInt(traversedNodeIDs[i]);
		}

		
		
		dout.flush();

		marshalledBytes= baOutputStream.toByteArray();
		return marshalledBytes;
	}

	public int getDestinationID() {
		return DestinationID;
	}

	public void setDestinationID(int destinationID) {
		DestinationID = destinationID;
	}

	public int getSourceID() {
		return SourceID;
	}

	public void setSourceID(int sourceID) {
		SourceID = sourceID;
	}

	public int getPayload() {
		return payload;
	}

	public void setPayload(int payload) {
		this.payload = payload;
	}

	public int getNumOfHops() {
		return numOfHops;
	}

	public void setNumOfHops(int numOfHops) {
		this.numOfHops = numOfHops;
	}

	public int[] getTraversedNodeIDs() {
		return traversedNodeIDs;
	}

	public int getTraversedNodeID(int index)
	{
		return traversedNodeIDs[index];
	}
	public void setTraversedNodeIDs(int[] traversedNodeIDs) {
		this.traversedNodeIDs = traversedNodeIDs;
	}
	
}
