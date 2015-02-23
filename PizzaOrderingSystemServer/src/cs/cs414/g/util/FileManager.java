package cs.cs414.g.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import cs.cs414.g.domain.Customer;
import cs.cs414.g.domain.Order;

public class FileManager {

	public static void writeOrders(Map<String, Vector<Order>> orders, String file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
	    ObjectOutputStream oos = new ObjectOutputStream(fos);
	    oos.writeObject(orders);
	    oos.writeInt(Order.orderCounter);
	    oos.close();
	}
	
	public static Map<String, Vector<Order>> readOrders(String file) throws IOException {
		Map< String, Vector< Order > > orders = Collections.synchronizedMap(new HashMap< String, Vector< Order > >());
		FileInputStream fis = new FileInputStream(file);
	    ObjectInputStream ois = new ObjectInputStream(fis);
	    try {
			orders = Collections.synchronizedMap((Map<String, Vector<Order>>) ois.readObject());
			Order.orderCounter = ois.readInt();
		} catch (ClassNotFoundException e) {
			System.err.println("Error reading orders database.");
			e.printStackTrace();
		}
	    ois.close();
	    return orders;
	}
	
	public static void writeCustomers(Map< String, Customer > customers, String file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
	    ObjectOutputStream oos = new ObjectOutputStream(fos);
	    oos.writeObject(customers);
	    oos.close();
	}
	public static Map< String, Customer > readCustomers(String file) throws IOException {
		Map< String, Customer > customers = Collections.synchronizedMap(new HashMap< String, Customer >());
		FileInputStream fis = new FileInputStream(file);
	    ObjectInputStream ois = new ObjectInputStream(fis);
	    try {
			customers = Collections.synchronizedMap((Map<String, Customer>) ois.readObject());
		} catch (ClassNotFoundException e) {
			System.err.println("Error reading customers database.");
			e.printStackTrace();
		}
	    ois.close();
	    return customers;
	}

}
