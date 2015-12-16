package discount;

import java.util.List;

import domain.Order;

public class SalePercentagePromotion implements CalculatePrice{

	@Override
	public double calculateTotal(List<Order> orders, String id) {
		double total = 0;
		for (Order order : orders){
			total += order.getPrice();
		}
		if (total >= 50)
			return total*0.10;
		else 
			return total;
	}

}
