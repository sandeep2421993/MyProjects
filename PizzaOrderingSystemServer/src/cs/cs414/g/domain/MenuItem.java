package cs.cs414.g.domain;

import cs.cs414.g.domain.MenuItem;

public class MenuItem implements java.io.Serializable{
	
	public int itemID;
	public double price;
	public String description;
	public String name;
	private String type;
	
	public int special=0;

	public MenuItem(String type, double price, int itemID, int special) {
		this.type = type;
		this.price = price;
		
		this.itemID = itemID;
		this.special = special;
	}
	public int getItemID() {
		return itemID;
	}
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	public void setPrice(double price) {
		this.price = price;
	}
	public MenuItem copy() {
		return new MenuItem(type, price, itemID, special);
	}
	
	public String toString() {
		return this.getType();
	}
	public boolean cancel() {
		// TODO Auto-generated method stub
		return false;
	}
}
