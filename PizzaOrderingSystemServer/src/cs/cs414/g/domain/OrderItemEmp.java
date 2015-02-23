package cs.cs414.g.domain;

import cs.cs414.g.util.OrderStatus;

public interface OrderItemEmp
	{
		public boolean cancel(OrderItem item);
		
		public OrderStatus getAssociatedStage();
	}
