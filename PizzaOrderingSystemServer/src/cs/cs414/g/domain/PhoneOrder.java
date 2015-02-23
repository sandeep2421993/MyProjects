package cs.cs414.g.domain;

import java.util.Map;
import java.util.Set;
import java.util.Vector;

import cs.cs414.g.util.OrderStatus;

public class PhoneOrder {
	
	private Map< String, Customer > customers = null;
	private Map< String, Vector< Order > > orders = null;
	
	public PhoneOrder(Map< String, Customer > customers, Map< String, Vector< Order > > orders) {
		this.customers = customers;
		this.orders = orders;
	}

	public Customer getCustomerForPhoneNumber(String phoneNumber) {
		return customers.get(phoneNumber);
	}

	public void addCustomer(Customer newCustomer) {
		customers.put(newCustomer.getPhoneNumber(), newCustomer);
		orders.put(newCustomer.getPhoneNumber(), new Vector< Order >());
	}
	
	public Vector< Order > getOrdersShallowCopyForCustomer(Customer customer) {
		synchronized(orders) {
			Vector< Order > ordersRef = orders.get(customer.getPhoneNumber()); 
			if (ordersRef == null) {
				return null;
			}
			
			synchronized(ordersRef) {
				return new Vector< Order >(ordersRef);
			}
		}
	}
	
	public Order getCurrentOrderForCustomer(Customer customer, boolean createNewOrder) {
		Vector< Order > customersOrders = orders.get(customer.getPhoneNumber());
		
		
		if (createNewOrder) {
			Order newOrder = new Order(customer);
			customersOrders.add(newOrder);
			return newOrder;
		}
		else {
			if (customersOrders.size() > 0) {
				return customersOrders.get(customersOrders.size() - 1);
			}
			else {
				return null;
			}
		}
	}
	
	public Order getLastOrder(String phoneNumber) {
		Customer customer = getCustomerForPhoneNumber(phoneNumber);

		if(customer == null)
			return null;

		Order order = getCurrentOrderForCustomer(customer, false);
		return order;
	}
	
	public boolean removeOrderItem(OrderItem item) {
		return item.getOrder().cancelItem(item);
	}

	public void estimateTime(Order newOrder){
		double total=0;
		synchronized(orders) {
			Set< Map.Entry< String, Vector<Order > > > entrySet = orders.entrySet();

			for (Map.Entry< String, Vector< Order > > kv : entrySet) {
				Vector< Order > custOrders = kv.getValue();
				synchronized(custOrders) {
					for (Order curOrder : custOrders) {
						synchronized(curOrder){
							
								double orderTotal =0;
								OrderStatus stage;
								for (OrderItem curItem : curOrder.getOrderItems()){
									
									
									total += orderTotal;
								
							}
						}
					}
				}
			}
		}
		
	}
	
}
