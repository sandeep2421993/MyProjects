package cs.cs414.g.domain;

import java.util.ArrayList;

import cs.cs414.g.util.OrderStatus;
import cs.cs414.g.util.WaitingQueue;

public class Kitchen implements  java.io.Serializable
{
	private static final long serialVersionUID = 3044674156377666396L;
	private WaitingQueue preparationWaitingQueue = new WaitingQueue(OrderStatus.PREPARATION_WAITING);
	private WaitingQueue cookingWaitingQueue = new WaitingQueue(OrderStatus.COOKING_WAITING);
	private ArrayList< Chef > chefs = new ArrayList< Chef >();
	
	
	public synchronized void addOrderItem(OrderItem item) {
		
	}
	public synchronized void addChef(Chef chef) {
		chefs.add(chef);
		chef.setWaitingQueue(preparationWaitingQueue);
	}
	
	
	public synchronized void update() {
	}
}
