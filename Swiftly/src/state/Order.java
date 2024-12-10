package state;

import factory.Product;

public class Order {
	private State currentState;
	private Product product;
    private int quantity;
    private int buyerId;
    private Double total;

    public Order(Product product, int quantity, int buyerId, Double total) {
        this.product = product;
        this.quantity = quantity;
        this.buyerId = buyerId;
        this.total = total;
        this.currentState = new PaymentState();
    }
	
    public String nextState() {
        return currentState.handle(this);
    }
    
    public String getCurrentState() {
        return currentState.getStateName();
    }
    
    public String cancelOrder() {
    	return currentState.cancel(this);
    }

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
}
