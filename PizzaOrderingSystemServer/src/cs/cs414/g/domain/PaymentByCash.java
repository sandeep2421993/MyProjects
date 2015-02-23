package cs.cs414.g.domain;

public class PaymentByCash extends Payment {
	
	private double price;

	public PaymentByCash(double d, String type) {
		super(d, type);
		this.price=d;
		
		// TODO Auto-generated constructor stub
	}
	
	public double getReturnAmount(double cashGiven)
	{
          return cashGiven-this.price;
	}

}
