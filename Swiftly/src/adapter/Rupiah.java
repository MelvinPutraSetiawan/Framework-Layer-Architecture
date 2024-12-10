package adapter;

public class Rupiah {
	private Double multiplier = 1.0;
	
	public Rupiah() {
		
	}
	
	public Double calculatePrice(Double price) {
		return price*multiplier;
	}
}
