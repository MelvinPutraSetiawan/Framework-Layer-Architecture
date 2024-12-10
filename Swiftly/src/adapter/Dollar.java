package adapter;

public class Dollar {
	private Double multiplier = 0.00006299;
	
	public Dollar() {
		
	}
	
	public Double calculatePrice(Double price) {
		return price*multiplier;
	}
}
