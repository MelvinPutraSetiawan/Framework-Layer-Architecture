package observer;

public class Event {
	private String name, description;
	private int discount;
	
	public Event(String name, String description, int discount) {
		this.name = name;
		this.description = description;
		this.discount = discount;
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

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}	
}
