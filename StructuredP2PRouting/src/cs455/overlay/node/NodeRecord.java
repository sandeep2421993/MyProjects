package cs455.overlay.node;

// CLASS TO MAINTAIN DETAILS OF NODE
public class NodeRecord {
	
private String hostname;
private String IPAddress;
private int portnumber;

private int nodeId;

public NodeRecord(String ip,int port,String host,int id)
{
	this.hostname=host;
	this.IPAddress=ip;
	this.portnumber=port;
	this.nodeId=id;
	
}

public boolean equals(NodeRecord newnode)
{
	Boolean exists= false;
	
	if((this.IPAddress.equals(newnode.getIPAddress()))&&(this.portnumber==newnode.getPortnumber()))
		exists=true;
	
	return exists;
	
}

public String getIPAddress() {
	return IPAddress;
}

public void setIPAddress(String iPAddress) {
	IPAddress = iPAddress;
}

public int getPortnumber() {
	return portnumber;
}

public void setPortnumber(int portnumber) {
	this.portnumber = portnumber;
}


public String getHostname() {
	return hostname;
}

public void setHostname(String hostname) {
	this.hostname = hostname;
}

public int getNodeId() {
	return nodeId;
}

public void setNodeId(int nodeId) {
	this.nodeId = nodeId;
}
}
