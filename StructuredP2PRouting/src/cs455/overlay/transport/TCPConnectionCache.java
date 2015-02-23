package cs455.overlay.transport;

import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Hashtable;

public class TCPConnectionCache {
	
public Hashtable<String,TCPConnection> connectionCache= new Hashtable<String,TCPConnection>();
	
public static String createID(Socket socket) throws UnknownHostException
{
	//System.out.println(socket.getInetAddress().getLocalHost().getHostName()+":"+socket.getPort());
	return socket.getInetAddress().getLocalHost().getHostName()+":"+socket.getPort();
	
}



}
