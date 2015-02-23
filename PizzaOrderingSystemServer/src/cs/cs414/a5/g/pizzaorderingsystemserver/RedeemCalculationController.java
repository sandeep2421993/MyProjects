package cs.cs414.a5.g.pizzaorderingsystemserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


public class RedeemCalculationController implements HttpHandler
{

	@Override
	public void handle(HttpExchange arg0) throws IOException 
	{
		// TODO Auto-generated method stub
		URI uri= arg0.getRequestURI();
		System.out.println(uri);
		String[] parts = uri.toString().split("=");
		String customerName = parts[1];
		System.out.println(customerName);
		String response = readFile(customerName);
		
		arg0.sendResponseHeaders(200, response.length());
		OutputStream stream =arg0.getResponseBody();
		stream.write(response.getBytes());
		stream.close();
		
	}

	private String readFile(String customerName) 
	{
		File file = new File("CustomerLogins");
		String discount="";
		try 
		{
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String newLine = bufferedReader.readLine();
			while(newLine != null)
			{
				String elements[] = newLine.split("\\|");
				
				if(elements[5].equals(customerName))
				{
					
					discount = (elements[6]);
					System.out.println(discount);
					break;
					
				}
				newLine = bufferedReader.readLine();
			  }
			   bufferedReader.close();
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return discount;


	}

}
