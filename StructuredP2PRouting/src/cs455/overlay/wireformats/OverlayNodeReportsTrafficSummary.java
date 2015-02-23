package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class OverlayNodeReportsTrafficSummary implements Event{
	
	private int type= Protocol.OVERLAY_NODE_REPORTS_TRAFFIC_SUMMARY;
	private int nodeID;
	private int sendTracker;
	private int receiveTracker;
	private int relayTracker;
	private long sendSummation;
	private long receiveSummation;
	
	public OverlayNodeReportsTrafficSummary(int identifier,int senttracker,int relaytracker,long sentsum,int receivetracker,long receivesum)
	{
	this.nodeID=identifier;
	this.sendTracker=senttracker;
	this.relayTracker=relaytracker;
	this.sendSummation=sentsum;
	this.receiveTracker=receivetracker;
	this.receiveSummation=receivesum;
	}

	public OverlayNodeReportsTrafficSummary(byte[] marshalledBytes) throws IOException
	{
		ByteArrayInputStream baInputStream =new ByteArrayInputStream(marshalledBytes);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));
		this.type = din.readByte();
		this.nodeID=din.readInt();
		this.sendTracker=din.readInt();
		this.relayTracker=din.readInt();
		this.sendSummation=din.readLong();
		this.receiveTracker=din.readInt();
		this.receiveSummation=din.readLong();
		
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
		
		//including node id
		dout.writeInt(nodeID);
		//sent packets
		dout.writeInt(sendTracker);
		//relayed packets
		dout.writeInt(relayTracker);
		//sum of payloads sent
		dout.writeLong(sendSummation);
		//received packets
		dout.writeInt(receiveTracker);
		//sum of payloads received
		dout.writeLong(receiveSummation);
		dout.flush();

		marshalledBytes= baOutputStream.toByteArray();
		return marshalledBytes;
	}

	public int getNodeID() {
		return nodeID;
	}

	public void setNodeID(int nodeID) {
		this.nodeID = nodeID;
	}

	public int getSendTracker() {
		return sendTracker;
	}

	public void setSendTracker(int sendTracker) {
		this.sendTracker = sendTracker;
	}

	public int getReceiveTracker() {
		return receiveTracker;
	}

	public void setReceiveTracker(int receiveTracker) {
		this.receiveTracker = receiveTracker;
	}

	public int getRelayTracker() {
		return relayTracker;
	}

	public void setRelayTracker(int relayTracker) {
		this.relayTracker = relayTracker;
	}

	public long getSendSummation() {
		return sendSummation;
	}

	public void setSendSummation(long sendSummation) {
		this.sendSummation = sendSummation;
	}

	public long getReceiveSummation() {
		return receiveSummation;
	}

	public void setReceiveSummation(long receiveSummation) {
		this.receiveSummation = receiveSummation;
	}

}
