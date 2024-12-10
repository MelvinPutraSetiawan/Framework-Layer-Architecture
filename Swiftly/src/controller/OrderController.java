package controller;

import java.util.ArrayList;

import adapter.Rupiah;
import factory.Product;
import state.Order;

public class OrderController {
	private static ArrayList<Order> orders = new ArrayList<>();
	
	public Order CheckOut(Product product, int quantity, int buyerId, Double total) {
		Order newOrder = new Order(product, quantity, buyerId, total);
		orders.add(newOrder);
		return newOrder;
	}
	
	// Purpose: Use for getting the total price of an item
	// Design Pattern: Used for ADAPTER Design pattern
	public Double getPrice(Rupiah rupiah, Double price) {
		return rupiah.calculatePrice(price);
	}
	
	// Purpose: Change the order into "Canceled State".
	public String CancelOrder(Order order) {
		return order.cancelOrder();
	}
	
	// Purpose: Changing the order state into the next state.
	public String nextState(Order order) {
		return order.nextState();
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}
}
