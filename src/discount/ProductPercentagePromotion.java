package discount;

import java.util.List;

import domain.Order;
/**
 * @author Ruben Schroyen
 */
public class ProductPercentagePromotion implements CalculatePrice{

	@Override
	public double calculateTotal(List<Order> orders, String id) {
		double total = 0;
		for (Order order : orders){
			if (order.getProduct().getId().equals(id)){
				total += order.getPrice()*0.90;
			}
			else{
				total += order.getPrice();
			}
		}
		return total;
	}

}
