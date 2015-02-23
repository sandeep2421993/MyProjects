package cs455.overlay.transport;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import cs455.overlay.node.Node;
import cs455.overlay.wireformats.Event;
import cs455.overlay.wireformats.EventFactory;

public class TCPReceiverThread extends Thread{

	private Socket receiverSocket;
	private DataInputStream din;
	private Node actionNode;

	public TCPReceiverThread(Node node,Socket socket) throws IOException
	{
		this.receiverSocket=socket;
		this.din= new DataInputStream(socket.getInputStream());
		this.actionNode=node;
		
	}
	
	public void run()
	{
		int length;
		while(receiverSocket!=null)
		try {
			//System.out.println("Reached:"+actionNode.getListeningPort());
			 length=din.readInt();
			
			byte[] data= new byte[length];
			din.readFully(data, 0, length);
			
			Event e= EventFactory.getEvent(data);
			
			if(e!=null){
		//	System.out.println("Received event");
			actionNode.onEvent(e,receiverSocket);
			}
			else
			System.out.println("null");
	
       
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
}
