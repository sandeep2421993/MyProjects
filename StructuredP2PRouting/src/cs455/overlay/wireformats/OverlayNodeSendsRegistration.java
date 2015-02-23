package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class OverlayNodeSendsRegistration implements Event{

	
	private int type= Protocol.OVERLAY_NODE_SENDS_REGISTRATION;
	//byte messageType= (byte) type;
	private String hostname;
	private String IPaddress;
	int IPAddressLength;
	//private int registeringPort;
	private int listeningPort;
	
	
	public OverlayNodeSendsRegistration(String ipaddress, int portnum, String hostname)
	{
		
		IPaddress=ipaddress;
		
		IPAddressLength=ipaddress.length();
		
		//registeringPort=portnum;
		this.listeningPort=portnum;
		//System.out.println(registeringPort);
		this.hostname=hostname;
	}
	
	//populating fields
	public OverlayNodeSendsRegistration(byte[] marshalledBytes) throws IOException
	{
		ByteArrayInputStream baInputStream =new ByteArrayInputStream(marshalledBytes);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));
				this.type = din.readByte();
                int IPLength= din.readByte();
                byte[] IP= new byte[IPLength];
                din.readFully(IP, 0, IPLength);
                this.IPaddress=new String(IP);
                this.listeningPort=din.readInt();
                
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
		dout.writeInt(listeningPort);
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

	public void setType(int type) {
		this.type = type;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getListeningPort() {
		return listeningPort;
	}

	public void setListeningPort(int listeningPort) {
		this.listeningPort = listeningPort;
	}

	
}
