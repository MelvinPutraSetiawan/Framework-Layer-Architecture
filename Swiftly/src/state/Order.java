package state;

import factory.Product;

public class Order {
	private State currentState;
	private Product product;
    private int quantity;
    private int buyerId;

    public Order(Product product, int quantity, int buyerId) {
        this.product = product;
        this.quantity = quantity;
        this.buyerId = buyerId;
        this.currentState = new PaymentState();
    }
	
    public void nextState() {
        currentState.handle(this);
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
}
