package adapter;

public class EUROAdapter extends Rupiah{
	private EURO euro;

	public EUROAdapter(EURO euro) {
		this.euro = euro;
	}
	
	public Double calculatePrice(Double price) {
		return euro.calculatePrice(price);
	}
}
