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

public class RedeemUpdateCall extends AsyncTask<String,String,String> 
{

	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		String url="http://10.0.2.2:8000/redeemUpdate"+"?customer="+arg0[0]+"&points="+arg0[1];
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
				
				
				istream.close();
			}
			
		}
		catch (Exception e) 
		{
			
			e.printStackTrace();
		} 
		return result;
	}


}


