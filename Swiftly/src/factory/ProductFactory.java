package factory;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public abstract class ProductFactory {
	public abstract Product createProduct(int creatorId, int price, String name, String description, byte[] image, int quantity, ArrayList<String> voucherCode);
}
