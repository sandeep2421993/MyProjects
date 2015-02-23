package cs.cs414.a5.g.pizzaorderingsystemclient;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		
		Button submit=(Button)findViewById(R.id.submitButton);
		submit.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0)
			{
                int flag=0;
                String response = null;
                
				EditText nameText = (EditText) findViewById(R.id.editText1);
				String name = nameText.getText().toString().trim();
				name=name.replace(" ","+");

				EditText emailText = (EditText) findViewById(R.id.editText2);
				String email = emailText.getText().toString();
				

				EditText addressText = (EditText) findViewById(R.id.editText3);
				String address = addressText.getText().toString().trim();
				address=address.replace(" ","+");

				EditText userText = (EditText) findViewById(R.id.editText4);
				String username = userText.getText().toString();
				

				EditText passText = (EditText) findViewById(R.id.editText5);
				String password = passText.getText().toString();
				

				EditText passConfirmText = (EditText) findViewById(R.id.editText6);
				String confirmPassword = passConfirmText.getText().toString();
				
				if(password.compareTo(confirmPassword)!=0)
				{
		        flag=1;
				Toast.makeText(getApplicationContext(),"Passwords dont match",Toast.LENGTH_LONG).show();
				}
				else
				{
					flag=0;
				}
				if(flag==0)
				{
					String message="Name="+name+"&"+"Email="+email+"&"+"Address="+address+"&"+"Username="+username+"&"+"Password="+password;
				//	Toast toast=Toast.makeText(getApplicationContext(), message,Toast.LENGTH_LONG);
				//	toast.setGravity(Gravity.CENTER, 0, 0);
				//	toast.show();
					AsyncTask result= new SignupCall().execute(message);
					try {
						response=(String)result.get();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					
				if(response==null)
				{
					Toast.makeText(getApplicationContext(),"Signup failed,username exists" ,Toast.LENGTH_LONG).show();
				}
				else
				{
				
				  // Toast.makeText(getApplicationContext(),response ,Toast.LENGTH_LONG).show();
				   Intent intent=new Intent(SignupActivity.this,KioskActivity.class);
				   intent.putExtra("GName",name);
				   intent.putExtra("CustomerID",response);
				   startActivity(intent);
				}
				}
				
				
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.signup, menu);
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
