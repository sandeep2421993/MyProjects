package cs455.overlay.Util;

import java.util.ArrayList;
import java.util.Random;

public class Util {
	
	public static int createNodeID(ArrayList<Integer> nodes)
	{
		int nodeId;
		Random id= new Random();
	    nodeId=id.nextInt(128);
	  
	    if(nodes.isEmpty())
	    return nodeId;
	    
	    while(nodes.contains(nodeId))	
		nodeId=id.nextInt(128);
	    
	    
	    return nodeId;
	    
	}
	
	public static int createPayload()
	{
		
		Random id= new Random();
		return id.nextInt();
	}

}
