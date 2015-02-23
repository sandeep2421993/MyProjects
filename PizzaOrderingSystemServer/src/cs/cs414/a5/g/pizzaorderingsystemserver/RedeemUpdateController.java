package cs.cs414.a5.g.pizzaorderingsystemserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


public class RedeemUpdateController implements HttpHandler
{

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		// TODO Auto-generated method stub
		
		URI uri= arg0.getRequestURI();
		
		String[] parts = uri.toString().split("=");
		String[] customer = parts[1].split("&");
		String customerName = customer[0];
		String[] customerPoints = parts[2].split("\\.");
		String pointMatters = customerPoints[0];
		if(customerName.equals("0"))
		{
			
		}
		
		else
		{
		File file = new File("CustomerLogins");
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String inputString = "";
		String newLine = bufferedReader.readLine();
		while(newLine != null)
		{
			String[] eleString = newLine.split("\\|");
			if(eleString[5].equals(customerName))
			{
				
				newLine = (newLine.replaceAll(eleString[6], pointMatters));
			}
			inputString = inputString + newLine + System.lineSeparator();
			newLine = bufferedReader.readLine();
		}
		
		FileOutputStream os = new FileOutputStream(file);
	    os.write(inputString.getBytes());
		bufferedReader.close();
		os.close();
		fileReader.close();
		}
	}

}
