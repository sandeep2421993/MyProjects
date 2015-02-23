package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RegistryReportsRegistrationStatus implements Event {
	
	private int type= Protocol.REGISTRY_REPORTS_REGISTRATION_STATUS;
	private int successStatus;
	private String info;
	
	public RegistryReportsRegistrationStatus(int success,String information)
	{
	 this.successStatus=success;
	 this.info=information;
	}
	
	public RegistryReportsRegistrationStatus(byte[] marshalledBytes) throws IOException
	{
		ByteArrayInputStream baInputStream =new ByteArrayInputStream(marshalledBytes);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));
		this.type = din.readByte();
        this.successStatus= din.readInt();
        int infolength=din.readByte(); 
        byte[] information= new byte[infolength];
        din.readFully(information, 0, infolength);
        this.info=new String(information);
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
		dout.writeInt(successStatus);
		//inluding information length as a byte and the info as a byte[]
		byte[] information=info.getBytes();
		int infoLength=information.length;
		dout.writeByte(infoLength);
		dout.write(information);
		
		dout.flush();
	//	System.out.println("Reached getbytes in registration status");
		marshalledBytes= baOutputStream.toByteArray();
		return marshalledBytes;
	}

	public int getSuccessStatus() {
		return successStatus;
	}

	public void setSuccessStatus(int successStatus) {
		this.successStatus = successStatus;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
