package discount;
/**
 * @author Ruben Schroyen
 */
import java.util.List;

import domain.Order;

public interface CalculatePrice {
	public double calculateTotal(List<Order> orders, String id);
}
