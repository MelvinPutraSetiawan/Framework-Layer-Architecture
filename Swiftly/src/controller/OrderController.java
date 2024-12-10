package controller;

import java.util.ArrayList;

import adapter.Rupiah;
import factory.Product;
import state.Order;

public class OrderController {
	private ArrayList<Order> orders = new ArrayList<>();
	
	public Order CheckOut(Product product, int quantity, int buyerId) {
		Order newOrder = new Order(product, quantity, buyerId);
		orders.add(newOrder);
		return newOrder;
	}
	
	public Double getPrice(Rupiah rupiah, Double price) {
		return rupiah.calculatePrice(price);
	}
	
	public void CancelOrder(Order order) {
		order.cancelOrder();
	}
	
	public void nextState(Order order) {
		order.nextState();
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}
}
