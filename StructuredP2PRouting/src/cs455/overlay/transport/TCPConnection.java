package cs455.overlay.transport;

import java.io.IOException;
import java.net.Socket;

import cs455.overlay.node.Node;

public class TCPConnection {
    
	String connectionName;
	Node connectedNode;
	Socket connectedSocket;
	TCPReceiverThread receiver;
	TCPSender sender;
	public TCPConnection(Node node, Socket socket) throws IOException{
	    
		 this.connectionName=TCPConnectionCache.createID(socket);
		 this.connectedNode=node;
		 this.connectedSocket=socket;
		 this.receiver = new TCPReceiverThread(node,socket);
		 this.receiver.start();
		 this.sender = new TCPSender(socket);
		// connectedNode.setSender(sender);
		 connectedNode.storeConnection(this);
		}
	public TCPReceiverThread getReceiver() {
		return receiver;
	}
	public void setReceiver(TCPReceiverThread receiver) {
		this.receiver = receiver;
	}
	public synchronized TCPSender getSender() {
		return sender;
	}
	public void setSender(TCPSender sender) {
		this.sender = sender;
	}
	public String getConnectionName() {
		return connectionName;
	}
	public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
	}
	public Node getConnectedNode() {
		return connectedNode;
	}
	public void setConnectedNode(Node connectedNode) {
		this.connectedNode = connectedNode;
	}
	public Socket getConnectedSocket() {
		return connectedSocket;
	}
	public void setConnectedSocket(Socket connectedSocket) {
		this.connectedSocket = connectedSocket;
	}
	
}
