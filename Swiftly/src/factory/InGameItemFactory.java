package factory;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class InGameItemFactory extends ProductFactory{

	@Override
	public Product createProduct(int creatorId, int price, String name, String description, byte[] image, int quantity,
			ArrayList<String> voucherCode) {
		// TODO Auto-generated method stub
		return new InGameItem(creatorId, price, name, description, image, quantity);
	}

}
