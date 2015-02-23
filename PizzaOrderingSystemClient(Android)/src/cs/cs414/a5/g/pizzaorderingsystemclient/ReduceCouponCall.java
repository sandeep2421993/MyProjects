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
import android.widget.Toast;

public class ReduceCouponCall extends AsyncTask<String,String,String> 
{

	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		String url="http://10.0.2.2:8000/coupon"+"?number="+arg0[0];
		HttpGet httpget= new HttpGet(url);
		HttpClient httpclient= new DefaultHttpClient();
		HttpResponse response;
		String result = null;
		try 
		{
			response = httpclient.execute(httpget);
			
			HttpEntity entity=response.getEntity();
			
			if(entity!=null)
			{
				InputStream istream=entity.getContent();
				
				result = convertToString(istream);
				Log.w("Show", result);
				istream.close();
			}
			
		}
		catch (Exception e) 
		{
			
			e.printStackTrace();
		} 
		return result;
	}
	
	private String convertToString(InputStream is) {
		//buffered reader reads the input stream
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		
		//StringBuffer creates the string efficiently
		StringBuffer buff = new StringBuffer();

		String line = null;
		try {
			//iterate over all the strings and build the value
			line = reader.readLine();
			while (line != null) 
			{
				buff.append(line + "\n");
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
