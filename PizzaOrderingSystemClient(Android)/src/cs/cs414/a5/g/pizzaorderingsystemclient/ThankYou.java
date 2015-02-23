package cs.cs414.a5.g.pizzaorderingsystemclient;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ThankYou extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thank_you);
		
		Bundle extras = getIntent().getExtras();
		String customerID = extras.getString("customerID");
		String redeemPoints = extras.getString("customerPoints");
		//Toast.makeText(getApplicationContext(),customerID,Toast.LENGTH_LONG).show();
       // Toast.makeText(getApplicationContext(),redeemPoints,Toast.LENGTH_LONG).show();
		Toast.makeText(getApplicationContext(),"Thank you!",Toast.LENGTH_LONG).show();
		final AsyncTask redeemResult = new RedeemUpdateCall().execute(customerID.trim(), redeemPoints.trim());
	
	
		
		
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent Main = new Intent(ThankYou.this, MainActivity.class);
				startActivity(Main);
			}
		});
	}
	
	@Override
	public void onBackPressed() 
	{
		Toast.makeText(getApplicationContext(),"You Cannot Go Back Now! Call Pizza Store If Need Help",Toast.LENGTH_LONG).show();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.thank_you, menu);
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
