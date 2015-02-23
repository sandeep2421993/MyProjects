package cs.cs414.g.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class OrderUtil {

	static File file = new File("order.txt");

	public static String getOrderText() {
		String orderList = new String();
		try {
			FileInputStream is = new FileInputStream(file);
			InputStreamReader reader = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(reader);
			String line = null;
			try {
				while ((line = br.readLine()) != null) {
					if (line.trim().length() == 0
							|| (line.length() > 0 && line.charAt(0) == '#')) {
						continue;
					}

					String[] splits = line.split("\\|");
					int length = splits.length;
//					if (!splits[length - 1].equalsIgnoreCase("COMPLETED")) {
//						for (int i = 0; i < splits.length; ++i) {
//							splits[i] = splits[i].trim();
//						}
						for (int i = 0; i < splits.length; i++)
							orderList += splits[i] + " ";
						orderList += "\n";
					}
				//}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return orderList;
	}

}