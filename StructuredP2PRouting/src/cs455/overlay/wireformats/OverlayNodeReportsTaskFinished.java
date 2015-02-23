package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class OverlayNodeReportsTaskFinished implements Event {
	
	private int type= Protocol.OVERLAY_NODE_REPORTS_TASK_FINISHED;
	private int nodeID;
	private String IPAddress;
	private int messagingPort;
	public OverlayNodeReportsTaskFinished(String ipaddress,int port,int identifier)
	{
		this.IPAddress=ipaddress;
		this.messagingPort=port;
		this.nodeID=identifier;
		
	}

	public OverlayNodeReportsTaskFinished(byte[] marshalledBytes) throws IOException
	{
		ByteArrayInputStream baInputStream =new ByteArrayInputStream(marshalledBytes);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));
				this.type = din.readByte();
				int length= din.readByte();
				byte[] ip= new byte[length];
				din.readFully(ip,0,length);
				this.messagingPort=din.readInt();
				this.nodeID=din.readInt();
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
		byte[] ip= IPAddress.getBytes();
		int length=ip.length;
		dout.writeByte(length);
		dout.write(ip);
		dout.writeInt(messagingPort);
		dout.writeInt(nodeID);
        dout.flush();
		
		marshalledBytes= baOutputStream.toByteArray();
		return marshalledBytes;
	}

	public int getID() {
		return nodeID;
	}

	public void setID(int identifier) {
		this.nodeID = identifier;
	}

	public String getIPAddress() {
		return IPAddress;
	}

	public void setIPAddress(String iPAddress) {
		IPAddress = iPAddress;
	}

	public int getMessagingPort() {
		return messagingPort;
	}

	public void setMessagingPort(int messagingPort) {
		this.messagingPort = messagingPort;
	}

	

	
}
