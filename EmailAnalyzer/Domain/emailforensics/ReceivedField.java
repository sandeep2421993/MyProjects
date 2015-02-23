package emailforensics;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReceivedField {

	@SuppressWarnings("unused")
	private String rfrom,rby,rvia,rwith,rid,rfor,rtime,ipaddress;
	String labels[]={"from","by","via","with","id","for",";"};
		private String content="";
		private String formattedContent="";
		private String message="";
		static String suspectfromclaimed;
		static String suspectfromactual;
		static String suspectip;
		static String suspectby;
		String suspectwith;
		static String suspectid;
		
		public static String getSuspectfromclaimed() {
			return suspectfromclaimed;
		}

		public static void setSuspectfromclaimed(String suspectfromclaimed) {
			ReceivedField.suspectfromclaimed = suspectfromclaimed;
		}

		public static String getSuspectfromactual() {
			return suspectfromactual;
		}

		public static void setSuspectfromactual(String suspectfromactual) {
			ReceivedField.suspectfromactual = suspectfromactual;
		}

		public static String getSuspectip() {
			return suspectip;
		}

		public static void setSuspectip(String suspectip) {
			ReceivedField.suspectip = suspectip;
		}

		public static String getSuspectid() {
			return suspectid;
		}

		public static void setSuspectid(String suspectid) {
			ReceivedField.suspectid = suspectid;
		}

		public String getRfrom() {
			return rfrom;
		}

		public void setRfrom(String rfrom) {
			this.rfrom = rfrom;
		}

		public String getRwith() {
			return rwith;
		}

		public void setRwith(String rwith) {
			this.rwith = rwith;
		}

		public String getRid() {
			
			return rid;
		}

		public void setRid(String rid) {
			this.rid = rid;
		}

		public String getIpaddress() {
			return ipaddress;
		}

		public void setIpaddress(String ipaddress) {
			this.ipaddress = ipaddress;
		}

		public void setContent(String temp)
		{
			content+=temp;
		}
		
		public String getContent()
		{
			return content;
		}
	    
	    public String getFormattedContent()
	    {
	    	return formattedContent+"\n";
	    }
	    public void format()
	    {
	       int	beginIndex=0,endIndex=0;
	    
	    	//formattedContent=content;
	    for(int i=0;i<labels.length;i++)
	    {
	    	if(content.contains(labels[i]))
	    	{
	    		//System.out.println(labels[i]);
	    	    endIndex=content.indexOf(labels[i]);
	    	   // System.out.println(endIndex);
	    	    //if((beginIndex!=endIndex)||(beginIndex==0))
	    	    
	    		formattedContent+=content.substring(beginIndex, endIndex);
	            formattedContent+="\n";
	    	    
	                	   
	    	    }
	    	beginIndex=endIndex;
	    }
	      	formattedContent+=content.substring(endIndex).trim();
	      	formattedContent+="\n";
	      	setTokens();
	    }
	    
	    public String getMessage()
	    {
	    message="The email was received from the domain : "+rfrom.split("\\(")[0]+" by the domain : "+rby.split("\\(")[0]+"\nThe SMTP id corresponding to this is : "+rid;
	    return message;
	    }
	    public void setTokens()
	    {
	    	String tempcontent=formattedContent;
	    	tempcontent+="\n";
	    	int temp,temp1=0,temp2;
	    	for(int i=0;i<labels.length;i++)
	    	{
	    		 if(tempcontent.contains(labels[i]))
	    		    {
	    			   if(labels[i].equals("from")){
	    		        temp=tempcontent.indexOf(labels[i]);
	    		        temp2=tempcontent.indexOf("\n",temp1+1);
	    		        rfrom=tempcontent.substring(temp+labels[i].length(),temp2).trim();
	    		        temp1=temp2;
	    		       // System.out.println("rfrom:"+rfrom);
	    		        if(rfrom.contains("["))
	    		        {
	    		        ipaddress=rfrom.substring(rfrom.indexOf("[")+1,rfrom.indexOf("]"));
	    		       // System.out.println(ipaddress);
	    		        }
	    		    }
	    			 
	    			   if(labels[i].equals("by")){
	       		        temp=tempcontent.indexOf(labels[i]);
	       		        temp2=tempcontent.indexOf("\n",temp1+1);
	       		        rby=tempcontent.substring(temp+labels[i].length(),temp2).trim();
	       		        temp1=temp2;
	       		   //  System.out.println("rby:"+rby);
	    			   }
	    			   if(labels[i].equals("via")){
	       		        temp=tempcontent.indexOf(labels[i]);
	       		        temp2=tempcontent.indexOf("\n",temp1+1);
	       		        rvia=tempcontent.substring(temp+labels[i].length(),temp2).trim();
	       		        temp1=temp2;
	       		     System.out.println("rvia:"+rvia);
	    			   }
	    			   if(labels[i].equals("with")){
	       		        temp=tempcontent.indexOf(labels[i]);
	       		        temp2=tempcontent.indexOf("\n",temp1+1);
	       		        rwith=tempcontent.substring(temp+labels[i].length(),temp2).trim();
	       		        temp1=temp2;
	       		    // System.out.println("rwith:"+rwith);
	    	           }
	    			   if(labels[i].equals("id")){
	       		        temp=tempcontent.indexOf(labels[i]);
	       		        temp2=tempcontent.indexOf("\n",temp1+1);
	       		        rid=tempcontent.substring(temp+labels[i].length(),temp2).trim();
	       		        temp1=temp2;
	       		   //  System.out.println("rid:"+rid);
	    			   }
	       		    	if(labels[i].equals(";")){
	        		        temp=tempcontent.indexOf(labels[i]);
	        		        temp2=tempcontent.indexOf("\n",temp);
	        		        rtime=tempcontent.substring(temp+labels[i].length(),temp2).trim();
	        		 //       System.out.println("rdate:"+rtime);
	       		    	}
	       		    	
	       		    	
			
		}}
	    	
	    }
	    
	    public Boolean checkHost() throws UnknownHostException
	    {
	    	if(rfrom.contains("[")){
	    	//System.out.println("Inside checkHost..");
	    	//boolean flag = true;
	    	String host_split[]=rfrom.split("\\(");
	    	Pattern p= Pattern.compile("[A-Za-z]");
	    	Matcher m=p.matcher(host_split[1]);
	    	if(m.find())
	    	{
	    		System.out.println("1");
	    	int a=host_split[0].indexOf(".");
	    //	System.out.println(host_split[0].substring(a+1));
	    	if(host_split[1].contains(host_split[0].substring(a+1)))
	    		return true;
	    	else
	    	{
	    		suspectfromclaimed=host_split[0];
	    	//	System.out.println(host_split[0]);
	    		String susp[]=host_split[1].split("\\[");
	    		suspectfromactual=susp[0];
	    		suspectip=ipaddress;
	    		suspectid=rid;
	    		suspectby=rby.split("\\(")[0].trim();
	    	//	System.out.println(host_split[1]);
	    	//	System.out.println("1 failed");
	    		return false;
	    	}
	    	}
	    	else
	    	{
	    	//	System.out.println("2");
	    		InetAddress inetAddress = InetAddress.getByName(ipaddress);
	    		String host=inetAddress.getHostName();
	    		if(host.equals(ipaddress))
	    		{
	    	//		System.out.println("3");
	    			suspectby=rby.split("\\(")[0].trim();
	    			return true;
	    		}
	    		if(host_split[0].contains(host.substring(host.indexOf(".")+1)))
	    		{
	    	//		System.out.println("4");
	    			return true;
	    		}
	    		else
	    		{
	    	//		System.out.println("5");
	    			suspectfromclaimed=host_split[0];
		    //		System.out.println(host_split[0]);
		    		//String susp[]=host_split[1].split("[");
		    		suspectfromactual=host;
		    		suspectip=ipaddress;
		    		suspectid=rid;
		    		suspectby=rby.split("\\(")[0].trim();
		    //		System.out.println(host_split[1]);
		    //		System.out.println("1 failed");
		    		return false;
	    		}
	    	}
	    	
	    	
	    }
	    	 else
	    	    {
	    	    	return true;
	    	    }
	    }

		public static String getSuspectby() {
			return suspectby;
		}
	   
	}
