package cs.cs414.a5.g.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public final class DataUtil {
	
public static Boolean loggedin=false;
public static Set<String> usernames=new HashSet<String>();


public static Set<String> getUsernames() throws FileNotFoundException {
	
	Scanner scanner=new Scanner(new File("CustomerLogins"));
	while(scanner.hasNext())
	{
		String line=scanner.nextLine();
		String temp[]=line.split("\\|");
		usernames.add(temp[3]);
	}
	scanner.close();
	return usernames;
}

public static void addUsername(String username) {
	DataUtil.usernames.add(username);
	System.out.println(username+" Added");
}

public static Boolean getLoggedin() {
	return loggedin;
}

public static void setLoggedin(Boolean loggedin) {
	DataUtil.loggedin = loggedin;
}



}
