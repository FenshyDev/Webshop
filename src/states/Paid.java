package states;

import domain.Invoice;
import domain.Order;

public class Paid implements State {
	private Invoice invoice;
	
	public Paid(Invoice invoice) {
		this.invoice = invoice;
	}
	
	public void delete(int i) {
		invoice.deleteProductOrder(i);
	}

	public void add(Order order) {
		invoice.addProductOrder(order);
	}

	public void cancel() {
		invoice.setState(new Cancelled(invoice));
		
	}

	public void pay(double amountPaid) {
		
	}
}