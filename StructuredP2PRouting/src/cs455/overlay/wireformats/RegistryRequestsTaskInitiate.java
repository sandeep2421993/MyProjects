package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RegistryRequestsTaskInitiate implements Event{
	
	private int type= Protocol.REGISTRY_REQUESTS_TASK_INITIATE;
	private int numDataPackets;
	
	public RegistryRequestsTaskInitiate(int numOfDataPackets)
	{
		this.numDataPackets=numOfDataPackets;
	}
    
	public RegistryRequestsTaskInitiate(byte[] marshalledBytes) throws IOException
	{
		ByteArrayInputStream baInputStream =new ByteArrayInputStream(marshalledBytes);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));
		this.type = din.readByte();
		this.numDataPackets=din.readInt();
		
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
		
		//including number of data packets
		dout.writeInt(numDataPackets);
		
		dout.flush();

		marshalledBytes= baOutputStream.toByteArray();
		return marshalledBytes;
		
	}

	public int getNumDataPackets() {
		return numDataPackets;
	}

	public void setNumDataPackets(int numDataPackets) {
		this.numDataPackets = numDataPackets;
	}

}
