package cs455.overlay.node;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import cs455.overlay.transport.TCPConnection;
import cs455.overlay.transport.TCPSender;
import cs455.overlay.wireformats.Event;

public interface Node {
	
public void onEvent(Event e, Socket receiverSocket) throws IOException;
public void setSender(TCPSender sender);
public void storeConnection(TCPConnection connection);
public void setListeningPort(int port);
public ArrayList<Integer> getNodeIds();
public String getListeningPort();
	
}
