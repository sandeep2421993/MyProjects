package cs.cs414.a5.g.pizzaorderingsystemclient;

import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Delivery extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delivery);
		
		final double d = Double.parseDouble(getIntent().getStringExtra("TotalPrice").trim());
		
		String discountRedeem;
		final String customerID = getIntent().getStringExtra("CustomerID").trim();
		//customerID
		if(customerID.equals("0"))
		{
			CheckBox redeemCheck = (CheckBox) findViewById(R.id.checkBox1);
			redeemCheck.setEnabled(false);
			
			TextView textView65 = (TextView) findViewById(R.id.textView1);
			textView65.setText("Guest does not have redeem points!");
		}
		else
		{
			
		}
		final AsyncTask redeemResult = new RedeemPointCall().execute(customerID);
		try 
		{
			
			 discountRedeem = (String) redeemResult.get();
			//Toast.makeText(getApplicationContext(),discountRedeem,Toast.LENGTH_LONG).show();
		} 
		catch (Exception e1) 
		{
			discountRedeem = "";
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		TextView textView2 = (TextView) findViewById(R.id.textView3);
		textView2.setText("Your Amount: "+Double.toString(d));
		
		CheckBox redeemCheck = (CheckBox) findViewById(R.id.checkBox1);
		redeemCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked)
				{
					TextView textView5 = (TextView) findViewById(R.id.textView3);
					String[] elements = textView5.getText().toString().split(":");
					
					TextView textView6 = (TextView) findViewById(R.id.textView1);
					String[] redeemElements = textView6.getText().toString().split(" ");
					
					double amount = Double.parseDouble(elements[1]);
					double points = Double.parseDouble(redeemElements[2]);
					points = points/20;
					amount = amount - points;
					
					DecimalFormat df = new DecimalFormat("#.##"); 
					DecimalFormat df1 = new DecimalFormat("#"); 
					//Toast.makeText(getApplicationContext(),Double.toString(amount),Toast.LENGTH_LONG).show();
					if(amount>=0) textView5.setText("Your New Amount:"+(df.format(amount)));
					else 
						{
							textView5.setText("Your New Amount: 0");
							CheckBox redeemDiss = (CheckBox) findViewById(R.id.checkBox1);
							redeemDiss.setEnabled(false);
							Button buttonAppl = (Button) findViewById(R.id.button3);
							buttonAppl.setEnabled(false);
						}
					textView6.setText("You Have 0 Redeem Points Left Now!");
					//Toast.makeText(getApplicationContext(),redeemElements[2],Toast.LENGTH_LONG).show();
				}
				else
				{
					try 
					{
						TextView textView8 = (TextView) findViewById(R.id.textView3);
						String[] elements = textView8.getText().toString().split(":");
						String newdiscountRedeem = (String) redeemResult.get();
						
						double amount = Double.parseDouble(elements[1]);
					//	Toast.makeText(getApplicationContext(),textView8.getText().toString(),Toast.LENGTH_LONG).show();
						double points = Double.parseDouble(newdiscountRedeem);
						points = points/20;
						amount = amount + points;
						points = points*20;
						DecimalFormat df = new DecimalFormat("#.##"); 
						DecimalFormat df1 = new DecimalFormat("#"); 
						//Toast.m//akeText(getApplicationContext(),Double.toString(amount),Toast.LENGTH_LONG).show();
						if(amount >= 0) textView8.setText("Your New Amount:"+(df.format(amount)));
						else 
							{
							textView8.setText("Your New Amount: 0");
							CheckBox redeemDiss = (CheckBox) findViewById(R.id.checkBox1);
							redeemDiss.setEnabled(false);
							Button buttonAppl = (Button) findViewById(R.id.button3);
							buttonAppl.setEnabled(false);
							}
						TextView textView9 = (TextView) findViewById(R.id.textView1);
						textView9.setText("You Have "+df1.format(points)+" Redeem Points Left Now!");
					}
					catch (Exception e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
			}
		});
		
		TextView textView4 = (TextView) findViewById(R.id.textView1);
		textView4.setText("You Have "+discountRedeem+" Redeem Points");
		
		Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent3 = new Intent(Delivery.this, ThankYou.class);
				intent3.putExtra("customerID", customerID);
				TextView textView10 = (TextView) findViewById(R.id.textView3);
				String[] elements = textView10.getText().toString().split(":");
				
				TextView textView11 = (TextView) findViewById(R.id.textView1);
				String[] redeemElements = textView11.getText().toString().split(" ");
				
				double amount = Double.parseDouble(elements[1]);
				double points = Double.parseDouble(redeemElements[2]);
				
				double newPoints = (points) + (amount * 2);
				intent3.putExtra("customerPoints", Double.toString(newPoints));
				startActivity(intent3);
			}
		});
        
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent4 = new Intent(Delivery.this, PaymentCard.class);
				intent4.putExtra("customerID", customerID);
				TextView textView10 = (TextView) findViewById(R.id.textView3);
				String[] elements = textView10.getText().toString().split(":");
				
				TextView textView11 = (TextView) findViewById(R.id.textView1);
				String[] redeemElements = textView11.getText().toString().split(" ");
				
				double amount = Double.parseDouble(elements[1]);
				double points = Double.parseDouble(redeemElements[2]);
				
				double newPoints = (points) + (amount * 2);
				intent4.putExtra("customerID", customerID);
				intent4.putExtra("customerPoints", Double.toString(newPoints));
				startActivity(intent4);
			}
		});
        
        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText editText = (EditText) findViewById(R.id.editText2);
			    String couponNumber = editText.getText().toString();
			    
			    TextView textView16 = (TextView) findViewById(R.id.textView1);
				String[] redeemElements = textView16.getText().toString().split(" ");
			
				double points = Double.parseDouble(redeemElements[2]);
				AsyncTask result3=new ReduceCouponCall().execute("ABCD9999");
				String pointToReduceAllow = "";
				try {
					pointToReduceAllow = (String) result3.get();
					////Toast.makeText(getApplicationContext(),pointToReduceAllow,//Toast.LENGTH_LONG).show();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				if(couponNumber.equals("ABCD9999") && points >= Double.parseDouble(pointToReduceAllow))
				{
					
					Button buttonApply = (Button) findViewById(R.id.button3);
					buttonApply.setEnabled(false);
					
					Button buttonDis = (Button) findViewById(R.id.button2);
					buttonDis.setEnabled(false);
					
					Button buttonNew = (Button) findViewById(R.id.button1);
					buttonNew.setText("Submit Order!");
					
					TextView textViewF = (TextView) findViewById(R.id.textView3);
					
					textViewF.setText("Your New Amount: 0");
					
					TextView textView70 = (TextView) findViewById(R.id.textView1);
					String[] redeemElementsF = textView70.getText().toString().split(" ");
					
					
					double newPoint = Double.parseDouble(redeemElements[2]) - Double.parseDouble(pointToReduceAllow) + 100;
					textView70.setText("You Have "+newPoint+" Redeem Points Left Now!");
					
					CheckBox redeemChange = (CheckBox) findViewById(R.id.checkBox1);
					redeemChange.setEnabled(false);
					Toast.makeText(getApplicationContext(),"Your Discount Is Applied Successfully",Toast.LENGTH_LONG).show();
					
				}
				else
				{
					AsyncTask result=new ReduceCouponCall().execute(couponNumber);
					TextView textView7 = (TextView) findViewById(R.id.textView3);
					String[] elements = textView7.getText().toString().split(":");
					double beforeDisc = Double.parseDouble(elements[1]);
					
					Button buttonApply = (Button) findViewById(R.id.button3);
					
					try 
					{
						String discount = (String) result.get();
						double discountAmount = Double.parseDouble(discount);
						double newPrice = beforeDisc - discountAmount;
						if(discount.equals(""))
						{
							
						}
						else
						{
							buttonApply.setEnabled(false);
						}
						
						TextView textView = (TextView) findViewById(R.id.textView3);
						DecimalFormat df = new DecimalFormat("#.##");   
						//df.format(newPrice);
						if(newPrice>=0) textView.setText("Your New Amount: "+df.format(newPrice));
						else 
							{textView.setText("Your New Amount: 0");
						CheckBox redeemDiss = (CheckBox) findViewById(R.id.checkBox1);
						redeemDiss.setEnabled(false);
						
						Button buttonAppl = (Button) findViewById(R.id.button3);
						buttonAppl.setEnabled(false);
							}
						Toast.makeText(getApplicationContext(),"Your Discount Is Applied Successfully",Toast.LENGTH_LONG).show();
					} 
					catch (Exception e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.delivery, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}