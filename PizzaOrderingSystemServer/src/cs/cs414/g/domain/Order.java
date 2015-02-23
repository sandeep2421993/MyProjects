package cs.cs414.g.domain;

import java.util.ArrayList;

public class Order implements java.io.Serializable{
	
	private static final long serialVersionUID = 5199787946298783352L;
	private ArrayList< OrderItem > orderItems = new ArrayList< OrderItem >();
	Customer customer;
	private static int orderId = 0;
	public double total;
	
	public static int orderCounter;
	
	public Order(){
		
	}
	public Order(Customer customer) {
		this.customer = customer;
		this.orderId++;
	}
	
	public synchronized OrderItem addFood(MenuItem food) {
		OrderItem newItem = new OrderItem(this, food);
		orderItems.add(newItem);
		return newItem;
	}

	public synchronized boolean cancelItem(OrderItem item) {
		if (orderItems.contains(item)) {
				orderItems.remove(item);
				return true;
		}
		return false;
	}
	
	public synchronized void cancel() {
		orderItems.clear();
	}
	
	public synchronized double getPrice() {
		total = 0.0;
		for (OrderItem oi : orderItems) {
			total += oi.getFood().getPrice();
		}
		setTotal(total);
		return total;
	}
	
	/**
	 * Get a shallow copy of the list of items in the order.
	 * @return the orderItems
	 */
	public synchronized ArrayList<OrderItem> getOrderItems() {
		return new ArrayList< OrderItem >(orderItems);
	}

	
	public void setOrderItems(ArrayList<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}
	
	/**
	 * Get the order identification number.
	 * @return the order ID.
	 */
	public int getOrderId() {
		return orderId;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
}
