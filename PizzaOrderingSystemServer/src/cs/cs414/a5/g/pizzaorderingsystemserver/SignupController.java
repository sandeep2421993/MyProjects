package cs.cs414.a5.g.pizzaorderingsystemserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import cs.cs414.a5.g.util.DataUtil;

public class SignupController implements HttpHandler{

	String name;
	String response=null;
	int customerID;
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		URI uri= exchange.getRequestURI();
		
		
		Boolean success=parseRequest(uri.getQuery());
		//System.out.println(uri.getQuery());
		if(success==true)
		{
		response=""+customerID;
        DataUtil.setLoggedin(success);
		}
		
		
		exchange.sendResponseHeaders(200, response.length());
		OutputStream stream =exchange.getResponseBody();
		stream.write(response.getBytes());
		stream.close();
	}

	private Boolean parseRequest(String query) {
		Boolean flag=true;
		Boolean there=true;
		String[] splits=query.split("&");
		//for(int i=0;i<splits.length;i++)
		//System.out.println(splits[i]);
		//System.out.println(query);
		try {
		 there= checkUsername(splits);
		} catch (FileNotFoundException e1) {
			
			e1.printStackTrace();
		}
		if(there)
		{
		File file = new File("CustomerLogins");
		try 
		{
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String LineRead = bufferedReader.readLine();
			System.out.println(LineRead);
			int count = 0;
			String[] updated = new String[100];
			while(LineRead != null)
			{
			    updated[count] = LineRead;
				count++;
				LineRead = bufferedReader.readLine();
			}
			bufferedReader.close();
			
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for(int i=0; i<100; i++)
			{
				
				if(updated[i] != null) bufferedWriter.write(updated[i]+"\n");
				
			}
			for(int i=0;i<splits.length;i++)
			{
				String[] temp=splits[i].split("=");
			bufferedWriter.write(temp[1]+"|");			
			}
			
			bufferedWriter.write((count+1)+"|"+100+"\n");
			bufferedWriter.close();
			customerID=count+1;
			flag=true;
		}
		catch(Exception e)
		{
		
		    e.printStackTrace();
			flag=false;
		}
		}
		else
		{
		flag=false;
		}
	return flag;
	}

	private Boolean checkUsername(String[] splits) throws FileNotFoundException {
		Boolean flag=true;
		Scanner scanner=new Scanner(new File("CustomerLogins"));
		for(int i=0;i<splits.length;i++)
		{
			String[] temp=splits[i].split("=");
			if(temp[0].equals("Username"))
			{
				while(scanner.hasNext())
				{
					String line=scanner.nextLine();
					String elements[]=line.split("\\|");
					if(elements[3].equals(temp[1]))
					{
						
						System.out.println("Username exists");
						flag=false;
						break;
					}
				}
                
                
			}
		
     	}
       return flag;
}
}
