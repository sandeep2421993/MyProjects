package cs455.overlay.transport;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import cs455.overlay.node.Node;

public class TCPServerThread extends Thread{
    
	TCPConnection connection;
	TCPReceiverThread receiver;
	TCPSender sender;
	private Node listeningNode;
	private int listeningPort;
	public TCPServerThread(Node node, int port)
	{
	this.listeningNode=node;
	this.listeningPort=port;
	}
	
	public void run()
	{
		try {
			ServerSocket serverSocket= new ServerSocket(listeningPort);
			this.listeningNode.setListeningPort(serverSocket.getLocalPort());
		//	System.out.println("Listening at "+serverSocket.getLocalPort());
			while(true)
			{
				Socket socket = serverSocket.accept();
				
				//using TCPReceiver for all inbound communication
				new TCPConnection(listeningNode,socket);
				

			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
}
