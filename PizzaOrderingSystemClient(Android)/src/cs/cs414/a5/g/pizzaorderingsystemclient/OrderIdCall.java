package cs.cs414.a5.g.pizzaorderingsystemclient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class OrderIdCall extends AsyncTask {

	@Override
	protected Object doInBackground(Object... params) {
        String result=null;
		
		String url="http://10.0.2.2:8000/orderid";
		HttpGet httpget= new HttpGet(url);
		HttpClient httpclient= new DefaultHttpClient();
		Log.w("ENTERED","ORDER ID CALL");
		
		HttpResponse response;
		try {
			response=httpclient.execute(httpget);
			HttpEntity entity=response.getEntity();
			
			if(entity!=null)
			{
				
				InputStream istream=entity.getContent();
				
				result=convertToString(istream);
				
				istream.close();
			}
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		return result;
	}

	private String convertToString(InputStream is) {
		
BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		
		//StringBuffer creates the string efficiently
		StringBuffer buff = new StringBuffer();

		String line = null;
		try {
			//iterate over all the strings and build the value
			line = reader.readLine();
			while (line != null) 
			{
				buff.append(line);
				line = reader.readLine();
			}
			is.close();
		} 
		catch (Exception e) 
		{
			//you can change your code to handle an exception how you want
			System.out.println(e.toString());
		} 
		return buff.toString();
	}
	}


