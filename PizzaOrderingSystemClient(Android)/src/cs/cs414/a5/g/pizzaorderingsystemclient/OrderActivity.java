package cs.cs414.a5.g.pizzaorderingsystemclient;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OrderActivity extends Activity implements OnClickListener{
	
	final ArrayList<String> orderSelected = new ArrayList<String>();
	final ArrayList<CheckBox> selectedcheckBox = new ArrayList<CheckBox>();
	public static String orderString = null;
	String removeItem = new String();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		
		Intent intent = getIntent();
		String resultXML = intent.getStringExtra("OrderString");
		Log.w("From OrderActivity",resultXML);
	
		String temp = new String();
		final String orderListStr = new String();
		DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
		DocumentBuilder b;
		try {
			b = f.newDocumentBuilder();
			Document doc = b.parse(new InputSource(new StringReader(resultXML)));
			NodeList orderList = doc.getElementsByTagName("orderitem");
			
			Log.w("OrderList length", orderList.getLength() + "");
			LinearLayout ll = (LinearLayout) findViewById(R.id.linearLayout3);
			for (int i = 0; i < orderList.getLength(); i++) {
				final CheckBox cb = new CheckBox(this);
				final Element order = (Element) orderList.item(i);
				//Text should be list of all pizza with toppings and Other items
				NodeList name = order.getElementsByTagName("pizza");
				Element line = (Element) name.item(0);
				temp += getCharacterDataFromElement(line);
				
				NodeList toppingsList = order.getElementsByTagName("topping");
				for (int j = 0; j < toppingsList.getLength(); j++) {
					Element element = (Element) toppingsList.item(j);
					temp +=  "-"+getCharacterDataFromElement(element) ;
				}
				
				cb.setText(temp);
				orderListStr.concat(temp);
				cb.setId(i);
				Log.w("OrderList ", orderListStr);
				cb.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						
						if (((CheckBox) v).isChecked()) {
							orderSelected
									.add(orderListStr);
							selectedcheckBox.add(cb);
						} 
					}
				});

				ll.addView(cb);
				temp="";
			}
				NodeList otherItems = doc.getElementsByTagName("otheritem");
				for (int k = 0; k < otherItems.getLength(); k++) {
					final CheckBox chkbx = new CheckBox(this);
					final Element others = (Element) otherItems.item(k);
					chkbx.setText(getCharacterDataFromElement(others));
					chkbx.setId(k);
					chkbx.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// is chkIos checked?
							if (((CheckBox) v).isChecked()) {
								orderSelected
										.add(getCharacterDataFromElement(others));
								selectedcheckBox.add(chkbx);
							} 
						}
					});

					ll.addView(chkbx);
					temp="";
				}
				TextView view = (TextView) findViewById(R.id.txtPrice);
				NodeList price = doc.getElementsByTagName("total");
				for(int l=0;l<price.getLength();l++){
					Element total = (Element) price.item(l);
					view.setText("Total = "+getCharacterDataFromElement(total));
				}
			
				Button btn = (Button) findViewById(R.id.removebtn);
				btn.setOnClickListener(this);
				
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		public static String getCharacterDataFromElement(Element e) {
			Node child = e.getFirstChild();
			if (child instanceof CharacterData) {
				CharacterData cd = (CharacterData) child;
				return cd.getData();
			}
			return null;
		}
		
		@Override
		public void onClick(View v) {
			//Only 1 button to remove items
			Toast toast;
			removeItem = "&remove=";
			for(CheckBox c: selectedcheckBox){
				removeItem += (String) c.getText();
				Log.w("Remove Item",removeItem);
				AsyncTask result = new SendOrder().execute(removeItem);
				
				try {
					orderString = (String) result.get();
					Log.w("After remove", orderString);
					selectedcheckBox.clear();
					Intent intent = getIntent();
					intent.putExtra("OrderString", orderString);
					finish();
					toast = Toast.makeText(this, "Removed Item!!", Toast.LENGTH_LONG);
					toast.setGravity(Gravity.TOP, 25, 400);
					toast.show();
					startActivity(intent);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				removeItem = "&remove=";
			}
		}
}
