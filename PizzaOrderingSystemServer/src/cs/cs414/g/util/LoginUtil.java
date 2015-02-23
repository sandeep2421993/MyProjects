package cs.cs414.g.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class LoginUtil {
	static Map<String,String> loginDetails = new TreeMap<String, String>();
	public static String userName,password;
	
	/**
	 * 
	 * @param userName
	 * @param password
	 * @return boolean
	 * @throws FileNotFoundException
	 */
	public static boolean authenticate(String userName,String password){
		System.out.println("11111");
		boolean result = false;
		String line;
		try {
		FileInputStream inFile = new FileInputStream("employees.txt");
		BufferedReader content = new BufferedReader(new InputStreamReader(inFile));
			while((line = content.readLine()) != null){
					String elements[] = line.split("-");
					loginDetails.put(elements[2], elements[3]);
			}
			System.out.println(userName+" "+password);
			System.out.println(loginDetails.toString());
			if(loginDetails.containsKey(userName)&&loginDetails.containsValue(password))
				result = true;
			content.close();
		} catch (IOException e) {
			System.out.println("Error opening menu"+e.getMessage());
		}
		System.out.println(result);
		return result;
	}
}
