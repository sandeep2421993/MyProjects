package cs.cs414.g.domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Payment {

	private String type;
	private double amount;
	private double discountedPrice;
	public Payment(double d, String type) {
		this.setType(type);
		this.setAmount(d);
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

   public Boolean processCoupon(String couponCode)
   {   
	   Boolean flag=false;
	   double discount=0.0;
	   
	   File file = new File("coupon.txt");
	   try {
	   FileReader fileReader = new FileReader(file);
	   BufferedReader bufferedReader = new BufferedReader(fileReader);
	   String newLine = bufferedReader.readLine();
	   while(newLine != null)
	   {
	   String elements[] = newLine.split("-");
	   if(elements[0].equals(couponCode))
	   {
	   flag=true;
	   discount = Float.parseFloat(elements[1]);
	   discountedPrice=amount-discount;
	   break;
	   }
	   newLine = bufferedReader.readLine();
	   }
	   bufferedReader.close();
	   } 
	   catch (FileNotFoundException e) {
	   System.out.println("coupon.txt does not exist");
	   e.printStackTrace();
	   }
	   catch (IOException e) {
	   System.out.println("error while reading file");
	   e.printStackTrace();
	   }
	   
	   
	   return flag;
   }
   
   public double getDiscountedPrice()
   {
	   return discountedPrice;
   }
}
