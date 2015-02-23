package cs.cs414.g.domain;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;

import cs.cs414.g.domain.MenuItem;


public class Menu implements Serializable {
	private ArrayList< MenuItem > foodItems = null;
	private ArrayList< Topping > toppings = null;
	
	public Menu(InputStream input) throws Exception {
		InputStreamReader reader = new InputStreamReader(input);
		BufferedReader br = new BufferedReader(reader);
		
		String line = null;
		ArrayList< MenuItem > foods = new ArrayList< MenuItem >();
		ArrayList< Topping > toppings = new ArrayList< Topping >();
		while ((line = br.readLine()) != null) {
			if (line.trim().length() == 0 || (line.length() > 0 && 
					line.charAt(0) == '#')) {
				continue;
			}
			
			String[] splits = line.split("\\|");
			for (int i = 0; i < splits.length; ++i) {
				splits[i] = splits[i].trim();
			}
			
			final int NUM_PIZZA_PARAMS = 6;
			final int NUM_TOPPINGS_PARAMS = 3;
			final int NUM_OTHER_PARAMS = 5;
			if (splits.length == NUM_PIZZA_PARAMS && splits[0].equals("0")) {
				// must be a pizza
				Pizza.Size size;
				if (splits[1].equalsIgnoreCase("Small")) {
					size = Pizza.Size.SMALL;
				}
				else if (splits[1].equalsIgnoreCase("Medium")) {
					size = Pizza.Size.MEDIUM;
				}
				else if (splits[1].equalsIgnoreCase("Large")) {
					size = Pizza.Size.LARGE;
				}
				else {
					throw new Exception("Unable to parse pizza size.");
				}
				
				double price = Double.parseDouble(splits[2]);
				double pricePerToppings = Double.parseDouble(splits[3]);
				int special = Integer.parseInt(splits[splits.length-2]);
				int ID = Integer.parseInt(splits[splits.length-1]);
				
			
				foods.add(new Pizza(size, price, pricePerToppings, ID, special));
			}
			else if (splits.length == NUM_TOPPINGS_PARAMS && splits[0].equals("1")) {
				toppings.add(new Topping(splits[1]));
			}
			else if (splits.length == NUM_OTHER_PARAMS && splits[0].equals("2")) {
				String name = splits[1];
				double price = Double.parseDouble(splits[2]);
				int ID = Integer.parseInt(splits[splits.length-1]);
				int special = Integer.parseInt(splits[splits.length-2]);
				foods.add(new MenuItem(name, price, ID, special));
			}
			else {
				throw new Exception("Unable to parse menu file -- error found.");
			}
		}
		
		this.foodItems = foods;
		this.toppings = toppings;
	}
	
	public Menu(ArrayList< MenuItem > foodItems, ArrayList< Topping > toppings) {
		this.foodItems = foodItems;
		this.toppings = toppings;
	}
	
	public MenuItem instantiateFood(MenuItem food) {
		return food.copy();
	}
	
	public ArrayList< MenuItem > getFoodItems() {
		ArrayList< MenuItem > deepCopy = new ArrayList< MenuItem >();
		for (MenuItem t : foodItems) {
			deepCopy.add(t.copy());
		}
		return deepCopy;
	}
	
	public ArrayList< Topping > getToppings() {
		ArrayList< Topping > deepCopy = new ArrayList< Topping >();
		for (Topping t : toppings) {
			deepCopy.add(new Topping(t));
		}
		return deepCopy;
	}
}
