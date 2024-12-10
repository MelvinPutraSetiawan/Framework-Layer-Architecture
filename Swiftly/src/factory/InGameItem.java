package factory;

import java.util.concurrent.atomic.AtomicReference;

public class InGameItem extends Product{
	private int quantity;

	public InGameItem(int creatorId, int price, String name, String description, byte[] image, int quantity) {
		super(creatorId, price, name, description, image);
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}	
	
	public void subtractQuantity(int quantity) {
		this.quantity = this.quantity - quantity;
	}
}
