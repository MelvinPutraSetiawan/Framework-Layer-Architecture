package factory;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class GameVoucher extends Product{
	private ArrayList<String> voucherCodes = new ArrayList<>();

	public GameVoucher(int creatorId, int price, String name, String description, byte[] image,
			ArrayList<String> voucherCodes) {
		super(creatorId, price, name, description, image);
		this.voucherCodes = voucherCodes;
	}

	public ArrayList<String> getVoucherCodes() {
		return voucherCodes;
	}

	public void setVoucherCodes(ArrayList<String> voucherCodes) {
		this.voucherCodes = voucherCodes;
	}
}
