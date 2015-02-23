package cs.cs414.g.domain;

public class Customer implements java.io.Serializable
{
	private static final long serialVersionUID = 5614201153279683663L;
	private String phoneNumber;
	private String name;

	public Customer(String phoneNumber, String name) {
		this.phoneNumber = phoneNumber;
		this.name = name;
	}
	public Customer(String name)
	{
		this.name = name;
	}
	public String toString() {
		return name + " - " + phoneNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getName() {
		return name;
	}	
}