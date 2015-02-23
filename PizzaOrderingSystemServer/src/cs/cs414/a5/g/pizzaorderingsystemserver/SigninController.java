package cs.cs414.a5.g.pizzaorderingsystemserver;

//import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
//import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Scanner;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import cs.cs414.a5.g.util.DataUtil;

public class SigninController implements HttpHandler{

	String customerID;
	String name;
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		URI uri= exchange.getRequestURI();
		
		
		String response=null;
		String temp="";
		Boolean r=authenticate(uri.getQuery());;
		if(r==true)
		{
			temp+=name;
			temp+="-";
			temp+=customerID;
			response=temp;
		DataUtil.setLoggedin(r);
		}
		
		exchange.sendResponseHeaders(200, response.length());
		OutputStream stream =exchange.getResponseBody();
		stream.write(response.getBytes());
		stream.close();
		
	}

	private Boolean authenticate(String query) throws FileNotFoundException 
	{
		
		boolean flag=false;
		String[] elements;
		String[] splits=query.split("&");
	String username=splits[0];
	String password=splits[1];
		System.out.println(username);
		System.out.println(password);
		File file=new File("CustomerLogins");
		Scanner scanner=new Scanner(file);
		try {
			//FileReader fileReader = new FileReader(file);
			//BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while(scanner.hasNext())
			
			
			//while(newLine != null)
			
			{
				String newLine = scanner.nextLine();
				elements = newLine.split("\\|");
				
				for(int i=0;i<elements.length;i++)
				//System.out.println(elements[i]);
				
				//System.out.println(elements.length);
				
				//System.out.println(elements[3]);
				//System.out.println(elements[elements.length]);
				
				if(elements[3].equals(username))
				{
					
					if(elements[4].equals(password))
					{
					    flag = true;
					    name=elements[0];
					    customerID=elements[5];
					}
					else
					{
						flag=false;
					}
					
					
				}
				
				
				//newLine = bufferedReader.readLine();
				
			}
		//	bufferedReader.close();
			
				
		} catch (Exception e) {
			
			System.out.println("CustomerLogins does not exist");
			e.printStackTrace();
		}
		
			scanner.close();
		return flag;
		
	}
}

	


