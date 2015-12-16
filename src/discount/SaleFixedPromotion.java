package discount;

import java.util.List;

import domain.Order;
/**
 * @author Ruben Schroyen
 */
public class SaleFixedPromotion implements CalculatePrice {

	@Override
	public double calculateTotal(List<Order> orders, String id) {
		double total = 0;
		for (Order order : orders){
			total += order.getPrice();
		}
		if (total >= 150)
			return total - 25;
		else if (total >= 100)
			return total - 15;
		else if (total >= 75)
			return total - 10;
		else if (total >= 50)
			return total - 5;
		else 
			return total;
	}

}
