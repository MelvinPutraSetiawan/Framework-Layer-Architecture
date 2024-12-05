package factory;

import java.util.concurrent.atomic.AtomicReference;

public class Product {
	private int creatorId, price;
	private String name, description;
	private byte[] image;
	public Product(int creatorId, int price, String name, String description, byte[] image) {
		this.creatorId = creatorId;
		this.price = price;
		this.name = name;
		this.description = description;
		this.image = image;
	}
	public int getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
}
