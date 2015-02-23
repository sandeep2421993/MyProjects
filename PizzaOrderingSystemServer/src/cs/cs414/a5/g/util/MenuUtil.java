package cs.cs414.a5.g.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import cs.cs414.g.domain.Menu;
import cs.cs414.g.domain.Pizza;

public class MenuUtil {

	public static Menu menuList = null;
	
	public static Menu getMenuItems(){

		FileInputStream menuFileStream = null;
		try {
			File file = new File("menu"); 
			menuFileStream = new FileInputStream(file);
		}
		catch (FileNotFoundException e) {
			System.err.println("Unable to open menu file. Exiting...");
			System.exit(1);
		}
		
		try {
			menuList = new Menu(menuFileStream);
		}
		catch (Exception exception) {
			System.err.println(exception.getMessage());
			exception.printStackTrace(System.err);
			System.exit(1);
		}
		return menuList;
	}
	
	public static double getPricePerTopping(String type){
		double pricePerToppings =0.0;
		FileInputStream menuFileStream = null;
		try {
			File file = new File("menu"); 
			menuFileStream = new FileInputStream(file);
		}
		catch (FileNotFoundException e) {
			System.err.println("Unable to open menu file. Exiting...");
			System.exit(1);
		}
		InputStreamReader reader = new InputStreamReader(menuFileStream);
		BufferedReader br = new BufferedReader(reader);
		
		String line = null;
		
		try {
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
				if (splits.length == NUM_PIZZA_PARAMS && splits[0].equals("0")) {
					if (splits[1].equalsIgnoreCase(type))
					 pricePerToppings = Double.parseDouble(splits[3]);	
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pricePerToppings;
	}

	public static double getPrice(String string) {

		double price =0.0;
		FileInputStream menuFileStream = null;
		try {
			File file = new File("menu"); 
			menuFileStream = new FileInputStream(file);
		}
		catch (FileNotFoundException e) {
			System.err.println("Unable to open menu file. Exiting...");
			System.exit(1);
		}
		InputStreamReader reader = new InputStreamReader(menuFileStream);
		BufferedReader br = new BufferedReader(reader);
		
		String line = null;
		
		try {
			while ((line = br.readLine()) != null) {
				if (line.trim().length() == 0 || (line.length() > 0 && 
						line.charAt(0) == '#')) {
					continue;
				}
				
				String[] splits = line.split("\\|");
				for (int i = 0; i < splits.length; ++i) {
					splits[i] = splits[i].trim();
				}
				final int NUM_OTHER_PARAMS = 5;
				if (splits.length == NUM_OTHER_PARAMS && splits[0].equals("2")) {
					if (splits[1].equalsIgnoreCase(string))
					 price = Double.parseDouble(splits[2]);	
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return price;
	
	}

	public static double getPricePizza(String string) {

		double price =0.0;
		FileInputStream menuFileStream = null;
		try {
			File file = new File("menu"); 
			menuFileStream = new FileInputStream(file);
		}
		catch (FileNotFoundException e) {
			System.err.println("Unable to open menu file. Exiting...");
			System.exit(1);
		}
		InputStreamReader reader = new InputStreamReader(menuFileStream);
		BufferedReader br = new BufferedReader(reader);
		
		String line = null;
		
		try {
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
				if (splits.length == NUM_PIZZA_PARAMS && splits[0].equals("0")) {
					if (splits[1].equalsIgnoreCase(string))
					 price = Double.parseDouble(splits[2]);	
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return price;
	
	}
}
