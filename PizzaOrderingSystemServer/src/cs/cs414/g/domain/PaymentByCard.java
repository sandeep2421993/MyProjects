package cs.cs414.g.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class PaymentByCard extends Payment{

	public String cardno;
	public PaymentByCard(double d, String type) {
		super(d, type);
		// TODO Auto-generated constructor stub
	}
	
	public Boolean authenticateCard(String cardNo,String CVV,String Expiry) throws ParseException
	{
		Boolean flag=true;
	    cardno=cardNo;
	    if(cardno.charAt(0)=='0')
	    	flag=false;
		String cvv=CVV;
		String expiry=Expiry;
		if(!(validateExpiry(expiry)))
			flag=false;
		if(!(cvv.matches("[0-9]+")))	
			flag=false;
        if(!(expiry.charAt(2)=='/'))
        	flag=false;
		if(cardno.isEmpty()||cvv.isEmpty()||expiry.isEmpty())
			flag=false;
		if(cardno.length()!=16)
			flag=false;
		return flag;
	}
	
	public String getCardNO()
	{
		return cardno.substring(cardno.length()-4);
	}
   
	/*public Boolean validateCard(String cnum)
	{
		Boolean flag=true;
		
		if(cnum.length()==16){
		    char[] c = cnum.toCharArray();
		    int[] cint = new int[16];
		    for(int i=0;i<16;i++){
		        if(i%2==1){
		            cint[i] = Integer.parseInt(String.valueOf(c[i]))*2;
		            if(cint[i] >9)
		                cint[i]=1+cint[i]%10;
		        }
		        else
		            cint[i] = Integer.parseInt(String.valueOf(c[i]));
		    }
		    int sum=0;
		    for(int i=0;i<16;i++){
		        sum+=cint[i];
		    }
		    if(sum%10==0)
		        flag=true;
		    else
		        flag=false;
		}else
		   flag=false;
		System.out.println(flag);
	return flag;
	}
	*/
	
	public Boolean validateExpiry(String expiry) throws ParseException
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yy");
		simpleDateFormat.setLenient(false);
		Date date= simpleDateFormat.parse(expiry);
		boolean expired = !date.before(new Date());
		return expired;
	}
}
