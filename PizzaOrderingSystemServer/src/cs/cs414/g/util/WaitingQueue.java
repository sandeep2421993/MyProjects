package cs.cs414.g.util;

import java.util.Iterator;
import java.util.LinkedList;

import cs.cs414.g.domain.OrderItem;
import cs.cs414.g.domain.OrderItemEmp;

public class WaitingQueue implements OrderItemEmp,
		Iterable<OrderItem>, java.io.Serializable {
	private LinkedList<OrderItem> queue = new LinkedList<OrderItem>();
	private OrderStatus associatedStage;

	public WaitingQueue(OrderStatus stage) {
		this.associatedStage = stage;
	}

	public synchronized void addOrderItem(OrderItem item) {
		queue.add(item);
		
	}

	/**
	 * Removes the next item.
	 *
	 * @return the order item
	 */
	public synchronized OrderItem removeNextItem() {
		OrderItem item = queue.remove();
		
		return item;
	}
	public synchronized OrderItem peekNextItem() {
		return queue.peek();
	}

	public synchronized LinkedList<OrderItem> getQueue() {
		return queue;
	}

	public synchronized boolean cancel(OrderItem item) {
		return queue.remove(item);
	}

	public OrderStatus getAssociatedStage() {
		return associatedStage;
	}

	public synchronized Iterator<OrderItem> iterator() {
		return queue.iterator();
	}

	public synchronized boolean contains(OrderItem item) {
		return queue.contains(item);
	}

}
