package cs.cs414.g.domain;

public class OrderItem implements java.io.Serializable{
	
	private static final long serialVersionUID = -7261946680285305982L;
	private MenuItem food;
	private Order order;
	
	public OrderItem(Order order, MenuItem food) {
		this.food = food;
		this.order = order;
	}

	public MenuItem getFood() {
		return food;
	}

	public Order getOrder() {
		return order;
	}
	
	public String toString() {
		return food.toString();
	}
}
