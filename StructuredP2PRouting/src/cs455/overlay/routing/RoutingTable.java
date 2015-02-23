package cs455.overlay.routing;

public class RoutingTable {

	int[] nodeIds;
	String[] nodeIps;
	int[] nodePorts;
	
	int noOfEntries;
	public RoutingTable(int[] nodeids, String[] nodeips, int[] nodeports) {
		
		this.nodeIds=nodeids;
		this.nodeIps=nodeips;
		this.nodePorts=nodeports;
	//	if((nodeids.length==nodeips.length)&&(nodeips.length==nodeports.length))
			noOfEntries=nodeids.length;
	     
	}
	public int[] getNodeIds() {
		return nodeIds;
	}
	
	public boolean isNotLower(int id)
	{
		
		for(int i=0;i<nodeIds.length;i++)
		{
			if(nodeIds[i]<id)
				return false;
		}
		
		return true;
	}
	public String[] getNodeIps() {
		return nodeIps;
	}
	public void setNodeIps(String[] nodeIps) {
		this.nodeIps = nodeIps;
	}
	public int[] getNodePorts() {
		return nodePorts;
	}
	public void setNodePorts(int[] nodePorts) {
		this.nodePorts = nodePorts;
	}

	public synchronized int  getNodeId(int index)
	{
		return nodeIds[index];
	}
	
	public String getNodeIp(int index)
	{
		return nodeIps[index];
	}
	
	public int getNodePort(int index)
	{
		return nodePorts[index];
	}
	public int getNoOfEntries() {
		return noOfEntries;
	}
	public void setNoOfEntries(int noOfEntries) {
		this.noOfEntries = noOfEntries;
	}
	
	//returns 1 if id is present in routing table else -1
	public synchronized int contains(int id)
	{
		for(int i=0;i<noOfEntries;i++)
		{
			if(nodeIds[i]==id)
				return 1;
		}
		return -1;
	}
	
	//returns the last entry in the routing table
	public int lastID()
	{
		return nodeIds[noOfEntries-1];
	}
	
	
	//Returns the maximum ID number in the routing table
    public int getMaxID()
    {
    	int max=nodeIds[0];
    	for(int i=1;i<nodeIds.length;i++)
    	{
    		if(max<nodeIds[i])
    		  max=nodeIds[i];
    	}
    	return max;
    }
}

