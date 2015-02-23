package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class OverlayNodeSendsDeregistration implements Event {
	
	private int type= Protocol.OVERLAY_NODE_SENDS_DEREGISTRATION;
	private String IPaddress;
	private int deregisteringPort;
	private int identifier;
	
	public OverlayNodeSendsDeregistration(String ipaddress,int port,int id)
	{
		this.IPaddress=ipaddress;
		this.deregisteringPort=port;
		this.identifier=id;
	
	}
	public OverlayNodeSendsDeregistration(byte[] marshalledBytes) throws IOException
	{
		ByteArrayInputStream baInputStream =new ByteArrayInputStream(marshalledBytes);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));
				this.type = din.readByte();
                int IPLength= din.readByte();
                byte[] IP= new byte[IPLength];
                din.readFully(IP, 0, IPLength);
                this.IPaddress=new String(IP);
                this.deregisteringPort=din.readInt();
                this.identifier=din.readInt();
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
		
		//inluding ip address length as a byte and the ip address as a byte[]
		byte[] IP=IPaddress.getBytes();
		int IPLength=IP.length;
		dout.writeByte(IPLength);
		dout.write(IP);
		
		//including port number as an int
		dout.writeInt(deregisteringPort);
		dout.writeInt(identifier);
		dout.flush();
		
		marshalledBytes= baOutputStream.toByteArray();
		return marshalledBytes;
	}
	public String getIPaddress() {
		return IPaddress;
	}
	public void setIPaddress(String iPaddress) {
		IPaddress = iPaddress;
	}
	public int getDeregisteringPort() {
		return deregisteringPort;
	}
	public void setDeregisteringPort(int deregisteringPort) {
		this.deregisteringPort = deregisteringPort;
	}
	public int getIdentifier() {
		return identifier;
	}
	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}

}
