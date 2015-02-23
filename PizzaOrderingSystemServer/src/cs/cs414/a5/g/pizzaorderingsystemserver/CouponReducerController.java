package cs.cs414.a5.g.pizzaorderingsystemserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


public class CouponReducerController implements HttpHandler{

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		// TODO Auto-generated method stub
		URI uri= arg0.getRequestURI();
		System.out.println(uri);
		String[] parts = uri.toString().split("=");
		String couponNumber = parts[1];
		String response = readFile(couponNumber);
		
		arg0.sendResponseHeaders(200, response.length());
		OutputStream stream =arg0.getResponseBody();
		stream.write(response.getBytes());
		stream.close();
		
	}
	public String readFile(String couponCode)
	{
		File file = new File("coupon.txt");
		String discount="";
		if(couponCode.equals("ABCD9999"))
		{
			FileReader fileReader;
			try {
				fileReader = new FileReader(new File("redeemCount"));
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				
				discount = bufferedReader.readLine();
				System.out.println(discount);
				bufferedReader.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
					
				
			  
			   
		
		}
		else
		{
		try 
		{
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String newLine = bufferedReader.readLine();
			while(newLine != null)
			{
				String elements[] = newLine.split("-");
				if(elements[0].equals(couponCode))
				{
					
					discount = (elements[1]);
					
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
		}
		return discount;

	}
}
