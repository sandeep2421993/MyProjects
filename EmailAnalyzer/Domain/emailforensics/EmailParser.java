package emailforensics;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class EmailParser {

	 String filePath;
	 String MessageID;
	 String date;
	 String sender;
	 String recipient;
	 ReceivedField r[];
	 int num_of_hops=0;
	ArrayList<String> smtpids =new ArrayList<String>();
	ArrayList<ReceivedField> rec= new ArrayList<ReceivedField>();
	
	public void parseHeader(String filePath)throws IOException 
	{
		this.filePath=filePath;
		calculateHops(this.filePath);
		int i=0;
		r=new ReceivedField[num_of_hops];
	 	Scanner scan=new Scanner(new File(filePath));
		String s=scan.nextLine();	
		while(scan.hasNext())
		{
		
		if(s.startsWith("Received"))
		{	

		r[i]=new ReceivedField();
		r[i].setContent(s.substring(9).trim());
		
		s=scan.nextLine();
		int count=0;
		do{
		
		if((!(s.startsWith("Received")))&&(scan.hasNext()))
		{   
			count++;
			
			if((s.matches("^\\s+.*"))&&(count<=3))
			{	
			//r[i].setContent("\n");	
			r[i].setContent(s);
			
			}
		}
		s=scan.nextLine();
		}while((s.matches("^\\s+.*")));
		
	   formatReceived(r[i]);
	   rec.add(r[i]);
		//smtpids.add(r[i].getRid());
	//System.out.println(r[i].getRid());
	    i++;
	 if(i==num_of_hops)
		break;
	}}
		while(scan.hasNext())
		{
			
			if(s.startsWith("Message-ID"))
			{
				MessageID=s.substring(11).trim();
			}
			if(s.startsWith("Date:"))
			{
				date=s.substring(5).trim();
			}
			if(s.startsWith("From:"))
			{
				sender=s.substring(5).trim();
			}
			if(s.startsWith("To:"))
			{
				recipient=s.substring(3).trim();
			}
			s=scan.nextLine();
		}
		/*System.out.println(MessageID);
		System.out.println(date);
		System.out.println(sender);
		System.out.println(recipient);
		*/
		scan.close();	
	
	}

	public void calculateHops(String filepath) throws IOException
	{
		String t;
		Scanner temp=new Scanner(new File(filepath));
		while(temp.hasNext())
		{
			t=temp.nextLine();	
			if(t.startsWith("Received"))
			{		
			num_of_hops++;	
			}
		}
		temp.close();
	}
	
	
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getMessageID() {
		return MessageID;
	}

	public void setMessageID(String messageID) {
		MessageID = messageID;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public int getNum_of_hops() {
		return num_of_hops;
	}

	public void setNum_of_hops(int num_of_hops) {
		this.num_of_hops = num_of_hops;
	}

	public String display(ReceivedField r)
	{
     return r.getFormattedContent();
	}
	
	public String display(int i)
	{
     return r[i].getFormattedContent();
	}
   public  void setPath(String path)
   {
	   filePath=path;
   }
   
   public void setSMTP()
   {
	   for(int i=0;i<num_of_hops;i++)
	   {
		   smtpids.add(r[i].getRid());
	   }
   }
   public ArrayList<String> getSMTP()
   {
	   return smtpids;
   }
  public Boolean check() throws UnknownHostException
  {
	  for(int i=0;i<num_of_hops;i++)
	  {
		  Boolean checker=r[i].checkHost();
		  if(checker==false)
		  {
			  return checker;
			  
		  }
			  
	  }
	  return true;
  }
  
  public void formatReceived(ReceivedField r)
  {
	  r.format();
	  
  }
  
  public ArrayList<ReceivedField> getRec()
  {
	  return rec;
  }
}
