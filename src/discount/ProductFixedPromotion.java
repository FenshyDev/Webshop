package discount;
/**
 * @author Ruben Schroyen
 */
import java.util.List;

import domain.Order;

public class ProductFixedPromotion implements CalculatePrice{

	@Override
	public double calculateTotal(List<Order> orders, String id) {
		double total = 0;
		for (Order order : orders){
			if (order.getProduct().getId().equals(id)){
				total += order.getPrice() - 0.5;
			}
			else{
				total += order.getPrice();
			}
		}
		return total;
	}

}
