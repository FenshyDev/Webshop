package discount;

import java.util.List;

import domain.Order;

public class NoDiscountPromotion implements CalculatePrice {

	@Override
	public double calculateTotal(List<Order> orders, String id) {
		double total = 0;
		for (Order order : orders){
			total += order.getPrice();
		}
		return total;
	}

}
