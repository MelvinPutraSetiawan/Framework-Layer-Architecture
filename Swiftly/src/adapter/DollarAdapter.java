package adapter;

public class DollarAdapter extends Rupiah{
	private Dollar dollar;
	
	public DollarAdapter(Dollar dollar) {
		this.dollar = dollar;
	}
	
	public Double calculatePrice(Double price) {
		return dollar.calculatePrice(price);
	}
}
