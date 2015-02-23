package cs.cs414.a5.g.pizzaorderingsystemclient;


//import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class MainActivity extends Activity {
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button signin=(Button)findViewById(R.id.button3);
		signin.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0)
			{
				Intent intent3=new Intent(MainActivity.this,SignInActivity.class);
				startActivity(intent3);
			}
			
		});
		
		Button signup=(Button)findViewById(R.id.button2);
		signup.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0)
			{
				Intent intent2= new Intent(MainActivity.this,SignupActivity.class);
				startActivity(intent2);
			}
			
		});
		
		Button guest=(Button)findViewById(R.id.button1);
		guest.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0)
			{
				setName();
			}

			private void setName() {
				AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
				LayoutInflater inflater= getLayoutInflater();
				final View myView= inflater.inflate(R.layout.login_dialog, null);
				builder.setTitle("Info");
				builder.setMessage("Enter Name:");
				builder.setView(myView);
				 builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {  
					    public void onClick(DialogInterface dialog, int whichButton) {  
					        //String value = input.getText().toString();
					        EditText mUserText;
					        mUserText = (EditText) myView.findViewById(R.id.editText1);
					        String name = mUserText.getText().toString();
					       // Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();
					        Intent intent=new Intent(MainActivity.this,KioskActivity.class);
					        intent.putExtra("GName",name);
					        intent.putExtra("CustomerID","0");
					        startActivity(intent);
					                          
					       }  
					     });  

					    builder.setNegativeButton("Skip", new DialogInterface.OnClickListener() {

					        public void onClick(DialogInterface dialog, int which) {
					        	//Toast.makeText(getApplicationContext(),"Skipped",Toast.LENGTH_LONG).show();
					        	Intent intent=new Intent(MainActivity.this,KioskActivity.class);
						        intent.putExtra("GName","Guest");
						        intent.putExtra("CustomerID","0");
						        startActivity(intent);
					        }
					    });		    
                builder.create();
				builder.show();
				
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
