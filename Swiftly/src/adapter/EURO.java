package adapter;

public class EURO {
	private Double multiplier = 0.00005965;
	
	public EURO() {
		
	}
	
	public Double calculatePrice(Double price) {
		return price*multiplier;
	}
}
